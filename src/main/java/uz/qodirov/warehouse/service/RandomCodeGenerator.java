package uz.qodirov.warehouse.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomCodeGenerator {
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final SecureRandom random = new SecureRandom();

    public String generateCode(int length) {
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }

}
