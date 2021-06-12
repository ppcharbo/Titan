package titan.impl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class OpenLoopController {
	
	public InputFunctionLanding u;
	public InputFunctionLanding v;
	
	private double diameter = 0;
	private double xLT = 0;
	private double yLT = 0;
	private String label;
	private JPanel parent;
	
	public InputFunctionLanding OpenLoopLandingSimulatorWRTU(State initialLaunchState) {
		
		//TODO Change shipWhenDistanceIsSmallest to ship
		int shipWhenDistanceIsSmallest = 0;
		Vector3d positionLaunch = initialLaunchState.getPosition()[shipWhenDistanceIsSmallest];
		Vector3d velocityLaunch = initialLaunchState.getVelocity()[shipWhenDistanceIsSmallest];
		double x = positionLaunch.getX();
		double y = positionLaunch.getY();
		double vx = velocityLaunch.getX();
		double vy = velocityLaunch.getY();
		
		//TODO define eta's
		double eta = 0;
		double eta2 = 0;
		InputFunctionLanding f = new InputFunctionLanding(x, y, eta, vx, vy, eta2);
		return u;
	}
	
	public InputFunctionLanding OpenLoopLandingSimulatorWRTV(State initialLaunchState) {
		//TODO Change shipWhenDistanceIsSmallest to ship
		int shipWhenDistanceIsSmallest = 0;
		Vector3d positionLaunch = initialLaunchState.getPosition()[shipWhenDistanceIsSmallest];
		Vector3d velocityLaunch = initialLaunchState.getVelocity()[shipWhenDistanceIsSmallest];
		double x = positionLaunch.getX();
		double y = positionLaunch.getY();
		double vx = velocityLaunch.getX();
		double vy = velocityLaunch.getY();
		
		//TODO define eta's
		double eta = 0;
		double eta2 = 0;
		InputFunctionLanding f = new InputFunctionLanding(x, y, eta, vx, vy, eta2);
		
		return v;
	}
	
	public void draw(Graphics g, double size, int width, int height) {

		Color color = new Color(0, 255, 0);
		
		size = 1;
		width = 110;
		height = 110;
		diameter = 10;
		
		g.setColor(color);
		

		//int x = (int) (width + (this.xLT - diameter / 2 - width) * size);
		// y = (int) (height + (this.yLT - diameter / 2 - height) * size);
		
		System.out.println("This is diameter " + diameter);
		//System.out.println("This is x " + x);
		//System.out.println("This is y " + y);
		
		g.fillRect(400, 0, (int) (diameter * size), (int) (diameter * size));
		
	}
	
}
