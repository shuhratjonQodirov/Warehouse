package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KindergartenReqDto {
    @NotNull(message = "Region cannot be empty")
    private Long regionId;
    @NotNull(message = "User cannot be empty")
    private Long mudirId;
    @NotBlank(message = "Kindergarten name cannot be empty")
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;

    //bolalarga oid jami bog'chadagi bolalar
    @NotNull(message = "Total children cannot be empty")
    @Min(value = 0, message = "Children amount must be than 0")
    private Integer totalChildren;
    //jami: 1-3 gacha bolalar vaqtga asosan
    private Integer totalChildren1to3 = 0;

    @NotNull(message = "Children 1-3 years 3 hours cannot be empty")
    @Min(0)
    private Integer children1to3_3h = 0;

    @Min(0)
    @NotNull(message = "Children 1-3 years 9 hours cannot be empty")
    private Integer children1to3_9h = 0;

    @NotNull(message = "Children 1-3 years 12 hours cannot be empty")
    @Min(0)
    private Integer children1to3_12h = 0;

    @NotNull(message = "Children 1-3 24 hours cannot be empty")
    @Min(0)
    private Integer children1to3_24h = 0;

    //jami: 3-7 gacha bolalar vaqtga asosan
    private Integer totalChildren3to7 = 0;

    @Min(0)
    @NotNull(message = "Children 3-7 years 3 hours cannot be empty")
    private Integer children3to7_3h = 0;
    @Min(0)
    @NotNull(message = "Children 3-7 9 years hours cannot be empty")
    private Integer children3to7_9h = 0;
    @Min(0)
    @NotNull(message = "Children 3-7 12 years hours cannot be empty")
    private Integer children3to7_12h = 0;
    @Min(0)
    @NotNull(message = "Children 3-7 years 24 hours cannot be empty")
    private Integer children3to7_24h = 0;


    @AssertTrue(message = "1-3 yoshdagilar soni vaqt bo'yicha taqsimotga mos kelmaydi")
    private boolean is1to3BreakdownValid() {
        int calculated = sum(children1to3_3h, children1to3_9h, children1to3_12h, children1to3_24h);
        return calculated == (totalChildren1to3 != null ? totalChildren1to3 : calculated);
    }

    @AssertTrue(message = "3-7 yoshdagilar soni vaqt bo'yicha taqsimotga mos kelmaydi")
    private boolean is3to7BreakdownValid() {
        int calculated = sum(children3to7_3h, children3to7_9h, children3to7_12h, children3to7_24h);
        return calculated == (totalChildren3to7 != null ? totalChildren3to7 : calculated);
    }

    @AssertTrue(message = "Umumiy bolalar soni ikkala guruh yig'indisiga teng emas")
    private boolean isTotalChildrenConsistent() {
        int group1 = totalChildren1to3 != null ? totalChildren1to3 : sum(children1to3_3h, children1to3_9h, children1to3_12h, children1to3_24h);
        int group2 = totalChildren3to7 != null ? totalChildren3to7 : sum(children3to7_3h, children3to7_9h, children3to7_12h, children3to7_24h);
        return totalChildren == (group1 + group2);
    }

    private static int sum(Integer... values) {
        int s = 0;
        for (Integer v : values) {
            if (v != null) s += v;
        }
        return s;
    }
}
