package GUIFolder;

import java.awt.Color;
import java.awt.Graphics;

public class Rocket {
	
	public double rocket_Velocity;
	public final double ROCKET_MASS = 15000; //kilograms
	public double rocket_Diameter;
	public double xLaunch;
	public double yLaunch;
	private boolean visability;
	private double acceleration = 0;
	private double dirX = 0;
	private double dirY = 0;
	private double distance = 0;
	private double initial = 1000;
	private double max = 0;
	
	public Color color;
	
	public Rocket(int r, int g, int b, double rocket_Velocity, double rocket_Diameter, double xLaunch, double yLaunch) {
		
		this.color = new Color(r, g, b);
		this.rocket_Velocity = rocket_Velocity;
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
		
		distance = Math.sqrt((xNew - xLaunch) * (xNew - xLaunch) + (yNew - yLaunch) * (yNew - yLaunch));
		initial = Math.min(distance, initial);
		max = Math.max(distance, max);

		acceleration = ROCKET_MASS / distance / distance;

		dirX = (xNew - xLaunch) / distance;
		dirY = (yNew - yLaunch) / distance;

		rocket_Velocity += dirX * acceleration + dirY * acceleration;  // not sure if this is physically correct
		
		changeLocationWithSpeed();
	}
	
}
