package Main;

import Essence.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static Essence.GLOBALTOKENS.*;

public class sqlUsing  {
    //Method for adding new users to the database, it connects to the database,
    // executes a SQL query on the Person object and closes the connection to the database.
    public static String SqlAdd (Person pers) throws  Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = null;

        try {
            connection  = DriverManager.getConnection(
                    CONNECTION_TOSQL, SQ_LLOGIN, PASSWORD
            );
            Statement statement = connection.createStatement();

            int results = statement.executeUpdate(
                    "INSERT INTO `testTable` (`id`, `data_add`, `name`, `surname`, `mail`, `number`) " +
                            "VALUES (NULL,'"+pers.getDate()+"', '"+ pers.getName() +
                            "', '"+ pers.getSurnem() +"', '"+ pers.getMail() +"', '"+ pers.getNumb()+"')");
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            connection.close();
            return "ok";
        }

    }

    //The method executes a SQL query to update the database
    // by taking the record ID from the database
    public static void SqlUpdate (int id) throws  Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    CONNECTION_TOSQL, SQ_LLOGIN, PASSWORD
            );
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

    //The method accepts an array of Person type to be filled with
    // records from the database and returns the filled array
    public static ArrayList<Person> SqlWithOutAnswer (ArrayList<Person> userList) throws  Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    CONNECTION_TOSQL, SQ_LLOGIN, PASSWORD
            );
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
