package uz.qodirov.warehouse.annotation;

import org.springframework.http.MediaType;

public class CustomMediaType {

    public static final MediaType DECRYPTED_JSON =
            new MediaType("application", "decrypted+json");
}
