package navabud.nava__bud;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Essence.Person;
import Main.Main;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.*;

import static Essence.GLOBALTOKENS.BOT_TOKEN;

@WebServlet("/telegram-bot")
public class TelegramBot extends HttpServlet implements LongPollingSingleThreadUpdateConsumer {

    private TelegramClient telegramClient = new OkHttpTelegramClient(BOT_TOKEN);
    private HashMap<String, Integer> buttons = new HashMap <String, Integer>();

    public TelegramBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        try {
            // Обработка callback-запроса
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String callbackData = callbackQuery.getData();
                for (String key : buttons.keySet()) {
                    if (key.equals(callbackData)) {
                        // Выполнение действия при нажатии кнопки

                        Main.sqlUsing.SqlUpdate(buttons.get(key));  // Ваш метод для обработки

                        // Создание объекта AnswerCallbackQuery с помощью сеттеров
                        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQuery.getId());
                        answerCallbackQuery.setText("Действие выполнено!");

                        // Отправка ответа на callback-запрос
                        telegramClient.execute(answerCallbackQuery);
                        buttons.remove(key);
                        System.out.println("AnswerCallbackQuery: " + answerCallbackQuery.getText());
                    }
                }

            }

            // Обработка текстовых сообщений
            if (update.hasMessage() && update.getMessage().hasText()) {
                if ("/start".equals(update.getMessage().getText())) {
                    long chatId = update.getMessage().getChatId();

                    ArrayList<Person> list = new ArrayList<>();
                    Main.sqlUsing.SqlWithOutAnswer(list);

                    // Отправляем сообщения пользователям
                    for (Person person : list) {
                        // Создаем кнопку
                        InlineKeyboardButton button = new InlineKeyboardButton("Ответил");
                        button.setCallbackData("button_click_"+person.getId());
                        buttons.put("button_click_"+person.getId(),person.getId());
                        // Создаем строку клавиатуры и добавляем кнопку
                        InlineKeyboardRow row = new InlineKeyboardRow();
                        row.add(button);

                        // Создаем разметку клавиатуры и добавляем строку
                        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(Collections.singletonList(row));

                        String messageText =  person.getName() + " " + person.getSurnem() +
                                "\n   " + person.getMail() + "\n   " + person.getNumb();

                        SendMessage sendMessage = SendMessage.builder()
                                .chatId(chatId)
                                .text(messageText)
                                .build();
                        sendMessage.setReplyMarkup(markup);
                        telegramClient.execute(sendMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
