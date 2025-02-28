package Main;

import Essence.Person;
import navabud.nava__bud.TelegramBot;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import static Essence.GLOBALTOKENS.*;

public class Main {

    public static void main(String[] args) throws Exception {

        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

            botsApplication.registerBot(BOT_TOKEN, new TelegramBot(BOT_TOKEN));
//            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(sqlUsing.SqlWithOutAnswer(new ArrayList<Person>()).get(0));
    }

    public class sqlUsing  {

        public static String SqlAdd (Person pers) throws  Exception{
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
                return "ok";
            }

        }
        public static void SqlUpdate (int id) throws  Exception {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(
                        CONNECTION_TOSQL, SQ_LLOGIN, PASSWORD);
                Statement statement = connection.createStatement();

                int results = statement.executeUpdate(
                        "UPDATE `testTable` SET `answer` = '1' WHERE `testTable`.`id` = "+id
                );

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }

        public static ArrayList<Person> SqlWithOutAnswer (ArrayList<Person> userList) throws  Exception {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(
                        CONNECTION_TOSQL, SQ_LLOGIN, PASSWORD);
                Statement statement = connection.createStatement();

                ResultSet results = statement.executeQuery(
                        "SELECT * FROM `testTable` WHERE `answer` = 0"
                );
                //System.out.println("id" + "\t name" + "\t surname" + "\t mail" + "\t\t\t\t number");
                while (results.next()) {
                    Integer id = results.getInt(1);
                    String name = results.getString(3);
                    String surname = results.getString(4);
                    String mail = results.getString(5);
                    String number = results.getString(6);

                    userList.add(new Person(id,name,surname,mail,number));
                    //System.out.println(id + "\t "+ name + "\t "+ surname + "\t "+ mail + "\t "+ number);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
                return userList;
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
