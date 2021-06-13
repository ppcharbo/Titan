package titan.impl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import titan.StateInterface;

public class OpenLoopController {
	
	public InputFunctionLanding u;
	public InputFunctionLanding v;
	
	public State[] landingStates;
	
	private double diameter = 0;
	private int xLT = 400;
	private int yLT = 0;
	private String label;
	private JPanel parent;
	
	public double OpenLoopLandingSimulatorWRTU(StateInterface initialLaunchState) {
		
		//TODO Change shipWhenDistanceIsSmallest to ship
		int ship = 0;
		int titan = 10;
		Vector3d positionLaunch = ((State)(initialLaunchState)).getPosition()[ship];
		Vector3d velocityLaunch = ((State)(initialLaunchState)).getVelocity()[ship];
		Vector3d positionTitan = ((State)(initialLaunchState)).getPosition()[titan];
		System.out.println("Distance from ship to Titan:");
		System.out.println(positionLaunch.dist(positionTitan));
		
		
		Vector3d velocityTitan = new Vector3d(0, 0, 0);
		
		Vector3d[] positions = {positionLaunch, positionTitan};
		Vector3d[] velocities = {velocityLaunch, velocityTitan};
		boolean[] isShip = {true, false};
		
		State initialState = new State(positions, velocities, isShip, ((State)(initialLaunchState)).getTime());
		ODEFunction f = new ODEFunction();
		
		ODESolverRungeKutta solveLanding = new ODESolverRungeKutta();
		f.setLanding(true);
		int tf = 60*60*24;
		int h = 60*60;
		
		StateInterface[] landingStatesPerTime = solveLanding.solve(f, initialState, tf, h);
		//go over landingStatesPerTime and draw the module using ((State) landingStatesPerTime).getPosition()[0] ((.getX()/.getY()))
		landingStates = (State[]) landingStatesPerTime;
		System.out.println("WET ASS PU$$Y " + landingStates.length);
		
		double x = positionLaunch.getX();
		double y = positionLaunch.getY();
		double vx = velocityLaunch.getX();
		double vy = velocityLaunch.getY();
		
		double eta = Math.PI/4; //such that we have equal contribution between sin(eta) and cos(eta)
		double eta2 = 0; //stationary?
		InputFunctionLanding g = new InputFunctionLanding(x, y, eta, vx, vy, eta2);
		return g.getU();
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
	
	public void setX(int x) {
		this.xLT = x;
	}
	
	public void setY(int y) {
		this.yLT = y;
	}
	
	public void update(Vector3d newPosition) {
		setX((int) (newPosition.getX()/(1E9)) );
		setY((int) (newPosition.getY()/(1E9)) );
	}
	
	public State[] returnLandingStates() {
		
		return landingStates;
	}
	
	public void draw(Graphics g, double size, int width, int height) {

		Color color = new Color(0, 255, 0);
		
		size = 1;
		width = 110;
		height = 110;
		diameter = 10;
		
		g.setColor(color);
		
		System.out.println("This is diameter " + diameter);
		
		g.fillRect(xLT, yLT, (int) (diameter * size), (int) (diameter * size));
		
	}
	
}
