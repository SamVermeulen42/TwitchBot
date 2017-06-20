package twitchbot.commands;

import twitchbot.ChatConnection;
import twitchbot.CommandModuleInterface;

public class Commands implements CommandModuleInterface {

    private ChatConnection connection;

    public Commands(ChatConnection connection) {
        this.connection = connection;
    }
    
    @Override
    public void receive(String user, String[] command) {
        this.connection.send("Possible commands: "
                + "!commands, "
                + "!help, "
                + "!name");
    }
    
}
