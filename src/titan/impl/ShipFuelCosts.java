package titan.impl;

import titan.RateInterface;
import titan.Vector3dInterface;

public class ShipFuelCosts {

	private static final double MASS_THIRSTY_CRAFT = 7.8e4 + 6.0e3; // craft + lander
	private static final double MASS_FUEL = 0.08e4; // assumption
	private static final double MAX_THRUST = 3e7; // Newton
	private static final double EFFECTIVE_EXHAUST_VELOCITY_COMBUSTION = 4e3; // m/s
	
	private static State Post_velocity;
	private static State Current_velocity;
	private static double mass_flow_rate;
	private static double engineTime;
	private static double fuelCost;

	// inclination = velocity vector
	
	public ShipFuelCosts() {
		
	}

	public static double massFlowrate() {

		return MAX_THRUST / EFFECTIVE_EXHAUST_VELOCITY_COMBUSTION;
		//flowrate is also a constant 
	}

	// Fmax /   total   mass  -  massflowrate  *  enginetime could be simplify as 1/(1-t) to
	// Derivative, equal -1/(1-t)^2

	public static Vector3d acceleration(double t) {
		
		return null;//MAX_THRUST.getAcceleration() / (MASS_THIRSTY_CRAFT + MASS_FUEL);
	}

	public static double engineTime() {
		return ((MASS_THIRSTY_CRAFT + MASS_FUEL)-Math.sqrt((-1 * MAX_THRUST.getAcceleration)/ velocity())) / mass_flow_rate;
		//use v(t) to get t(v) function, so as we know the delta(v), we could get delta(t)
	}

	public static StateInterface velocity() {

		return Post_velocity.getVelocity-Current_velocity.getVelocity;
		// set Post_velocity-getCurrentVelocity
	}

	public static double fuelcost() {
		fuelCost = mass_flow_rate * engineTime;
		return fuelCost;
	}

	public static void main(String[] args){
		
	}
}