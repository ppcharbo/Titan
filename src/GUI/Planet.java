package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Planet {
	//fields
	
	private double x;
	private double y;
	private double dia;
	private double a = 0; //this
	private double distance = 0; //this
	private double initial = 1000; //this
	private double max = 0; //this
	private double dirX = 0; //this
	private double dirY = 0; //this
	private double vx;
	private double vy;
	private double mass;
	private boolean visability;
	public int[][] orbitDots = new int[1000][2]; //this
	public int counter = 0; //this
	public Color colour;
	
	
	//code here for every planet + sun as an object
	public Planet(int r, int g, int b, double xCoordinate, double yCoordinate, double diameter, double vx, double vy, double mass) {
		//Colour properties using r,g,b
		this.colour = new Color(r,g,b);
		
		//physical properties of a planet
		this.x = xCoordinate;
		this.y = yCoordinate;
		this.dia = diameter;
		
		//properties of speed of a planet
		this.vx = vx;
		this.vy = vy;
		
		//physical property of a planet that is different compared to x,y and d in units
		this.mass = mass;
	}
	
	//accessor methods
	public Color getColour() {
		return this.colour;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getMass() {
		return this.mass;
	}
	
	public double getDiameter() {
		return this.dia;
	}
	
	public boolean isVisible() {
		return this.visability;
	}
	
	public boolean collision(int x, int y, double scale) {
		if(x>600 + (getX() - getDiameter() - 600)*scale && x < 600 + (getX() + getDiameter()-600) *scale && y>400+(getY()-getDiameter()-400)*scale && y<400+(getY() + getDiameter() -400)*scale) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//mutator methods
	public void visibalityChange(boolean set) {
		this.visability = set;
	}
	
	public void changeLocationWithSpeed() {
		this.x = this.x + vx;
		this.y = this.y + vy;
	}
	
	public void drawPlanet(Graphics g, double size) {
		g.setColor(colour);
		//this
		int xLT = (int) (888+(this.x-getDiameter()/2-888)*size);
		int yLT = (int) (888+(this.y-getDiameter()/2-888)*size);
		int widthAndHeight = (int) (getDiameter()*size);
		g.fillOval(xLT, yLT, widthAndHeight, widthAndHeight);
	}
	
	public void updatePlanet(double xNew, double yNew, double massNew) { //this
		if(this.visability == true) {
			orbitDots[counter][0] = (int) (this.x + 1);
			orbitDots[counter][1] = (int) (this.y + 1);
			counter = (counter+1)%1000;
		}else {
			orbitDots = new int[1000][2];
			counter = 0;
		}
		double distanceSquared = (xNew-this.x)*(xNew-this.x) + (yNew-this.y)*(yNew-this.y);
		distance = Math.sqrt(distanceSquared);
		if(distance < initial) {
			initial = distance;
		}
		else {
			//do nothing because initial = initial
		}
		if(distance > max) {
			max = distance;
		}
		else {
			//do nothing because max = max
		}
		
		a = massNew/this.distance/this.distance;
		dirX = (xNew-this.x)/this.distance;
		dirY = (yNew-this.x)/this.distance;
		
		vx = vx + dirX * this.a;
		vy = vy + dirY * this.a;
		
		changeLocationWithSpeed();
	}
	
	public void dispDesc(Graphics g, double scale) { //this
		g.setColor(colour);
		for(int[] orbit : orbitDots) {
			g.drawLine(orbit[0], orbit[1], orbit[0], orbit[1]);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.setColor(Color.BLUE);
			
			String arg0 = (Math.round(this.distance*100.0)/100.0) * 1000000 + " km";
			int arg1 = (int) (getDiameter()+600+(this.x-getDiameter()/2-600)*scale);
			int arg2 = (int) ((16+400+(this.y-getDiameter()/2-400)*scale)+getDiameter());
			g.drawString(arg0, arg1, arg2);
		}
	}
}
