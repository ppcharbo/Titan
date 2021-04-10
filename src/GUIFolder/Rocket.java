package GUIFolder;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Rocket {

	public double rocketMass = 15; // kilograms
	public double rocket_Diameter;
	public double xLaunch;
	public double yLaunch;
	private boolean visability;
	private double acceleration = 0;
	private double dirX = 0;
	private double dirY = 0;
	private double distance = 0;
	private double initial = 500000; // TEST CONSTANT
	private double vx = 0;
	private double vy = 0;
	private double max = 0;
	private JPanel parent;
	private Color color = new Color(255, 0, 0);

	public Rocket(JPanel parent, double initialSpeed, double rocket_Diameter, double xLaunch, double yLaunch) {

		this.xLaunch = xLaunch;
		this.yLaunch = yLaunch;
		this.rocket_Diameter = rocket_Diameter;
		// this.initial = initial;
		this.parent = parent;

		// TEST THIS CONSTANT *0.001
		this.vx = initialSpeed;
		this.vy = initialSpeed;

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
		xLaunch = xLaunch + vx; // vx
		yLaunch = yLaunch + vy; // vy
	}

	public void draw(Graphics g, double size) {

		g.setColor(color);
		int xLT = (int) (888 + (this.xLaunch - getDiameter() / 2 - 888) * size);
		int yLT = (int) (888 + (this.yLaunch - getDiameter() / 2 - 888) * size);
		int widthAndHeight = (int) (getDiameter() * size);
		
		System.out.println(" x = " + xLT + " y= " + yLT);
		g.fillRect(xLT, yLT, widthAndHeight, widthAndHeight);
	}

	public void rocketMotion(double xNew, double yNew) {

		distance = Math.sqrt((xNew - xLaunch) * (xNew - xLaunch) + (yNew - yLaunch) * (yNew - yLaunch));
		initial = Math.min(distance, initial);
		max = Math.max(distance, max);

		acceleration = rocketMass / distance / distance;
		acceleration = acceleration / 2;
		dirX = (xNew - xLaunch) / distance;
		dirY = (yNew - yLaunch) / distance;

		vx = vx + (dirX * acceleration);
		vy = vy + (dirY * acceleration);

		changeLocationWithSpeed();
	}

}
