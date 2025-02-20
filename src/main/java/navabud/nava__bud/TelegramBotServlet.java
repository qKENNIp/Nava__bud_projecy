package navabud.nava__bud;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

public class TelegramBotServlet extends HttpServlet {

    private static final String BOT_TOKEN = "8076694475:AAHZ_l58p_Ey7iiALDdJrvwViro8naX-GJM";  // Замените на ваш токен

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // URL для получения информации о боте
        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/getMe";

        // Создаем HTTP соединение
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");

        // Устанавливаем необходимые заголовки (если нужно)
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Получаем ответ от Telegram API
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Если ответ успешный
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder responseContent = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            // Устанавливаем тип контента и отправляем ответ
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(responseContent.toString());
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при запросе к Telegram API");
        }
    }
}
