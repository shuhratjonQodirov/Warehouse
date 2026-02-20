package uz.qodirov.warehouse.component;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    private String mode;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {

            User user = User
                    .builder()
                    .fullName("Qodirov Shuhratjon")
                    .username("Shuhratchik")
                    .email("shuhratbekqodirov926@gmail.com")
                    .phoneNumber("+998905823433")
                    .password(passwordEncoder.encode("0101"))
                    .chatId(Long.parseLong("01214754"))
                    .brithDate(LocalDate.of(2000, 2, 10))
                    .role(RoleName.ADMIN)
                    .build();


            userRepository.save(user);
        }
    }
}
