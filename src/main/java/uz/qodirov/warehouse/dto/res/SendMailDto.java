package uz.qodirov.warehouse.dto.res;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SendMailDto {
    private String sendTo;
    private String subjectName;
    private String templateName;
    private Map<String, Object> model;
}