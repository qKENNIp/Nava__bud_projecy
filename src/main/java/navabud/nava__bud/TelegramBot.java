package navabud.nava__bud;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Essence.Person;
import Main.Main;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.*;

import static Essence.GLOBALTOKENS.BOT_TOKEN;

@WebServlet("/telegram-bot")
public class TelegramBot extends HttpServlet implements LongPollingSingleThreadUpdateConsumer {

    private TelegramClient telegramClient = new OkHttpTelegramClient(BOT_TOKEN);

    public TelegramBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {

        if (update.getMessage().getChatId().equals(BOT_TOKEN)) {

        }
        if (update.hasMessage() && update.getMessage().hasText()) {
        // Create your send messengeText object
            if (update.getMessage().getText().equals("/start")) {
                try {
                    long chatId = update.getMessage().getChatId();

                    // Создаем кнопку
                    InlineKeyboardButton button = new InlineKeyboardButton("Ответил");
                    button.setCallbackData("button_click");

                    CallbackQuery callbackQuery = update.getCallbackQuery();
                    String callbackData = callbackQuery.getData();
                    // Создаем строку клавиатуры и добавляем кнопку
                    InlineKeyboardRow row = new InlineKeyboardRow();
                    row.add(button);

                    // Создаем разметку клавиатуры и добавляем строку
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup(Collections.singletonList(row));

                    ArrayList <Person> list = new ArrayList<Person>();
                    Main.sqlUsing.SqlWithOutAnswer(list);

                    for (Person person : list) {
                        String messengeText =
                                ""+person.getId()+" "+person.getName()+" "+person.getSurnem()+
                                        "\n   "+person.getMail()+"\n   "+ person.getNumb();

                        SendMessage sendMessage = SendMessage
                                .builder()
                                .chatId(chatId)
                                .text(messengeText)
                                .build();
                        sendMessage.setReplyMarkup(markup);
                        // Execute it
                        telegramClient.execute(sendMessage);
                        if ("button_click".equals(callbackData)) {
                            Main.sqlUsing.SqlUpdate(person.getId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
