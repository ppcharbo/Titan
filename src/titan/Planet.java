package titan;

import java.awt.Color;
import java.awt.Graphics2D;


public class Planet {
	int x;
	int y;
	
	public Planet(int xLT, int yLT) {
		this.x = xLT;
		this.y = yLT;
	}
	public void draw(Graphics2D g2) {
		g2.setColor(new Color(255, 0, 0));
		g2.fillOval(x, y, 120, 120);
	}
}