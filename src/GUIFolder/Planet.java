package GUIFolder;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Planet {

	private double mass = 0;
	private int dia = 0;
	private double x = 0;
	private double y = 0;
	private double vx = 0;
	private double vy = 0;
	private double a = 0;
	private double dX = 0;
	private double dY = 0;
	private double distance = 0;
	private double initial = 1000;
	private double max = 0;
	public String label;
	private JPanel parent;
	private final boolean DEBUG = false;

	boolean visible;
	Color color;

	public Planet(JPanel parento, String label, int r, int g, int b, double xCoordinate, double yCoordinate,
			int diameter, double vx, double vy, double mass) {

		this.setParent(parento);
		this.label = label;
		color = new Color(r, g, b);
		x = xCoordinate;
		y = yCoordinate;
		this.dia = diameter;
		this.vx = vx;
		this.vy = vy;
		this.mass = mass;

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getMass() {
		return mass;
	}

	public int getDiameter() {
		return dia;
	}

	public void updateLocation() {
		x = x + vx;
		y = y + vy;
	}

	public void update(double xNew, double yNew, double mass) {
		distance = Math.sqrt((xNew - x) * (xNew - x) + (yNew - y) * (yNew - y));
		initial = Math.min(distance, initial);
		max = Math.max(distance, max);

		a = mass / distance / distance;

		dX = (xNew - x) / distance;
		dY = (yNew - y) / distance;

		vx = vx + (dX * a);
		vy = vy + (dY * a);
		updateLocation();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void draw(Graphics g, double size, int windth, int height) {
		g.setColor(color);

		int x = (int) (windth + (this.x - dia / 2 - windth) * size);
		int y = (int) (height + (this.y - dia / 2 - height) * size);

		if(DEBUG) {
			System.out.println("x: " + x);
			System.out.println("y: " + y);
		}
		g.fillOval(x, y, (int) (dia * size), (int) (dia * size));
		

	}

	public JPanel getParent() {
		return parent;
	}

	public void setParent(JPanel parent) {
		this.parent = parent;
	}
	
	 public void translate(double d, double e) {
	        this.x += d;
	        this.y += e;
	        if(DEBUG) {
	        	System.out.println("Translated: " + label +  " to x: " + x + " and y: " + y);
	        }
	    }


}
