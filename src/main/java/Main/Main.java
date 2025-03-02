package Main;

import navabud.nava__bud.TelegramBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

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
