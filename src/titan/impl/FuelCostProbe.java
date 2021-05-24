package titan.impl;

import titan.RateInterface;
import titan.Vector3dInterface;

//public class FuelCostProbe implements ProbeSimulatorInterface{
public class FuelCostProbe {

	private static final double MASS_EMPTY_CRAFT = 7.8e4 + 7.8e4;
	private static final double MASS_totalfuel=0.08e4; // assume.
	private static double maxthrusty;
	private static double exit_velocity;
	private static State Maxthrust;
	private static State Post_velocity;
	private static State Current_velocity;
	private static double mass_flow_rate;
	private static double engineTime;
	private static double fuelCost;

	public FuelCostProbe() {
	}

	public static double massFlowrate() {

		return maxthrusty / exit_velocity;
		//flowrate is also a constant 
	}

	// Fmax /   total   mass  -  massflowrate  *  enginetime could be simplify as 1/(1-t) to
	// Derivative, equal -1/(1-t)^2

	public static StateInterface acceleration() {
		return Maxthrust.getAcceleration / (MASS_EMPTY_CRAFT + MASS_totalfuel);
	}

	public static double engineTime() {
		return ((MASS_EMPTY_CRAFT + MASS_totalfuel)-Math.sqrt((-1 * Maxthrust.getAcceleration)/ velocity())) / mass_flow_rate;
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