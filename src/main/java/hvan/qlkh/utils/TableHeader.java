package hvan.qlkh.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;

public class TableHeader extends JLabel {

    public TableHeader(String text) {
        super(text);
        setOpaque(true);
        setFont(new Font(getFont().getFontName(), Font.BOLD, 15));
        setBackground(new Color(246,251,249));
        setForeground(new Color(102, 102, 102));
        setBorder(null);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(TOP);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(new Color(230, 230, 230));
        graphics.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
}
