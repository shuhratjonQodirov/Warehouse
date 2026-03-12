package uz.qodirov.warehouse.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qodirov.warehouse.tgBot.model.BotUser;

import java.util.Optional;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findByChatId(Long chatId);
}