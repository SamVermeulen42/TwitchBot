package twitchbot.colorname;

import java.util.Random;
import twitchbot.CommandModuleInterface;
import twitchbot.mvc.Model;

public class ColorName extends Model implements CommandModuleInterface {

    private String text;
    private int[][] colors;
    
    public ColorName(String text) {
        this.text = text;
        this.colors = new int[text.length()][3];
        randomize();
    }
    
    private void randomize() {
        Random r = new Random(System.nanoTime());
        for (int i = 0; i < colors.length; i++) {
            colors[i][0] = r.nextInt(256);
            colors[i][1] = r.nextInt(256);
            colors[i][2] = r.nextInt(256);
        }
    }

    public String getText() {
        return text;
    }

    public int[][] getColors() {
        return colors;
    }

    @Override
    public void receive(String user, String[] command) {
        if (command.length == 4) {
            System.out.println("hey i got a move for the game");
            int index = Integer.valueOf(command[0]) - 1;
            int r = Integer.valueOf(command[1]);
            int g = Integer.valueOf(command[2]);
            int b = Integer.valueOf(command[3]);
            if (index >= 0 && index < colors.length && r >= 0 && r < 256 && 
                    g >= 0 && g < 256 && b >= 0 && b < 256) {
                this.colors[index][0] = r;
                this.colors[index][1] = g;
                this.colors[index][2] = b;
                notifyListeners();
                System.out.println("valid");
            }
        }
    }
}
