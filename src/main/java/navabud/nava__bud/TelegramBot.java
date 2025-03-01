package navabud.nava__bud;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Essence.Person;
import Main.sqlUsing;

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

import static Essence.GLOBALTOKENS.ADMINUSER;
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
            // Callback request processing
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String callbackData = callbackQuery.getData();
                for (String key : buttons.keySet()) {
                    if (key.equals(callbackData)) {
                        // Performing an action when the button is pressed

                        Main.sqlUsing.SqlUpdate(buttons.get(key));

                        // Creating an AnswerCallbackQuery object using setters
                        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQuery.getId());
                        answerCallbackQuery.setText("Действие выполнено!");

                        // Send response to callback request
                        telegramClient.execute(answerCallbackQuery);
                        buttons.remove(key);
                        System.out.println("AnswerCallbackQuery: " + answerCallbackQuery.getText());
                    }
                }

            }

            // Text message processing
            if (update.hasMessage() && update.getMessage().hasText()) {
                if ("/start".equals(update.getMessage().getText()) && (update.getMessage().getChatId() == ADMINUSER)) {
                    long chatId = update.getMessage().getChatId();

                    ArrayList<Person> list = new ArrayList<>();
                    sqlUsing.SqlWithOutAnswer(list);

                    // Sending messages to users
                    for (Person person : list) {
                        // Create a button
                        InlineKeyboardButton button = new InlineKeyboardButton("Ответил");
                        button.setCallbackData("button_click_"+person.getId());
                        buttons.put("button_click_"+person.getId(),person.getId());
                        // Create a keyboard row and add a button
                        InlineKeyboardRow row = new InlineKeyboardRow();
                        row.add(button);

                        // Create a keyboard layout and add a line
                        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(Collections.singletonList(row));

                        String messageText =  person.getName() + " " + person.getSurnem() +
                                "\n" + person.getMail() + "\n" + person.getNumb();

                        // Create and send a message
                        SendMessage sendMessage = SendMessage
                                .builder()
                                .chatId(chatId)
                                .text(messageText)
                                .build();
                        sendMessage.setReplyMarkup(markup);
                        telegramClient.execute(sendMessage);
                        System.out.println(chatId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
