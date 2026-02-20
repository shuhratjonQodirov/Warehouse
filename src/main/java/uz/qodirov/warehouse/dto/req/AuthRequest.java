package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(

        @NotBlank(message = "username cannot be empty")
        String username,
        @NotBlank(message = "password cannot be empty")
        String password
) {
}
