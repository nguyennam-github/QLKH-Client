package hvan.qlkh.utils;

import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 *
 * @author Nguyễn Phan Hoài Nam
 */

public class ScrollBar extends JScrollBar {

    public ScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(5, 5));
        setOpaque(false);
        setUnitIncrement(20);
    }
}
