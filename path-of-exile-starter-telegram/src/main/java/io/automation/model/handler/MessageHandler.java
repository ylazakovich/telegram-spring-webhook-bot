package io.automation.model.handler;

import io.automation.dao.UserDAO;
import io.automation.service.MenuService;
import io.automation.cash.BotStateCash;
import io.automation.model.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@Slf4j
public class MessageHandler {

  private final UserDAO userRepo;
  private final MenuService menuService;
  private final BotStateCash botStateCash;

  public MessageHandler(UserDAO userRepo,
                        MenuService menuService,
                        BotStateCash botStateCash) {
    this.userRepo = userRepo;
    this.menuService = menuService;
    this.botStateCash = botStateCash;
  }

  public BotApiMethod<?> handle(Message message, State state) {
    // TODO: might be it can be moved to higher level;
    final User user = message.getFrom();
    userRepo.addIfNotExist(user);
    long userId = user.getId();
    long chatId = message.getChatId();
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chatId));
    sendMessage.setText("Welcome to service: 'Path of Exile Starter'");
    botStateCash.saveState(userId, state);
    return switch (state) {
      case START -> menuService.getMainMenuMessage(message, "Select ANY command");
      case SKILLS_WAIT_EVENT -> menuService.getSkillsMenu(message, "Select ANY Skill's command");
      // TODO: wait for implementation
      case WAIT_FOR_COMMAND -> new SendMessage();
      default -> throw new IllegalStateException("Unexpected value: " + state);
    };
  }
}
