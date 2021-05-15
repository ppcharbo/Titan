package titan.GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PlanetGUI {

	private int dia = 0;
	private double x = 0;
	private double y = 0;
	public String label;
	private JPanel parent;
	private final boolean DEBUG = false;

	boolean visible;
	Color color;

	public PlanetGUI(JPanel parento, String label, int r, int g, int b, Vector3d position, double diameter) {

		this.setParent(parento);
		this.label = label;
		color = new Color(r, g, b);
		x = (position.getX()/(10E9));
		y = (position.getY()/(10E9));
		//this.dia = (int) (diameter/1E5);
		
		
		if(label.equals("SUN")) {
			this.dia = (int) (diameter/(1E6));
		}
		if(label.equals("TITAN") || label.equals("MARS") || label.equals("MERCURY") || label.equals("VENUS") || label.equals("EARTH")) {
			this.dia = (int) (diameter/(10E4));
		}
		else {
			this.dia = (int) (diameter/1E5);
		}
		

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getDiameter() {
		return dia;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void update(Vector3d newPosition) {
		setX((int) (newPosition.getX()/(1E9)) );
		setY((int) (newPosition.getY()/(1E9)) );
	}

	public void draw(Graphics g, double size, int width, int height) {
		g.setColor(color);

		int x = (int) (width + (this.x - dia / 2 - width) * size);
		int y = (int) (height + (this.y - dia / 2 - height) * size);

		if(DEBUG) {
			System.out.println("x: " + x);
			System.out.println("y: " + y);
		}
		
		if(label.equals("SHIP")) {
			g.fillRect(x, y, (int) (dia * size), (int) (dia * size));
		}
		else {
			g.fillOval(x, y, (int) (dia * size), (int) (dia * size));
		}
		
		

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
