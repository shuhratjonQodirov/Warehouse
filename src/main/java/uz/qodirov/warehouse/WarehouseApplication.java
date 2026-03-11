package uz.qodirov.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import uz.qodirov.warehouse.service.WorkingDayService;

@SpringBootApplication
@EnableAsync
public class WarehouseApplication {


    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);


    }

}
