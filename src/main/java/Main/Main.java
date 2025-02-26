package Main;

import Essence.Person;
import navabud.nava__bud.TelegramBot;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import static Essence.GLOBALTOKENS.*;

public class Main {

    public static void main(String[] args) {

        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

            botsApplication.registerBot(BOT_TOKEN, new TelegramBot(BOT_TOKEN));

            System.out.println("MyAmazingBot successfully started!");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class sqlUsing  {

        public static void SqlAdd (Person pers) throws  Exception{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = null;

            try {
                connection  = DriverManager.getConnection(
                        CONNECTION_TOSQL, SQ_LLOGIN, PASSWORD);
                Statement statement = connection.createStatement();

                int results = statement.executeUpdate(
                        "INSERT INTO `testTable` (`id`, `data_add`, `name`, `surname`, `mail`, `number`) " +
                                "VALUES (NULL,'"+pers.getDate()+"', '"+ pers.getName() +"', '"+ pers.getSurnem() +"', '"+ pers.getMail() +"', '"+ pers.getNumb()+"')");
                connection.close();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                connection.close();
            }

        }
        public static void SqlUpdate (String id) throws  Exception {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(
                        CONNECTION_TOSQL, SQ_LLOGIN, PASSWORD);
                Statement statement = connection.createStatement();

                int results = statement.executeUpdate(
                        "UPDATE `testTable` SET `id`='"+id+"',`answer`='1' WHERE 1"
                );

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }

    }
    public class JsonUsing {

        public static void JsontoPerson () throws  Exception{
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader("src/test.json");

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

        }
    }
}
