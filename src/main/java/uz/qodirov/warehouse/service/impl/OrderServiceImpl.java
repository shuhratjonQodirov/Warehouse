package uz.qodirov.warehouse.service.impl;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.model.Order;
import uz.qodirov.warehouse.model.OrderItem;
import uz.qodirov.warehouse.repository.OrderItemRepository;
import uz.qodirov.warehouse.repository.OrderRepository;
import uz.qodirov.warehouse.service.OrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @SneakyThrows
    @Override
    public byte[] generateWayBillings(Long orderId) {

        Order order = orderRepository.findByIdAndDeletedFalse(orderId).orElseThrow(() -> new ByIdException("Order not found"));
        List<OrderItem> items = orderItemRepository.findAllByOrderId(orderId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // 1. Sarlavha (Korxona nomi va Sana)
        document.add(new Paragraph("Korxona nomi: My Gold"));

        String sana = (order.getOrderDate() != null) ? order.getOrderDate().toString() : "___/___/2026-yil";
        document.add(new Paragraph("Sana: " + sana).setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph("YUK XATI").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));

        // 2. Ma'lumotlar
        String qayerdan = (items != null && !items.isEmpty()) ? items.get(0).getWarehouse().getName() : "_________________";
        document.add(new Paragraph("Qayerdan: " + qayerdan));
        document.add(new Paragraph("Qayerga: " + order.getKindergarten().getName()));
        document.add(new Paragraph("Kim orqali: " + order.getDriver().getFullName()));

        // 3. Jadval
        float[] columnWidths = {30f, 200f, 70f, 60f, 80f, 100f};
        Table table = new Table(UnitValue.createPointArray(columnWidths));

        table.addHeaderCell("T/r №");
        table.addHeaderCell("Mahsulot nomi");
        table.addHeaderCell("O'lchov birligi");
        table.addHeaderCell("Miqdori");
        table.addHeaderCell("Narxi");
        table.addHeaderCell("Summasi");

        int count = 1;
        for (OrderItem item : items) {
            table.addCell(String.valueOf(count++));
            table.addCell(item.getProduct().getName());
            table.addCell("kg/dona");
            table.addCell(item.getQuantity().toString());
            table.addCell(item.getPriceAtOrder().toString());
            table.addCell(item.getTotalPrice().toString());
        }

        // 4. Jami
        table.addCell("");
        table.addCell(new Cell(1, 4).add(new Paragraph("Jami:").setBold()));
        table.addCell(order.getTotalAmount().toString());

        document.add(table);

        // 5. Imzolar
        document.add(new Paragraph("\nRahbar: _________                   Topshirdi: _________"));
        document.add(new Paragraph("Hisobchi: ________                    Qabul qildi: ________"));

        document.close();
        return baos.toByteArray();
    }
}
