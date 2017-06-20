package twitchbot.swingelements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

public class NoAliasingLabel extends JLabel {

    private String text;
    
    public NoAliasingLabel(String string) {
        super(string);
        this.text = string;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.drawString(text, 0, g2d.getFont().getSize());
    }
}
