package uz.qodirov.warehouse.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class EncryptionAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        EncryptResponse annotation = returnType.getMethodAnnotation(EncryptResponse.class);

        return annotation != null && annotation.encrypted();
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        try {

            // 🔥 1. Accept headerni olish
            boolean wantsPlain = request.getHeaders()
                    .getAccept()
                    .stream()
                    .anyMatch(mt -> mt.includes(CustomMediaType.DECRYPTED_JSON));

            // 🔥 2. Agar plain so‘rasa → encrypt QILMAYMIZ
            if (wantsPlain) {
                response.getHeaders().setContentType(CustomMediaType.DECRYPTED_JSON);
                return body;
            }

            // 🔥 3. Default → encrypt
            String json = objectMapper.writeValueAsString(body);
            String encrypted = EncryptionUtil.encrypt(json);

            return Map.of("data", encrypted);

        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }
}
