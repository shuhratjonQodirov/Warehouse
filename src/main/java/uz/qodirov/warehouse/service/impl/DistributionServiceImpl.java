package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.dto.req.*;
import uz.qodirov.warehouse.dto.res.DistributionInfoResDto;
import uz.qodirov.warehouse.dto.res.OrderDetailResDto;
import uz.qodirov.warehouse.dto.res.OrderItemResDto;
import uz.qodirov.warehouse.enums.AgeGroup;
import uz.qodirov.warehouse.enums.OrderStatus;
import uz.qodirov.warehouse.enums.StayDuration;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.error.ExistsNameException;
import uz.qodirov.warehouse.model.*;
import uz.qodirov.warehouse.repository.*;
import uz.qodirov.warehouse.service.CalculateDailyNorm;
import uz.qodirov.warehouse.service.DistributionService;
import uz.qodirov.warehouse.service.WorkingDayService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistributionServiceImpl implements DistributionService {
    private final KindergartenRepository kindergartenRepo;
    private final ProductNormRepository productNormRepo;
    private final ProductRepository productRepository;
    private final KindergartenMonthlySupplyRepository monthlySupplyRepo;
    private final HolidayRepository holidayRepo;
    private final StockRepository stockRepo;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WorkingDayService workingDayService;
    private final CalculateDailyNorm calculateDailyNorm;
    private final WarehouseRepository warehouseRepository;
    private final PaginationUtil paginationUtil;
    private final UserRepository usersRepository;
    private final TelegramClient telegramClient;

    @Override
    public ApiResponse<?> getInfo(DistributionInfoReqDto dto) {

        YearMonth yearMonth = YearMonth.parse(dto.getYearMonth());
        if (yearMonth.isBefore(YearMonth.now())) {
            throw new ByIdException("O‘tgan oy uchun mahsulot berish mumkin emas");
        }
        int totalWorkingDays = workingDayService.calculateWorkingDays(yearMonth);

        Kindergarten kindergarten = kindergartenRepo
                .findByIdAndDeletedFalse(dto.getKindergartenId())
                .orElseThrow(() -> new ByIdException("kindergarten not found"));

        Product product = productRepository
                .findByIdAndDeletedFalse(dto.getProductId())
                .orElseThrow(() -> new ExistsNameException("Product not found"));

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(dto.getWarehouseId())
                .orElseThrow(() -> new ExistsNameException("This warehouse not found"));

        Stock stock = stockRepo.findByProductAndWarehouse(product, warehouse).orElse(new Stock());


        Optional<KindergartenMonthlySupply> optionalKindergartenMonthlySupply = monthlySupplyRepo.findByKindergartenAndProductAndYearMonth(kindergarten, product, dto.getYearMonth());
        KindergartenMonthlySupply monthlySupply;
        if (optionalKindergartenMonthlySupply.isPresent()) {
            monthlySupply = optionalKindergartenMonthlySupply.get();
        } else {
            monthlySupply = new KindergartenMonthlySupply();
            monthlySupply.setKindergarten(kindergarten);
            monthlySupply.setYearMonth(dto.getYearMonth());
            monthlySupply.setTotalWorkingDays(totalWorkingDays);
            monthlySupply.setSuppliedWorkingDays(0);
            monthlySupply.setProduct(product);
            monthlySupplyRepo.save(monthlySupply);
        }

        Integer suppliedWorkingDays = monthlySupply.getSuppliedWorkingDays();

        int remainingWorkingDays = totalWorkingDays - suppliedWorkingDays;

        List<ProductNorm> productNormList = productNormRepo.findByProductAndDeletedFalse(product);

        BigDecimal dailyNorm = calculateDailyNorm.dailyNorm(kindergarten, productNormList);
        DistributionInfoResDto dto1 = DistributionInfoResDto.builder().totalWorkingDays(totalWorkingDays)
                .suppliedWorkingDays(suppliedWorkingDays)
                .remainingWorkingDays(remainingWorkingDays)
                .recommendedQuantity(dailyNorm)
                .availableProduct(stock.getAvailableQuantity())
                .build();
        return new ApiResponse<>("Rec", true, dto1);
    }

    @Override
    @Transactional
    public ApiResponse<?> create(OrderReqDto dto) {

        Kindergarten kindergarten = kindergartenRepo
                .findByIdAndDeletedFalse(dto.getKindergartenId())
                .orElseThrow(() -> new ExistsNameException("Kindergarten not found"));

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(dto.getWarehouseId())
                .orElseThrow(() -> new ExistsNameException("Warehouse not found"));

        Order order = new Order();
        order.setStatus(OrderStatus.PREPARING);
        order.setKindergarten(kindergarten);
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemReq item : dto.getItems()) {
            Product product = productRepository.findByIdAndDeletedFalse(item.getProductId()).orElseThrow();

            KindergartenMonthlySupply monthlySupply = monthlySupplyRepo
                    .findByKindergartenAndProductAndYearMonth(kindergarten, product, dto.getYearMonth())
                    .orElseThrow(() -> new ExistsNameException("Monthly supply not found"));


            if (monthlySupply.getRemainingWorkingDays() < item.getDays()) {
                throw new ByIdException("Remaining WorkingDays siz kiritagan kunlardan kam");
            }

            Stock stock = stockRepo.findByProductAndWarehouse(product, warehouse)
                    .orElseThrow(() -> new ExistsNameException("This product not exixts in the warehouse"));


            if (stock.getPhysicalQuantity().compareTo(item.getQuantity()) < 0) {
                throw new ByIdException("Omborda maxsulot yetarli emas");
            }


            stock.setReservedQuantity(stock.getReservedQuantity().add(item.getQuantity()));

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setWarehouse(warehouse);
            orderItem.setMonthlySupply(monthlySupply);
            orderItem.setPriceAtOrder(product.getCurrentPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setWorkingDays(item.getDays());


            totalAmount = totalAmount.add(item.getQuantity().multiply(product.getCurrentPrice()));

            List<ProductNorm> productNormList = productNormRepo.findByProductAndDeletedFalse(product);

            BigDecimal dailyNorm = calculateDailyNorm.dailyNorm(kindergarten, productNormList);

            monthlySupply.setSuppliedWorkingDays(monthlySupply.getSuppliedWorkingDays() + item.getDays());
            monthlySupply.setLastSupplyDate(LocalDate.now());

            orderItem.setRecommendedQuantity(dailyNorm.multiply(BigDecimal.valueOf(item.getDays())));

            orderItemRepository.save(orderItem);
        }
        order.setTotalAmount(totalAmount);
        return null;
    }

    @Override
    public ApiResponse<?> getAll(int page, int size) {
        Pageable pageable = paginationUtil.createPageable(page, size);

        List<OrderListResDto> list = orderRepository.findByRes(pageable);
        int totalPages = (int) Math.ceil((double) list.size() / size);
        Map<String, Object> meta = paginationUtil.createMeta(list.size(), page, size, totalPages);


        return new ApiResponse<>("", true, list, meta);
    }

    @Override
    public ApiResponse<?> getOneByid(Long id) {
        OrderListResDto dto = orderRepository.findByIdQu(id).orElseThrow(() -> new ExistsNameException("Order not found"));
        List<OrderItemResDto> itemResDtoList = orderItemRepository.findByOrder(dto.getId());

        OrderDetailResDto detailResDto = OrderDetailResDto.builder()
                .id(dto.getId())
                .kindergartenName(dto.getKindergartenName())
                .driverName(dto.getDriverName())
                .status(dto.getStatus()).totalAmount(dto.getTotalAmount())
                .orderDate(dto.getOrderDate())
                .items(itemResDtoList)
                .build();

        return new ApiResponse<>("order detail", true, detailResDto);
    }

    @SneakyThrows
    @Override
    @Transactional
    public ApiResponse<?> assignDriver(AssignDriver assignDriver) {
        Order order = orderRepository
                .findByIdAndDeletedFalse(assignDriver.getOrderId())
                .orElseThrow(() -> new ByIdException("Order not found"));

        User driver = usersRepository
                .findByIdAndDeletedFalse(assignDriver.getDriverId())
                .orElseThrow(() -> new ByIdException("Driver not found"));

        order.setDriver(driver);
        order.setStatus(OrderStatus.SHIPPED);

        List<OrderItemResDto> itemResDtoList = orderItemRepository.findByOrder(order.getId());

        for (OrderItemResDto item : itemResDtoList) {
            Product product = productRepository
                    .findByIdAndDeletedFalse(item.getProductId())
                    .orElseThrow(() -> new ByIdException("Product not found"));
            System.out.println(item.toString());
            Warehouse warehouse = warehouseRepository
                    .findByIdAndDeletedFalse(item.getWarehouseId())
                    .orElseThrow(() -> new ByIdException("Warehosue not found"));


            Stock stock = stockRepo.findByProductAndWarehouse(product, warehouse).orElseThrow();
            stock.setPhysicalQuantity(stock.getPhysicalQuantity().subtract(item.getQuantity()));
            stock.setReservedQuantity(stock.getReservedQuantity().subtract(item.getQuantity()));

        }


        SendMessage message = new SendMessage(driver.getChatId().toString(), "");

        message.setText("Sizga yangi buyurtma biriktirildi");
        telegramClient.execute(message);

        return new ApiResponse<>("Driver assigned", true);
    }
}
