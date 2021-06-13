package titan.impl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import titan.StateInterface;

public class OpenLoopController {
	
	public InputFunctionLanding u;
	public InputFunctionLanding v;
	
	//public StateInterface[] landingStates;
	private StateInterface[] landingStatesPerTime;
	
	private double diameter = 10;
	private int xLT = 400;
	private int yLT = 0;
	
	public double openLoopLandingSimulatorWRTU(StateInterface initialLaunchState) {
		
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
		int tf = 60*60*24*50;
		int h = 60*60;
		
		landingStatesPerTime = (StateInterface[]) solveLanding.solve(f, initialState, tf, h);
		
		double x = positionLaunch.getX();
		double y = positionLaunch.getY();
		double vx = velocityLaunch.getX();
		double vy = velocityLaunch.getY();
		
		double eta = Math.PI/4; //such that we have equal contribution between sin(eta) and cos(eta)
		double eta2 = 0; //stationary?
		InputFunctionLanding g = new InputFunctionLanding(x, y, eta, vx, vy, eta2);
		return g.getU();
	}
	
	public InputFunctionLanding openLoopLandingSimulatorWRTV(State initialLaunchState) {
		int ship = 0;
		Vector3d positionLaunch = initialLaunchState.getPosition()[ship];
		Vector3d velocityLaunch = initialLaunchState.getVelocity()[ship];
		double x = positionLaunch.getX();
		double y = positionLaunch.getY();
		double vx = velocityLaunch.getX();
		double vy = velocityLaunch.getY();
		
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
	
	public StateInterface[] returnLandingStates() {
		return landingStatesPerTime;
	}
	
	public void draw(Graphics g, double size, int width, int height) {
		Color color = new Color(0, 255, 0);
		g.setColor(color);
		g.fillRect(xLT, yLT, width, height);
		
	}
}
