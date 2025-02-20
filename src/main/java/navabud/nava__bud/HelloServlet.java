package navabud.nava__bud;

import java.io.*;
import Essence.Person;
import Main.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/submit")
public class HelloServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Устанавливаем кодировку для чтения и отправки данных
        response.setHeader("Access-Control-Allow-Origin", "*"); // Разрешаем запросы с любого источника
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // Чтение тела запроса в формате JSON
        BufferedReader reader = request.getReader();
        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }
        // Парсинг JSON-строки
        JSONParser parser = new JSONParser();
        JSONObject jsonRequest = null;
        try {
            jsonRequest = (JSONObject) parser.parse(jsonString.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Извлечение данных из JSON
        String name = (String) jsonRequest.get("name");
        String surname = (String) jsonRequest.get("surname");
        String email = (String) jsonRequest.get("email");
        String phone = (String) jsonRequest.get("phone");
        String consent = (String) jsonRequest.get("consent");


        // Логика обработки данных
        // Здесь просто выводим данные в консоль, но можно сделать и больше.
        System.out.print("Received data: ");
        System.out.print(" Name: " + name);
        System.out.print(" Surname: " + surname);
        System.out.print(" Email: " + email);
        System.out.print(" Phone: " + phone);
        System.out.println(" Consent: " + consent);

        // Создаем JSON-объект для ответа
        JSONObject jsonResponse = new JSONObject();

        // Проверка валидности данных
        if (name != null && surname != null && email != null && phone != null && consent != null) {
            jsonResponse.put("success", true);
        } else {
            // Если данные не валидны, отправляем success: false
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Некоторые данные отсутствуют.");
        }
        try {
            Main.sqlUsing.SqlAdd(new Person(name,surname,email,phone));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Отправка ответа обратно клиенту
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();

    }
    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject errorJson = new JSONObject();
        errorJson.put("error", message);
        response.getWriter().write(errorJson.toJSONString());
    }
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");//change on actual link
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
