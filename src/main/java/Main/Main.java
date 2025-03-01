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
    }
}
