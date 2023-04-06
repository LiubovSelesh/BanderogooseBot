package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
// t.me/BanderroGooseLSBot

public class Main extends TelegramLongPollingBot {
    private Map<Long, Integer> levels = new HashMap<>();


    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new Main());
//        System.out.println("Hello world!");
    }
    @Override
    public String getBotUsername() {
        return "BanderroGooseLSBot";
    }
    @Override
    public String getBotToken() {
        return "6216495034:AAFYCfos0ClH9S9rhACj9n90NoGgeap6ni4";
    }
    @Override
    public void onUpdateReceived(Update update) {
        Long chatID = getChatID(update);

         if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
     // send image leve 1
             sendImage("level-1", chatID);

     // send message
             SendMessage message = createMessage("Ґа-ґа-ґа!\n" +
                     "Вітаємо у боті біолабораторії «Батько наш Бандера».\n" +
                     "\n" +
                     "Ти отримуєш гусака №71\n" +
                     "\n" +
                     "Цей бот ми створили для того, щоб твій гусак прокачався з рівня звичайної свійської худоби до рівня біологічної зброї, здатної нищити ворога. \n" +
                     "\n" +
                     "Щоб звичайний гусак перетворився на бандерогусака, тобі необхідно:\n" +
                     "✔\uFE0Fвиконувати завдання\n" +
                     "✔\uFE0Fпереходити на наступні рівні\n" +
                     "✔\uFE0Fзаробити достатню кількість монет, щоб придбати Джавеліну і зробити свєрхтра-та-та.\n" +
                     "\n" +
                     "*Гусак звичайний.* Стартовий рівень.\n" +
                     "Бонус: 5 монет.\n" +
                     "Обери завдання, щоб перейти на наступний рівень");
             message.setChatId(chatID);

             attachButtons(message, Map.of(
                     "Сплести маскувальну сітку (+15 монет)", "level_1_task",
                     "Зібрати кошти патріотичними піснями (+15 монет)", "level_1_task",
                     "Вступити в Міністерство Мемів України (+15 монет)", "level_1_task"
             ));

             sendApiMethodAsync(message);
         }
//            SendMessage message = createMessage("Привіт!");
//            message.setChatId(chatID);
//            attachButtons(message, Map.of(
//                "Слава Україні!", "glory_for_ukraine"
//
//            ));
//            sendApiMethodAsync(message);
//        }
//
//        if (update.hasCallbackQuery()) {
//            if (update.getCallbackQuery().getData().equals("glory_for_ukraine")) {
//                SendMessage message = createMessage("Героям Слава!");
//                message.setChatId(chatID);
//                attachButtons(message, Map.of(
//                        "Слава Нації", "glory_for_nation"
//                ));
//                sendApiMethodAsync(message);
//            }
//
//            if (update.getCallbackQuery().getData().equals("glory_for_nation")) {
//                SendMessage message = createMessage("Смерть ворогам");
//                message.setChatId(chatID);
//                sendApiMethodAsync(message);
//            }
//        }

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
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
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
            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);

            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }

    public void sendImage(String name, Long chatID) {
        SendAnimation animation = new SendAnimation();

        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File("images/" + name + ".gif"));

        animation.setAnimation(inputFile);
        animation.setChatId(chatID);

        executeAsync(animation);
    }
    public int getLevel(Long chatId) {
        return levels.getOrDefault(chatId, 1);
    }

    public void setLevel(Long chatID, int level) {

        levels.put(chatID, level);
    }
}
