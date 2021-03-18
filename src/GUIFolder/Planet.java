package GUIFolder;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

public class Planet {

	private int mass = 0;
	private int diameter = 0;
	private double x = 0;
	private double y = 0;
	private double vx = 0;
	private double vy = 0;
	// private double speed = 0;
	Color color;
	private double a = 0;
	private double dirX = 0;
	private double dirY = 0;
	private double distance = 0;
	private double initial = 1000;
	private double max = 0;
	boolean visible;
	private String label;
	private JPanel parent;

	public Planet(JPanel parent, String label, int r, int g, int b, double xCoordinate, double yCoordinate,
			int diameter, double vx, double vy, int mass) {

		this.parent = parent;
		this.label = label;
		color = new Color(r, g, b);
		x = xCoordinate;
		y = yCoordinate;
		this.diameter = diameter;
		this.vx = vx;
		this.vy = vy;
		this.mass = mass;

	}

	public double getXPosition() {
		return x;
	}

	public double getYPosition() {
		return y;
	}

	public int getMass() {
		return mass;
	}

	public int getDiameter() {
		return diameter;
	}

	public void move() {
		x = x + vx;
		y = y + vy;
	}

	public void update(double StarX, double StarY, int StarMass) {
		distance = Math.sqrt((StarX - x) * (StarX - x) + (StarY - y) * (StarY - y));
		initial = Math.min(distance, initial);
		max = Math.max(distance, max);

		a = StarMass / distance / distance;

		dirX = (StarX - x) / distance;
		dirY = (StarY - y) / distance;

		vx = vx +(dirX*a);
		vy = vy +(dirY*a);
		move();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

//	@Deprecated
//	public void draw(Graphics g, double size) {
//		g.setColor(color);
//
//		g.fillOval((int) (650 + (xLoc - diameter / 2 - 650) * size), (int) (500 + (yLoc - diameter / 2 - 500) * size), (int) (diameter * size), (int) (diameter * size));
//	}

	public void draw(Graphics g, double size, int windth, int height) {
		g.setColor(color);

		int x = (int) (windth + (this.x - diameter / 2 - windth) * size);
		int y = (int) (height + (this.y - diameter / 2 - height) * size);

		System.out.println(" x = " + x + " y= " + y + " windth =" + windth + " height =" + height);
		g.fillOval(x, y, (int) (diameter * size), (int) (diameter * size));

	}

}
