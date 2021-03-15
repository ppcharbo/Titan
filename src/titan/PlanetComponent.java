package titan;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
//import titan.Planet;

public class PlanetComponent extends JComponent {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private int r;
	private int g;
	private int b;

	public PlanetComponent(int x, int y, int width, int height, int r, int g, int b) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.r = r;
		this.g = g;
		this.b = b;
	}
	public void paintComponent(Graphics g1) {
		Graphics2D g2 = (Graphics2D) g1;
		Planet planet = new Planet(x, y, width, height, r, g, b);
		planet.draw(g2);
	}
}
