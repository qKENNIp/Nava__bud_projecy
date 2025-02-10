package navabud.nava__bud;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
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
        // Читаем JSON из тела запроса
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        try {
            // Парсим JSON с помощью json-simple
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) parser.parse(jsonBuffer.toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Проверяем, есть ли ключ "UserData"
            JSONObject userData = (JSONObject) jsonObject.get("UserData");
            if (userData == null) {
                sendError(response, "Invalid JSON format: Missing 'UserData'");
                return;
            }

            // Извлекаем значения из JSON
            String name = (String) userData.get("name");
            String surname = (String) userData.get("surname");
            String email = (String) userData.get("email");
            String number = (String) userData.get("number"); // Ошибка в ключе сохраняется

            // Логируем полученные данные
            System.out.println("Received User Data: " + name + " " + surname + ", " + email + ", " + number );

            // Создаем JSON-ответ
            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "User data received successfully");

            // Отправляем ответ клиенту
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responseJson.toJSONString());

        } catch (Exception e) {
            sendError(response, "Invalid JSON format: " + e.getMessage());
        }
    }
    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject errorJson = new JSONObject();
        errorJson.put("error", message);
        response.getWriter().write(errorJson.toJSONString());
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");//change on actual link
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        // Обрабатываем запрос
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"success\"}");
        System.out.println("request: " + request.getRequestURI());

    }
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");//change on actual link
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}