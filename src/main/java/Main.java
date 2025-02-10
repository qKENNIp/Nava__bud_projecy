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
            Connection connection  = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test",
                    "root", "ServBay.dev");
            Statement statement = connection.createStatement();

            int results = statement.executeUpdate(
                    "INSERT INTO `testTable` (`id`, `name`, `surnem`, `mail`, `numb`) " +
                            "VALUES (NULL, '"+ pers.getName() +"', '"+ pers.getSurnem() +"', '"+ pers.getMail() +"', '"+ pers.getNumb()+"')");

            connection.close();
        }

    }
    public class JsonUsing {

        public static void JsontoPerson () throws  Exception{
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader("src/test.json");

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONObject jsonObject1 = (JSONObject) jsonObject.get("address");


            String name = (String) jsonObject1.get("name");
            String surname = (String) jsonObject1.get("surname");
            String email = (String) jsonObject1.get("email");
            Integer numbr = (Integer) jsonObject1.get("numbr");


            System.out.println("Key: " + jsonObject.keySet());

            Person per = new Person(name,surname,email,numbr);
            sqlUsing.SqlAdd(per);
            System.out.println("success");

        }
    }
}
