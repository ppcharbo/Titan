package titan.impl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PlanetGUI {
	
	private final boolean DEBUG = false;
	private double diameter = 0;
	private double x = 0;
	private double y = 0;
	private String label;
	private JPanel parent;
	private Color color;

	/**
	 * This constructor is only for the SystemPlanet class (so GUI only)
	 * @param parent: parent JPanel for drawing
	 * @param label: 
	 * @param r: red colour
	 * @param g: green colour
	 * @param b: blue colour
	 * @param position: vector that contains the position of the object, scaled by 1E9
	 * @param diameter: diameter of the object in space
	 */
	public PlanetGUI(JPanel parent, String label, int r, int g, int b, Vector3d position, double d) {

		this.parent = parent;
		this.label = label;
		color = new Color(r, g, b);
		x = (position.getX()/(1E9));
		y = (position.getY()/(1E9));
		this.diameter = d;
	}

	/**
	 * This constructor is only for the ClosestFlyByCalculator class
	 * @param string: name of the planet
	 * @param r: red colour
	 * @param g: green colour
	 * @param b: blue colour
	 * @param position: vector that contains the position of the object, scaled by 1E9
	 * @param diameter: diameter of the object in space
	 */
	public PlanetGUI(String string, int r, int g, int b, Vector3d position, double d) {
		this.label = string;
		color = new Color(r, g, b);
		x = (position.getX()/(1E9));
		y = (position.getY()/(1E9));
		this.diameter = d;
	}

	// getters
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getDiameter() {
		return this.diameter;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public JPanel getParent() {
		return parent;
	}
	
	// mutators
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setColor(int r, int g, int b) {
		this.color = new Color(r, g, b);
	}
	
	public void setDiameter(double d) {
		this.diameter = d;
	}

	/**
	 * Method that updates the position of the object by a 2D vector
	 * Scaling constant: 1E9
	 * @param newPosition: is the updated vector
	 */
	public void update(Vector3d newPosition) {
		setX((int) (newPosition.getX()/(1E9)) );
		setY((int) (newPosition.getY()/(1E9)) );
	}

	/**
	 * Method that draws the planets
	 * @param g: Graphics
	 * @param size: scaling factor (for zooming)
	 * @param width: width of an object
	 * @param height: height of an object
	 */
	public void draw(Graphics g, double size, int width, int height) {
		g.setColor(color);

		int x = (int) (width + (this.x - diameter / 2 - width) * size);
		int y = (int) (height + (this.y - diameter / 2 - height) * size);

		if(DEBUG) {
			System.out.println("x: " + x);
			System.out.println("y: " + y);
		}
		
		if(label.equals("SHIP")) {
			g.fillRect(x, y, (int) (diameter * size), (int) (diameter * size));
		}
		else {
			g.fillOval(x, y, (int) (diameter * size), (int) (diameter * size));
		}

	}
	
	/**
	 * Method that translate the planets by d and e in x and y direction respectively
	 * @param d: distance to be moved in x direction
	 * @param e: distance to be moved in y direction
	 */
	public void translate(double d, double e) {
		this.x += d;
	    this.y += e;
	    if(DEBUG) {
	    	System.out.println("Translated: " + label +  " to x: " + x + " and y: " + y);
	    }
	 }
}