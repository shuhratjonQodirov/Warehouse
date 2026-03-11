package uz.qodirov.warehouse.tgBot.handle;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.tgBot.state.BotState;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HandlerFactory {

    private final Map<BotState, BotStateHandler> handlers;

    public HandlerFactory(List<BotStateHandler> handlerList) {
        this.handlers = handlerList.stream().collect(Collectors
                .toMap(BotStateHandler::getState
                        , h -> h));
    }

    public BotStateHandler getHandler(BotState state) {
        return handlers.get(state);
    }
}
