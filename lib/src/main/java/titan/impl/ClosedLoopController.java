package titan.impl;

import java.awt.Color;
import java.awt.Graphics;

import titan.StateInterface;
/**
  *  This class is responsible for simulating the probe's landing using a closed loop controller.
  *  @author Group 12 
  */
public class ClosedLoopController {
	private StateInterface[] landingStatesPerTime;
	public final double E = 2.718281828459;
	private int xLT = 400;
	private int yLT = 0;
	private boolean landingSpotX = false;

	public ClosedLoopController() {
		//nothing
	}
	
	public ClosedLoopController(StateInterface initialLaunchState) {
		
		int ship = 0;
		int titan = 10;
		Vector3d positionLaunch = ((State)(initialLaunchState)).getPosition()[ship];
		Vector3d velocityLaunch = ((State)(initialLaunchState)).getVelocity()[ship];
		Vector3d positionTitan = ((State)(initialLaunchState)).getPosition()[titan];
		positionLaunch.setZ(0);
		velocityLaunch.setZ(0);
		positionTitan.setZ(0);
		//System.out.println("Distance from ship to Titan:");
		//System.out.println(positionLaunch.dist(positionTitan));
		
		Vector3d velocityTitan = new Vector3d(0, 0, 0);
		
		Vector3d[] positions = {positionLaunch, positionTitan};
		Vector3d[] velocities = {velocityLaunch, velocityTitan};
		boolean[] isShip = {true, false};
		State initialState = new State(positions, velocities, isShip, 0);
		ODEFunction f = new ODEFunction();
		f.setLandingFeedback(true);
		
		//ODESolverEuler solveLanding = new ODESolverEuler();
		ODESolverRungeKutta solveLanding = new ODESolverRungeKutta();
		
		int tf = 60*60*24*365; //1 day
		int h = 60*60; //1 minute
		
		landingStatesPerTime = (StateInterface[]) solveLanding.solve(f, initialState, tf, h);
	}
	
	
	public double calcU() {
		if(landingSpotX == true) {
			return 1.352;
		}
		
		return 5;
	}
	
	
	public double computePositiveLambda(double a, double b) {
		
		double posLambda = (b/2) + Math.sqrt( ((a*a - 4*b)/2) );
		return posLambda;
	}
	
	
	public double computeNegativeLambda(double a, double b) {
		
		double negLambda = (b/2) - Math.sqrt( ((a*a - 4*b)/2) );
		return negLambda;
	}
	
	
	public double calcTheta(double a, double b, double t, StateInterface y) {        // might not need
		
		if(((State) y).getPosition()[0].getX()/1E9 <= 1290 && ((State) y).getPosition()[0].getX()/1E9 >= 1310) { //1300
			landingSpotX  = true;
			return 0;
		}
		if(landingSpotX == true) {
			return 0;
		}
		
		double posLambda = computePositiveLambda(a,b);
		double theta = Math.pow(E, posLambda*t);
		
		return theta;
	}
	
	public double calcThetaDot(double a, double b, double t) {     // might not need
		if(landingSpotX == true) {
			return 0;
		}
		double lambda = computePositiveLambda(a,b);
		double thetaDot = lambda * Math.pow(E, lambda*t);
		return thetaDot;
		
	}
	
	public double calcThetaDoubleDot(double a, double b, double t) {     // the equivalent of V
		if(landingSpotX == true) {
			return 0;
		}
		double lambda = computePositiveLambda(a,b);
		double thetaDoubleDot = lambda * lambda * Math.pow(E, lambda*t);
		return thetaDoubleDot;
	}

	public StateInterface[] getLandingStates() {
		return this.landingStatesPerTime;
	}
	
	public void update(Vector3d newPosition) {
		setX((int) (newPosition.getX()/(1E9)) );
		setY((int) (newPosition.getY()/(1E9)) );
	}

	private void setX(int i) {
		this.xLT = i;
	}
	
	private void setY(int i) {
		this.yLT = i;
	}
	
	public void draw(Graphics g, double size, int width, int height) {	
		Color color = new Color(0, 255, 0);
		System.out.println(xLT);
		g.setColor(color);
		g.fillRect(xLT, yLT, width, height);
		
		if (yLT >= 742) {
			LandingModule.stop = true;
		}
	}
}
