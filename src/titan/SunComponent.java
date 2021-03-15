package titan;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;


public class SunComponent extends JComponent {
	public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Sun sunny = new Sun(800, 450);
        sunny.draw(g2);
    }
}
