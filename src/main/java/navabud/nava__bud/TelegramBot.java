package navabud.nava__bud;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static Essence.GLOBALTOKENS.BOT_TOKEN;

@WebServlet("/telegram-bot")
public class TelegramBot extends HttpServlet implements LongPollingSingleThreadUpdateConsumer {

    private TelegramClient telegramClient = new OkHttpTelegramClient(BOT_TOKEN);

    public TelegramBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
        // Create your send messengeText object
            String messengeText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage sendMessage =  SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(messengeText)
                    .build();
            try {
                // Execute it
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Чтение JSON-данных, полученных от Telegram (данные о нажатой кнопке)
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }


        // Парсим полученные данные
        JSONParser parser = new JSONParser();
        try {
            JSONObject update = (JSONObject) parser.parse(sb.toString());

            // Извлечение данных из обновления
            JSONObject message = (JSONObject) update.get("message");
            if (message == null) {
                JSONObject callbackQuery = (JSONObject) update.get("callback_query");
                if (callbackQuery != null) {
                    message = (JSONObject) callbackQuery.get("message");
                }
            }

            if (message != null) {
                String chatId = ((JSONObject) message.get("chat")).get("id").toString();
                String text = (String) message.get("text");

                // Если это команда /start, отправляем сообщение с кнопками
                if ("/start".equals(text)) {
                    sendStartMessage(chatId);
                } else if ("Нажми меня".equals(text)) {
                    sendMessage(chatId, "Вы нажали на кнопку!");
                } else {
                    sendMessage(chatId, "Привет! Выберите действие.");
                    sendInlineKeyboard(chatId);
                }
            }
        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка при парсинге данных");
        }
    }

    private void sendStartMessage(String chatId) throws IOException {
        // Отправляем сообщение с кнопками при команде /start
        sendMessage(chatId, "Добро пожаловать! Выберите одну из опций:");
        sendInlineKeyboard(chatId);
    }


    private void sendMessage(String chatId, String text) throws IOException {
        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        JSONObject messageObj = new JSONObject();
        messageObj.put("chat_id", chatId);
        messageObj.put("text", text);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = messageObj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Получаем ответ от Telegram API
        int responseCode = connection.getResponseCode();
        System.out.println("Ответ от Telegram API: " + responseCode);
    }


        private void sendInlineKeyboard(String chatId) throws IOException {
        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Создание клавиатуры с кнопками
        JSONObject button1 = new JSONObject();
        button1.put("text", "Нажми меня");
        button1.put("callback_data", "button_click");

        JSONObject button2 = new JSONObject();
        button2.put("text", "Другое действие");
        button2.put("callback_data", "other_action");

        JSONObject replyMarkup = new JSONObject();
        replyMarkup.put("inline_keyboard", new Object[] {
                new Object[] {button1},  // Кнопка 1
                new Object[] {button2}   // Кнопка 2
        });

        // Отправляем сообщение с кнопками
        JSONObject messageObj = new JSONObject();
        messageObj.put("chat_id", chatId);
        messageObj.put("text", "Выберите действие:");
        messageObj.put("reply_markup", replyMarkup);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = messageObj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        connection.getResponseCode();  // Отправка запроса
    }*/
}
