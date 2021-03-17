package GUIFolder;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

public class Planet {

	private int mass = 0;
	private int diameter = 0;
	private double xLoc = 0;
	private double yLoc = 0;
	private double velX = 0;
	private double velY = 0;
	//private double speed = 0;
	Color color;
	private double acceleration = 0;
	private double dirX = 0;
	private double dirY = 0;
	private double distance = 0;
	private double initial = 1000;
	private double max = 0;
	boolean visible;
	private String label;
	private JPanel parent;

	public Planet(JPanel parent, String label, int r, int g, int b, double xCoordinate, double yCoordinate, int diameter, double vx, double vy, int mass) {

		this.parent = parent;
		this.label = label;
		color = new Color(r, g, b);
		xLoc = xCoordinate;
		yLoc = yCoordinate;
		this.diameter = diameter;
		velX = vx;
		velY = vy;
		this.mass = mass;

	}

	/*
	public Planet(double x, double y, double xVelocity, double yVelocity, int bodyMass, int bodyDiameter, Color bodyColor)
	{
	   xLoc = x;
	   yLoc = y;
	   velX = xVelocity;
	   velY = yVelocity;
	   	mass = bodyMass;
	    diameter = bodyDiameter;
	   color = bodyColor;
	}
	*/
	public double getXPosition() {
		return xLoc;
	}

	public double getYPosition() {
		return yLoc;
	}

	public int getMass() {
		return mass;
	}

	public int getDiameter() {
		return diameter;
	}

	public void move() {
		xLoc += velX;
		yLoc += velY;
	}

	public void update(double StarX, double StarY, int StarMass) {
		distance = Math.sqrt((StarX - xLoc) * (StarX - xLoc) + (StarY - yLoc) * (StarY - yLoc));
		initial = Math.min(distance, initial);
		max = Math.max(distance, max);

		acceleration = StarMass / distance / distance;

		dirX = (StarX - xLoc) / distance;
		dirY = (StarY - yLoc) / distance;

		velX += dirX * acceleration;
		velY += dirY * acceleration;
		move();

	}

	@Deprecated
	public void draw(Graphics g, double size) {
		g.setColor(color);

		g.fillOval((int) (650 + (xLoc - diameter / 2 - 650) * size), (int) (500 + (yLoc - diameter / 2 - 500) * size), (int) (diameter * size), (int) (diameter * size));
	}

	public void draw(Graphics g, double size, int windth, int height) {
		g.setColor(color);
 
			int x = (int) (windth + (xLoc - diameter / 2 - windth) * size);
			int y = (int) (height + (yLoc - diameter / 2 - height) * size);

			System.out.println(" x = " + x + " y= " + y + " windth =" + windth + " height =" + height);
			g.fillOval(x, y, (int) (diameter * size), (int) (diameter * size));
	
		
	}

}
