package twitchbot.help;

import java.util.HashMap;
import java.util.Map;
import twitchbot.ChatConnection;
import twitchbot.CommandModuleInterface;

public class Help implements CommandModuleInterface {

    private ChatConnection connection;
    private Map<String, String> help;

    public Help(ChatConnection connection) {
        this.connection = connection;
        this.help = new HashMap<>();
        this.help.put("commands", "Commands gives a list of possible commands.");
        this.help.put("help", "Help explains a command, but it seems like you don't need the help.");
        this.help.put("name", "Name is a small game, it lets you change the colors of my name on stream. Use \'!name letterNumber r g b\' with r, g and b 0-255.");
    }
    
    @Override
    public void receive(String user, String[] command) {
        if (command.length != 1) {
            connection.send("Help explains a command, put another command after this: \'!help commands\'.");
        } else {
            if (help.containsKey(command[0])) {
                connection.send(help.get(command[0]));
            } else {
                connection.send("Command not found.");
            }
        }
    }
    
}
