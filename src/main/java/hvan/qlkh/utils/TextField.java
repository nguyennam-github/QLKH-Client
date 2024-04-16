/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.utils;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JTextField;

/**
 *
 * @author super
 */
public class TextField extends JTextField {

    private final int radius;
    private final Color color;

    public TextField(int radius, Color color) {
        super();
        this.radius = radius;
        this.color = color;
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        super.paintComponent(g);
    }
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(color);
        g.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, radius, radius);
    }
}
