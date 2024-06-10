package io.starter.telegram.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.starter.telegram.cash.state.CallbackState;
import io.starter.telegram.cash.state.MessageState;
import io.starter.telegram.config.Emoji;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
public class MenuService {

  public SendMessage getMain(Message message) {
    return createMessageWithInlineKeyboard(message, getReplyMenu());
  }

  public SendMessage getStart(Message message) {
    return createMessageWithInlineKeyboard(message.getChatId(), getStartSubMenu());
  }

  public EditMessageText getMenuWithSkills(MaybeInaccessibleMessage message) {
    final String text = """
        GUIDE

        1. Looking for Skill gem      = 20 lvl / no quality
        2. #1 + Gemcutter's Prism =   1  lvl / 20% quality

        Example:
        Faster Attack Support 10
        1. Faster Attack Support - Skill Gem which you can craft and trade on market
        2. 10 - Your expected profit value in Chaos
        """;
    return createMessageWithInlineKeyboard(message, text, getSubMenuWithSkills());
  }

  private SendMessage createMessageWithInlineKeyboard(Message message,
                                                      ReplyKeyboardMarkup keyboard) {
    final SendMessage sendMessage = generateSendMessage("""
            %s
            Greetings, Exile **%s**!
            I will tell you the most profitable ways to earn your first Divine.
            """.formatted(Emoji.WAVING_HAND, message.getFrom().getFirstName()),
        message.getChatId());
    if (keyboard != null) {
      sendMessage.setReplyMarkup(keyboard);
    }
    return sendMessage;
  }

  private EditMessageText createMessageWithInlineKeyboard(MaybeInaccessibleMessage message,
                                                          String text,
                                                          InlineKeyboardMarkup keyboard) {
    final EditMessageText result = generateEditMessage(message, text);
    if (keyboard != null) {
      result.setReplyMarkup(keyboard);
    }
    return result;
  }

  private SendMessage createMessageWithInlineKeyboard(long chatId,
                                                      InlineKeyboardMarkup keyboard) {
    final SendMessage sendMessage = generateSendMessage("What options do you want to choose ?", chatId);
    if (keyboard != null) {
      sendMessage.setReplyMarkup(keyboard);
    }
    return sendMessage;
  }

  private ReplyKeyboardMarkup getReplyMenu() {
    final ReplyKeyboardMarkup replyKeyboardMarkup = buildReplyKeyboard();
    List<KeyboardRow> keyboard = new ArrayList<>();
    KeyboardRow line1 = new KeyboardRow();
    KeyboardRow line2 = new KeyboardRow();
    line1.add(new KeyboardButton(MessageState.START.value));
    line1.add(new KeyboardButton(MessageState.SETTINGS.value));
    line2.add(new KeyboardButton(MessageState.FEEDBACK.value));
    keyboard.add(line1);
    keyboard.add(line2);
    replyKeyboardMarkup.setKeyboard(keyboard);
    replyKeyboardMarkup.setResizeKeyboard(true);
    return replyKeyboardMarkup;
  }

  private ReplyKeyboardMarkup buildReplyKeyboard() {
    return ReplyKeyboardMarkup.builder()
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  private InlineKeyboardMarkup getStartSubMenu() {
    List<InlineKeyboardRow> keyboard = new ArrayList<>();
    InlineKeyboardButton skillsBtn = InlineKeyboardButton.builder()
        .text("Skills")
        .callbackData(CallbackState.SKILLS.value)
        .build();
    InlineKeyboardButton blessingBtn = InlineKeyboardButton.builder()
        .text("Blessing Items")
        .callbackData(CallbackState.BLESSING_ITEMS.value)
        .build();
    List<InlineKeyboardButton> buttons = List.of(skillsBtn, blessingBtn);
    keyboard.add(new InlineKeyboardRow(buttons));
    return InlineKeyboardMarkup.builder()
        .keyboard(keyboard)
        .build();
  }

  private InlineKeyboardMarkup getSubMenuWithSkills() {
    List<InlineKeyboardRow> keyboard = new ArrayList<>();
    InlineKeyboardButton allBtn = InlineKeyboardButton.builder()
        .text("Analyze All Skills")
        .callbackData(CallbackState.ALL_SKILLS.value).build();
    List<InlineKeyboardButton> buttons = List.of(allBtn);
    keyboard.add(new InlineKeyboardRow(buttons));
    return InlineKeyboardMarkup.builder()
        .keyboard(keyboard)
        .build();
  }

  public InlineKeyboardMarkup keyboardWithRefresh() {
    InlineKeyboardMarkup markup = new InlineKeyboardMarkup(Collections.emptyList());
    List<InlineKeyboardRow> keyboard = new ArrayList<>();
    InlineKeyboardButton refreshBtn = InlineKeyboardButton.builder()
        .text(Emoji.REPEAT.value)
        .callbackData(CallbackState.REFRESH.value)
        .build();
    List<InlineKeyboardButton> buttons = List.of(refreshBtn);
    keyboard.add(new InlineKeyboardRow(buttons));
    markup.setKeyboard(keyboard);
    return markup;
  }

  public SendMessage generateSendMessage(String text,
                                         long chatId) {
    return SendMessage.builder()
        .chatId(chatId)
        .parseMode("Markdown")
        .text(text)
        .build();
  }

  public EditMessageText generateEditMessage(MaybeInaccessibleMessage message,
                                             String text) {
    return EditMessageText.builder()
        .chatId(message.getChatId())
        .messageId(message.getMessageId())
        .parseMode("Markdown")
        .text(text)
        .build();
  }

  public EditMessageText generateEditMessage(MaybeInaccessibleMessage message,
                                             String text,
                                             InlineKeyboardMarkup keyboard) {
    return EditMessageText.builder()
        .chatId(message.getChatId())
        .messageId(message.getMessageId())
        .parseMode("Markdown")
        .text(text)
        .replyMarkup(keyboard)
        .build();
  }
}
