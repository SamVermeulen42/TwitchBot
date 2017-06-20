package twitchbot.colorname;

import twitchbot.swingelements.NoAliasingLabel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import twitchbot.mvc.Listener;

public class ColorNameView extends JFrame implements Listener{

    private ColorName model;
    private JLabel[] labels;
    
    public ColorNameView(ColorName model, int fontSize) {
        super("ColorName");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        this.model = model;
        setBackground(new Color(42, 42, 42));
        model.addListener(this);
        
        String text = model.getText();
        int[][] colors = model.getColors();
        this.labels = new JLabel[text.length()];
        for (int i = 0; i < text.length(); i++) {
            this.labels[i] = new NoAliasingLabel("" + text.charAt(i));
            this.labels[i].setFont(new Font("serif", Font.BOLD, fontSize));
            this.labels[i].setBackground(Color.WHITE);
            this.labels[i].setForeground(new Color(colors[i][0], colors[i][1], colors[i][2]));
            add(this.labels[i]);
        }
        
        pack();
        setVisible(true);
    }

    @Override
    public void onChange() {
        int[][] colors = model.getColors();
        for (int i = 0; i < labels.length; i++) {
            this.labels[i].setForeground(new Color(colors[i][0], colors[i][1], colors[i][2]));
            this.labels[i].repaint();
        }
    }
}
