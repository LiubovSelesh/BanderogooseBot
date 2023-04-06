package org.example;

import com.sun.source.tree.IfTree;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
// DinnerAtHomeBot
// t.me/DinnerAtHomeBot.

public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new Bot());
    }
    @Override
    public String getBotUsername() {
        return "DinnerAtHomeBot";
    }
    @Override
    public String getBotToken() {
        return "6253642764:AAGv9dKuZ_1iqW0o34RYujFMcPyj-SDoQZg";
    }
    @Override
    public void onUpdateReceived(Update update) {
        Long chatID = getChatID(update);

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessage message = createMessage("Hey Sergio! \nWhen you will be at home?");

            message.setChatId(chatID);
            attachButtons(message, Map.of(
                    "Soon", "soon",
                    "I will be late", "late"

            ));
            sendApiMethodAsync(message);

        }

        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("soon")) {
                SendMessage message = createMessage("Wait for you?");
                message.setChatId(chatID);
                attachButtons(message, Map.of(
                        "Yes", "yes_wait",
                        "No","no_wait"
                ));
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("yes_wait")) {
                SendMessage message = createMessage("Will you eat?");
                message.setChatId(chatID);
                attachButtons(message, Map.of(
                        "Yes", "yes_eat",
                        "No", "no_eat"
                ));
                sendApiMethodAsync(message);
            } else if (update.getCallbackQuery().getData().equals("no_wait")) {
                SendMessage message = createMessage("You are the big kakaha");
                message.setChatId(chatID);
                attachButtons(message, Map.of(
                        "start", "sorry"
                ));
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("yes_eat")) {
                SendMessage message = createMessage("Nice");
                message.setChatId(chatID);
                sendApiMethodAsync(message);
//            }

            } else if (update.getCallbackQuery().getData().equals("no_eat")) {
                SendMessage message = createMessage("Then I eat without you");
                message.setChatId(chatID);
                sendApiMethodAsync(message);
            }
//            if (update.getCallbackQuery().getData().equals("yes")) {
//                SendMessage message = createMessage("Nice");
//                message.setChatId(chatID);
//            }

        }

    }
    public Long getChatID(Update update) {
        if (update.hasMessage()) {
            return  update.getMessage().getFrom().getId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }
    public SendMessage createMessage(String text) {
        SendMessage message = new SendMessage();
//        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setText(text);
        message.setParseMode("markdown");
        return message;
    }

    //BUTTON => btn
    public void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();
//            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setText(buttonName);
            button.setCallbackData(buttonValue);

            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

    }
}
