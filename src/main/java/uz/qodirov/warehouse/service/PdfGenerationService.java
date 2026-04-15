package uz.qodirov.warehouse.service;

import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.model.Order;

import java.io.File;

@Service
public interface PdfGenerationService {
    File generateOrderInvoice(Order order) throws Exception;
}
