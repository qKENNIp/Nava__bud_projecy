package navabud.nava__bud;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Essence.Person;
import Main.Main;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
                    SendMessage message;
                    String buttonValue ="buton";

                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText("button");
                    button.setCallbackData(buttonValue);

                    keyboard.add(List.of(button));

                    markup.setKeyboard(keyboard);
                    message.setReplyMarkup(markup) = new SendMessage()


                    ArrayList <Person> list = new ArrayList<Person>();
                    Main.sqlUsing.SqlWithOutAnswer(list);

                    for (Person person : list) {
                        String messengeText =
                                ""+person.getId()+" "+person.getName()+" "+person.getSurnem()+
                                        "\n   "+person.getMail()+"\n   "+ person.getNumb();

                        SendMessage sendMessage =  SendMessage
                                .builder()
                                .chatId(chatId)
                                .text(messengeText)
                                .build();

                        // Execute it
                        telegramClient.execute(sendMessage);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
