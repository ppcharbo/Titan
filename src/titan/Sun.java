package titan;

import java.awt.Color;
import java.awt.Graphics2D;


public class Sun {
	int x;
	int y;
	
	public Sun(int xLT, int yLT) {
		this.x = xLT;
		this.y = yLT;
	}
	void draw(Graphics2D g2) {
		g2.setColor(new Color(255, 165, 0));
		g2.fillOval(x, y, 100, 100);
	}
}
