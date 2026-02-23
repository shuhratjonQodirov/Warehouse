package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReqDto {

    @NotBlank(message = "username cannot be empty")
    @Size(min = 6, message = "username must be at least 6 characters")
    private String username;
    @NotBlank(message = "full name cannot be empty")
    private String fullName;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    private Long chatId;
    @NotNull(message = "Brith date cannot be empty")
    private LocalDate brithDate;
    @NotBlank(message = "role cannot be empty")
    private String role;

    @Size(min = 6, message = "password must be at least 6 characters")
    @NotBlank(message = "password cannot be empty")
    private String password;
}
