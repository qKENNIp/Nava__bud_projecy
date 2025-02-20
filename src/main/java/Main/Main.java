package Main;

import Essence.Person;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

    }

    public class sqlUsing  {

        public static void SqlAdd (Person pers) throws  Exception{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection  = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test",
                    "root", "ServBay.dev");
            Statement statement = connection.createStatement();

            int results = statement.executeUpdate(
                    "INSERT INTO `testTable` (`id`, `data_add`, `name`, `surname`, `mail`, `number`) " +
                            "VALUES (NULL,'"+pers.getDate()+"', '"+ pers.getName() +"', '"+ pers.getSurnem() +"', '"+ pers.getMail() +"', '"+ pers.getNumb()+"')");
           // "INSERT INTO `testTable` (`id`, `data_add`, `name`, `surname`, `mail`, `number`) VALUES (NULL, '', '', '', '', '')"
            connection.close();
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
