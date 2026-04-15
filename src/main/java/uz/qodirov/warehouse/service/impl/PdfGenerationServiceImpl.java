package uz.qodirov.warehouse.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.model.Order;
import uz.qodirov.warehouse.model.OrderItem;
import uz.qodirov.warehouse.repository.OrderItemRepository;
import uz.qodirov.warehouse.service.PdfGenerationService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfGenerationServiceImpl implements PdfGenerationService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public File generateOrderInvoice(Order order) throws Exception {
        String fileName = "order_" + order.getId() + ".pdf";
        File pdfFile = new File(fileName);

        try (PdfWriter writer = new PdfWriter(pdfFile);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Header
            document.add(new Paragraph("YUK XATI (Nakladnoy) #" + order.getId())
                    .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            document.add(new Paragraph("Sana: " + (order.getOrderDate() != null ? order.getOrderDate().toString() : "Hozirgacha"))
                    .setTextAlignment(TextAlignment.RIGHT));

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Qabul qiluvchi: " + order.getKindergarten().getName()).setBold());
            if (order.getKindergarten().getRegion() != null) {
                document.add(new Paragraph("Manzil: " + order.getKindergarten().getRegion().getName()));
            }

            document.add(new Paragraph("\n"));

            // Items Table
            float[] columnWidths = {1, 5, 2, 2, 3};
            Table table = new Table(columnWidths);
            table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("T/R").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Maxsulot").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("O'lchov").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Miqdor").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Narx").setBold()));

            List<OrderItem> items = orderItemRepository.findAllByOrderId(order.getId());
            int i = 1;
            for (OrderItem item : items) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(i++))));
                table.addCell(new Cell().add(new Paragraph(item.getProduct().getName())));
                table.addCell(new Cell().add(new Paragraph(item.getProduct().getUnit())));
                table.addCell(new Cell().add(new Paragraph(item.getQuantity().toString())));
                table.addCell(new Cell().add(new Paragraph(item.getPriceAtOrder() != null ? item.getPriceAtOrder().toString() : "0")));
            }
            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Jami Summa: " + (order.getTotalAmount() != null ? order.getTotalAmount().toString() : "0") + " so'm")
                    .setBold().setTextAlignment(TextAlignment.RIGHT));

            document.add(new Paragraph("\n\nTizim orqali tasdiqlash uchun QR Kod:").setBold().setTextAlignment(TextAlignment.CENTER));
            
            // Add QR Code
            String qrData = "CONFIRM_ORDER_" + order.getId();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            
            Image qrImage = new Image(ImageDataFactory.create(pngOutputStream.toByteArray()));
            qrImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
            document.add(qrImage);

            document.add(new Paragraph("Ushbu yuk xati elektron hosil qilingan bo'lib, QR kod orqali haydovchi tomonidan tasdiqlanishi shart.")
                    .setFontSize(10).setTextAlignment(TextAlignment.CENTER));

        } catch (Exception e) {
            log.error("Error generating PDF", e);
            throw e;
        }

        return pdfFile;
    }
}
