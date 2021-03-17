package GUI;

import java.awt.Color;
import java.awt.Graphics;

public class Rocket {
	
	public double rocket_Velocity;
	public double rocket_Mass;
	public double rocket_Diameter;
	public double xLaunch;
	public double yLaunch;
	private boolean visability;
	
	public Color color;
	
	public Rocket(int r, int g, int b, double rocket_Velocity, double rocket_Mass, double rocket_Diameter, double xLaunch, double yLaunch) {
		
		this.color = new Color(r, g, b);
		this.rocket_Velocity = rocket_Velocity;
		this.rocket_Mass = rocket_Mass;
		this.xLaunch = xLaunch;
		this.yLaunch = yLaunch;
		this.rocket_Diameter = rocket_Diameter;
		
	}
	

	public Color getColour() {
		return this.color;
	}

	public double getX() {
		return this.xLaunch;
	}

	public double getY() {
		return this.yLaunch;
	}

	public double getMass() {
		return this.rocket_Mass;
	}
	
	public boolean isVisible() {
		return this.visability;
	}
	
	public double getDiameter() {
		return this.rocket_Diameter;
	}
	
	public void visibalityChange(boolean set) {
		this.visability = set;
	}

	public void changeLocationWithSpeed() {
		this.xLaunch = this.xLaunch + rocket_Velocity;  // vx
		this.yLaunch = this.yLaunch + rocket_Velocity;  // vy
	}
	
	public void drawRocket(Graphics g, double size) {
		g.setColor(color);
		// this
		int xLT = (int) (888 + (this.xLaunch - getDiameter() / 2 - 888) * size);
		int yLT = (int) (888 + (this.yLaunch - getDiameter() / 2 - 888) * size);
		int widthAndHeight = (int) (getDiameter() * size);
		g.fillOval(xLT, yLT, widthAndHeight, widthAndHeight);
	}
	
	public void rocketMotion(double xNew, double yNew) {
		
		//TODO Finish this method that is the equivalent of the updatePlanet from Planet.java class
	}
	
}
