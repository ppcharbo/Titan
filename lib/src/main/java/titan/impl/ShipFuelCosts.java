package titan.impl;

public class ShipFuelCosts {

	private static final double MASS_THIRSTY_CRAFT = 7.8e4 + 6.0e3; // craft + lander
	private static final double MASS_FUEL_INITIAL = 0.08e4; // assumption
	private static final double MAX_THRUST_COMBUSTION = 3e7; // Newton
	
	private static final double EFFECTIVE_EXHAUST_VELOCITY_COMBUSTION = 4e3; // m/s
	private static final double MAX_THRUST_ION = 2e4; 
	private static final double EFFECTIVE_EXHAUST_VELOCITY_ION = 1e-1; 
	private static final double MAX_THRUST_MAGNETOPLASMADYNAMIC = 3e7; 
	private static final double EFFECTIVE_EXHAUST_VELOCITY_MAGNETOPLASMADYNAMIC = 2.5e-25; 
	private static Vector3d delta_velocity;
	private static Vector3d Post_velocity;
	private static Vector3d Current_velocity;
	private static double engineTime;
	private static Vector3d[] acceleration;
	
	private static double mass_flow_rate;
	private static double fuel_cost;
	
	public ShipFuelCosts() {
		
		mass_flow_rate = MAX_THRUST_COMBUSTION / EFFECTIVE_EXHAUST_VELOCITY_COMBUSTION; // constant
	}


	public static Vector3d[] acceleration(double t, State state) {
		
		Vector3d normTitan = (Vector3d) state.getPosition()[10];
		Vector3d ship = (Vector3d) state.getPosition()[0];
		
		Vector3d distanceVector = (Vector3d) normTitan.sub(ship); // distance between norm of titan and norm of ship
		double normDistanceVector = distanceVector.norm();
				
		double norm = MAX_THRUST_COMBUSTION / (MASS_THIRSTY_CRAFT + (MASS_FUEL_INITIAL -t*mass_flow_rate)); // fuel mass decreasing over time  
		
		double constant = 1/(normDistanceVector/norm);
		
		acceleration = new Vector3d[state.getVelocity().length]; 
		
		acceleration[0] = (Vector3d) distanceVector.mul(constant); // acceleration of ship
				
		return acceleration;
	}

	
	public Vector3d changeVelocity() {

		//delta_velocity between engine on.
		delta_velocity=(Vector3d) Post_velocity.sub(Current_velocity);
		return delta_velocity;
	}

	
	public static double calcTime() {
		
		System.out.println("Hey There, the error might have been fixed " + acceleration[0].norm());
		engineTime=(delta_velocity.norm())/acceleration[0].norm();
		
		return engineTime;
	}

	
	public static double fuelCost(double t) {
		
		fuel_cost = mass_flow_rate * t;
		
		return fuel_cost;
	}
}