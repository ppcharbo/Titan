package titan.impl;

import titan.StateInterface;

public class ClosedLoopController {
	private StateInterface[] landingStatesPerTime;
	public final double E = 2.718281828459;

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
		State initialState = new State(positions, velocities, isShip, ((State)(initialLaunchState)).getTime());
		ODEFunction f = new ODEFunction();
		f.setLandingOpen(true);
		
		//ODESolverEuler solveLanding = new ODESolverEuler();
		ODESolverRungeKutta solveLanding = new ODESolverRungeKutta();
		
		int tf = 60*60*24*365; //1 day
		int h = 60*60; //1 minute
		
		landingStatesPerTime = (StateInterface[]) solveLanding.solve(f, initialState, tf, h);
	}
	
	public double functionU(double x, double y, double eta, double xDot, double yDot, double etaDot) {
		double uMax = 100;
		while(x != 0) {
			//
		}
		
		return 5;
	}
	
	public double computePositiveLambda(double a, double b) {
		
		double posLambda;
		posLambda = (b/2) + ((a*a - 4*b)/2);
		return posLambda;
		
	}
	
	/*
	public double computeNegativeLambda(double a, double b) {
		
		double negLambda;
		negLambda = (b/2) - ((a*a - 4*b)/2);
		return negLambda;
		
	}
	*/
	
	public double calcTheta(double a, double b, double t) {        // might not need
		
		double theta;
		double lambda = computePositiveLambda(a,b);
		theta = Math.pow(E, lambda*t);
		return theta;
		
	}
	
	public double calcThetaDot(double a, double b, double t) {     // might not need
		
		double thetaDot;
		double lambda = computePositiveLambda(a,b);
		thetaDot = lambda * Math.pow(E, lambda*t);
		return thetaDot;
		
	}
	
	public double calcThetaDoubleDot(double a, double b, double t) {     // the equivalent of V
		
		double thetaDoubleDot;
		double lambda = computePositiveLambda(a,b);
		thetaDoubleDot = lambda * lambda * Math.pow(E, lambda*t);
		return thetaDoubleDot;
		
	}
	
	
	
	
}