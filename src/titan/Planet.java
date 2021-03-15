package titan;

import java.awt.Color;
import java.awt.Graphics2D;

public class Planet {
	//coordinates
	private int x;
	private int y;
	private int width;
	private int height;
	
	//colours
	private int r;
	private int g;
	private int b;

	public Planet(int xLT, int yLT, int width, int height, int r, int g, int b) {
		this.x = xLT;
		this.y = yLT;
		this.width = width;
		this.height = height;
		
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void draw(Graphics2D g2) {
		g2.setColor(new Color(255, 0, 0));
		g2.fillOval(x, y, 120, 120);
	}
}
