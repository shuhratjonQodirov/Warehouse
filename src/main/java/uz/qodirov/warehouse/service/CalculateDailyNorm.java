package uz.qodirov.warehouse.service;

import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.enums.AgeGroup;
import uz.qodirov.warehouse.enums.StayDuration;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.ProductNorm;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculateDailyNorm {
    public BigDecimal dailyNorm(Kindergarten kg, List<ProductNorm> list) {
        BigDecimal total = BigDecimal.ZERO;
        for (ProductNorm norm : list) {
            int countChildren = children(kg, norm.getAgeGroup(), norm.getStayDuration());
            if (countChildren == 0) {
                continue;
            }

            BigDecimal quantity =
                    norm.getDailyPerChild()
                            .multiply(BigDecimal.valueOf(countChildren));

            total = total.add(quantity);

        }
        return total;
    }

    private int children(Kindergarten kg, AgeGroup ageGroup, StayDuration stayDuration) {

        if (ageGroup == AgeGroup.ONE_TO_THREE) {

            switch (stayDuration) {
                case H_3_4:
                    return kg.getChildren1to3_3h();

                case H_9:
                    return kg.getChildren1to3_9h();

                case H_12:
                    return kg.getChildren1to3_12h();

                case H_24:
                    return kg.getChildren1to3_24h();
            }
        }
        if (ageGroup == AgeGroup.THREE_TO_SEVEN) {
            switch (stayDuration) {
                case H_3_4:
                    return kg.getChildren3to7_3h();

                case H_9:
                    return kg.getChildren3to7_9h();

                case H_12:
                    return kg.getChildren3to7_12h();

                case H_24:
                    return kg.getChildren3to7_24h();
            }
        }
        return 0;
    }
}
