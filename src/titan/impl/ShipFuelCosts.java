package titan.impl;

public class ShipFuelCosts {

	private static final double MASS_THIRSTY_CRAFT = 7.8e4 + 6.0e3; // craft + lander
	private static final double MASS_FUEL_INITIAL = 0.08e4; // assumption
	private static final double MAX_THRUST = 3e7; // Newton
	private static final double EFFECTIVE_EXHAUST_VELOCITY_COMBUSTION = 4e3; // m/s
	
	
	//private static State Post_velocity;
	//private static State Current_velocity;
	private static double mass_flow_rate;
	private static double fuelCost;

	// inclination = velocity vector
	
	public ShipFuelCosts() {
		
		mass_flow_rate = MAX_THRUST / EFFECTIVE_EXHAUST_VELOCITY_COMBUSTION;
	}

	// Fmax /   total   mass  -  massflowrate  *  enginetime could be simplify as 1/(1-t) to
	// Derivative, equal -1/(1-t)^2
	public static Vector3d[] acceleration(double t, State state) {
		
		double norm = MAX_THRUST / (MASS_THIRSTY_CRAFT + (MASS_FUEL_INITIAL -t*mass_flow_rate));
		
		Vector3d[] acceleration = new Vector3d[state.getVelocity().length];
		
		for(int i=0; i<state.getVelocity().length; i++) {
			
			Vector3d velocity = state.getVelocity()[i];
			
			double teta = Math.acos(velocity.getZ()/velocity.norm());
			double phy = Math.atan(velocity.getY()/velocity.getX());
			
			double accelerationX = norm*Math.cos(phy)*Math.sin(teta);
			double accelerationY = norm*Math.sin(phy)*Math.sin(teta);
			double accelerationZ = norm*Math.cos(teta);
			
			acceleration[i] = new Vector3d(accelerationX, accelerationY, accelerationZ); 
		}
		
		return acceleration;
	}

/*
	public static double engineTime() {
		return ((MASS_THIRSTY_CRAFT + MASS_FUEL)-Math.sqrt((-1 * MAX_THRUST.getAcceleration)/ velocity())) / mass_flow_rate;
		//use v(t) to get t(v) function, so as we know the delta(v), we could get delta(t)
	}
	
	public static StateInterface velocity() {

		return Post_velocity.getVelocity-Current_velocity.getVelocity;
		// set Post_velocity-getCurrentVelocity
	}
*/
	public static double fuelCost(double t) {
		
		fuelCost = mass_flow_rate * t;
		
		return fuelCost;
	}
}