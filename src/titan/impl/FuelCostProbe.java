package titan.impl;

/*
hnn
*/

//public class FuelCostProbe implements ProbeSimulatorInterface{
public class FuelCostProbe {

	private static double mass_craft;
	private static double mass_lander;
	private static double mass_totalfuel; // have to calculate by using exit velocity and distance from earth to titan.
	private static double exit_velocity;
	private static double maxthrust;
	private static double mass_flow_rate;
	private static double acceleration;
	private static double engineTime;
	private static double fuelCost;

	public FuelCostProbe() {
		mass_craft = 7.8e4;
		mass_lander = 6e3;
		exit_velocity = 2e4;
		maxthrust = 3e7;
	}

	public static double massFlowrate() {

		return maxthrust / exit_velocity;

	}

	// Fmax /   total   mass  -  massflowrate  *  enginetime could be simplify as 1/(1-t) to
	// Derivative, equal -1/(1-t)^2

	public static double acceleration() {
		return maxthrust / (mass_craft + mass_lander + mass_totalfuel);
	}

	public static double engineTime() {
		return (Math.sqrt((-1 * maxthrust) / velocity()) - mass_craft + mass_lander + mass_totalfuel) / mass_flow_rate;
	}

	public static double velocity() {
		return -maxthrust / (mass_craft + mass_lander + mass_totalfuel - mass_flow_rate * engineTime);
	}

	public static double fuelcost() {
		fuelCost = mass_flow_rate * engineTime;
		return (Double) null;
	}

}
