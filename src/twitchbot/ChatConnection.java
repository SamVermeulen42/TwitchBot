package twitchbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatConnection implements Runnable {

    private final Thread t;
    
    private final String server = "irc.chat.twitch.tv";
    private final int port = 6667;
    private final String username = "BOT NAME";
    private final String pw = "ENTER PW HERE"; // http://twitchapps.com/tmi/
    private final String channel = "CHANNEL NAME";
    
    private BufferedWriter writer;
    private BufferedReader reader;
    private boolean running = true;
    private Map<String, CommandModuleInterface> commands = new HashMap<>();

    public ChatConnection() {
        this.t = new Thread(this, "Chat receiver thread");
    }
    
    public void register(String command, CommandModuleInterface module) {
        commands.put(command, module);
    }
    
    public void send(String msg) {
        try {
            writer.write("PRIVMSG #" + channel + " :" + msg + "\r\n");
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ChatConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start() {
        t.start();
    }
    
    public void halt() {
        running = false;
        t.interrupt();
    }
    
    @Override
    public void run() {
        while (running) {
            connect();
            listen();
            System.out.println("Lost connection: reconnecting.");
        }
    }
    
    private void connect() {
        try {
            Socket socket = new Socket(server, port);
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            
            System.out.println("Sending login info");
            writer.write("USER " + username + "\r\n");
            writer.write("PASS " + pw + "\r\n");
            writer.write("NICK " + username + "\r\n");
            writer.flush();
            
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                if (line.indexOf("004") >= 0) {
                    // We are now logged in.
                    System.out.println("Logged in.");
                    break;
                }
                else if (line.indexOf("433") >= 0) {
                    System.out.println("Nickname is already in use.");
                    return;
                }
                line = reader.readLine();
            }
            
            writer.write("JOIN #" + channel + "\r\n");
            writer.write("PRIVMSG #" + channel + " :" + "Let the games begin!!!" + "\r\n");
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ChatConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listen() {
        try {
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                if (line.startsWith("PING")) {
                    System.out.println("Hey, I got a ping!!");
                    writer.write("PONG :tmi.twitch.tv\r\n");
                    writer.flush();
                } else {
                    String[] splitted = line.split(" ");
                    if (splitted.length > 1 && splitted[1].equals("PRIVMSG")) {
                        splitted = line.split("#");
                        splitted = splitted[1].split(" ");
                        if (splitted[1].startsWith(":!")) {
                            String user = splitted[0];
                            String command = splitted[1].substring(2);
                            String[] message = new String[splitted.length-2];
                            for (int i = 2; i < splitted.length; i++) {
                                message[i-2] = splitted[i];
                            }
                            CommandModuleInterface module = commands.get(command);
                            if (module != null) {
                                module.receive(user, message);
                            }
                        }
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done listening (got none)");
    }
    
}
