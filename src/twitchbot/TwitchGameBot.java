package twitchbot;

import twitchbot.colorname.ColorName;
import twitchbot.colorname.ColorNameView;
import twitchbot.commands.Commands;
import twitchbot.games.fourinarow.FourInARow;
import twitchbot.help.Help;

public class TwitchGameBot {

    public static void main(String[] args) {
        // create a connection with the chat
        ChatConnection con = new ChatConnection();
        
        // commands:
        // !name
        ColorName cn = new ColorName("SamVermeulen42");
        con.register("name", cn);
        ColorNameView cnView = new ColorNameView(cn, 60);
        
        // !commands
        Commands com = new Commands(con);
        con.register("commands", com);
        
        // !help
        Help help = new Help(con);
        con.register("help", help);
        
        // !game
        FourInARow four = new FourInARow();
        con.register("four", four);
        
        con.start();
    }
    
}
