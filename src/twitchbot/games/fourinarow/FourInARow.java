package twitchbot.games.fourinarow;

import twitchbot.CommandModuleInterface;
import twitchbot.mvc.Model;

public class FourInARow extends Model implements CommandModuleInterface {

    private int firstTurn;
    
    private int[][] field;
    private int curTurn;
    private boolean listening;

    public FourInARow() {
        this.field = new int[7][6];
        this.firstTurn = 0;
        this.listening = false;
        this.curTurn = this.firstTurn;
    }
    
    public void myMove(int columnId) {
        if (curTurn == 0) {
            move(columnId - 1);
        }
    }
    
    @Override
    public void receive(String user, String[] command) {
        if (curTurn == 1 && command.length == 1) {
            int column = Integer.valueOf(command[0]) - 1;
            move(column);
        }
    }
    
    private void move(int columnId) {
        if (columnId >= 0 && columnId < 7) {
            int[] column = field[columnId];
        for (int i = 0; i < column.length; i++) {
            if (column[i] != 0) {
                column[i] = curTurn + 1;
                break;
            }
        }
        this.curTurn = (curTurn + 1) % 2;
        notifyListeners();
        }
    }
    
}
