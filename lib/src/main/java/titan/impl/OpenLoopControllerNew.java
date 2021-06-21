package titan.impl;

import java.awt.Color;
import java.awt.Graphics;

import titan.StateInterface;
/**
 *  This class is responsible for simulating the probe's landing using an open loop controller.
 *  @author Group 12 
 */
public class OpenLoopControllerNew {
	
	private double etaDotZero = 0;
	private double etaZero = Math.PI/4;
	private StateInterface[] landingStatesPerTime;
	private int xLT = 400;
	private int yLT = 0;
	private boolean landingSpotX = false;

	public OpenLoopControllerNew() {
		//nothing
	}
	
	public OpenLoopControllerNew(StateInterface initialLaunchState) {
		
		int ship = 0;
		int titan = 10;
		Vector3d positionLaunch = ((State)(initialLaunchState)).getPosition()[ship];
		Vector3d velocityLaunch = ((State)(initialLaunchState)).getVelocity()[ship];
		Vector3d positionTitan = ((State)(initialLaunchState)).getPosition()[titan];
		//System.out.println("Distance from ship to Titan:");
		//System.out.println(positionLaunch.dist(positionTitan));
		
		Vector3d velocityTitan = new Vector3d(0, 0, 0);
		
		Vector3d[] positions = {positionLaunch, positionTitan};
		Vector3d[] velocities = {velocityLaunch, velocityTitan};
		boolean[] isShip = {true, false};
		State initialState = new State(positions, velocities, isShip, ((State)(initialLaunchState)).getTime());
		ODEFunction f = new ODEFunction();
		
		//ODESolverEuler solveLanding = new ODESolverEuler();
		ODESolverRungeKutta solveLanding = new ODESolverRungeKutta();
		f.setLandingOpen(true);
		int tf = 60*60*24*365;
		int h = 60*60;
		
		landingStatesPerTime = (StateInterface[]) solveLanding.solve(f, initialState, tf, h);
	}
	
	public double calcU(StateInterface y) {
		//Function u is pre-determined by the time t.
		
		/*
		if(((State) y).getTime() < 60*60) {
			return 20;
		}
		else if(((State) y).getTime() < 7*24*60*60) {
			return 10;
		}
		*/
		
		
		//System.out.println("Simulation is taking longer than a day");
		return 5;
	}
	
	public double calcThetaDoubleDot(StateInterface y) { //this is v (torque in Nm)
		//Function v is pre-determined by the time t.
		/*
		if(((State) y).getTime() < 60*60) {
			return 20;
		}
		else if(((State) y).getTime() < 24*60*60) {
			return 10;
		}
		*/
		
		if(landingSpotX == true) {
			return 0;
		}
		
		//System.out.println("Simulation is taking longer than a day");
		return 5;
	}
	
	public double calcThetaDot(StateInterface y) {
		//Function v is pre-determined by the time t.
		/*
		if(((State) y).getTime() < 60*60) {
			return 20;
		}
		else if(((State) y).getTime() < 24*60*60) {
			return 10;
		}
		*/
		
		if(landingSpotX == true) {
			return 0;
		}
		
		//System.out.println("Simulation is taking longer than a day");
		return 5;
	}
	
	public double calcTheta(StateInterface y) {
		
		/*
		if(((State) y).getPosition()[0].getX() == ((State) y).getPosition()[1].getX()) {
			System.out.println("We are at the same x, so x = 0");
			landingSpotX = true;
			return 0;
		}
		*/
		
		if(((State) y).getPosition()[0].getX()/1E9 <= 1290 && ((State) y).getPosition()[0].getX()/1E9 >= 1310) { //1300
			landingSpotX = true;
			return 0;
		}
		if(landingSpotX == true) {
			return 0;
		}
		
		double returner = (0.5*calcThetaDoubleDot(y)*Math.pow(((State) y).getTime(), 2) + etaDotZero *((State) y).getTime()+etaZero) % (Math.PI*2);
		return returner;
	}
	
	public StateInterface[] getLandingStates() {
		return this.landingStatesPerTime;
	}
	
	public void update(Vector3d newPosition) {
		setX((int) (newPosition.getX()/(1E9)) );
		setY((int) (newPosition.getY()/(1E9)) );
	}
	
	public void draw(Graphics g, double size, int width, int height) {
		//System.out.println("xLT: " + xLT);
		//System.out.println("yLT: " + yLT);
		
		Color color = new Color(0, 255, 0);
		g.setColor(color);
		g.fillRect(xLT, yLT, width, height);
		
		if(yLT >= 734) {
			LandingModule.stop = true;
		}
		
	}
	
	public void setX(int x) {
		this.xLT = x;
	}
	
	public void setY(int y) {
		this.yLT = y;
	}
}
