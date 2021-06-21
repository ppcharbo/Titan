package titan.impl;

/**
 * This class calculates the cost of fuel, taking into consideration the
 * velocity, acceleration and time.
 * 
 * @author Group 12
 */

public class FuelCostProbe {

	private static final double MASS_EMPTY_CRAFT = 7.8e4;
	private static final double MASS_totalfuel = 7.8e4; // assumption
	private static double currentMassFuel;

	private static double maxThrusty = 3E+07; // in N

	private static double effective_exhaust_velocity = 2e4; // in m/s

	// Velocity
	private Vector3d deltaVelocity;
	private Vector3d postVelocity;
	private Vector3d currentVelocity;
	private Vector3d acceleration;

	private static double massFlowRate = 1500;
	private static double engineTime; // fuel burning time
	private static double fuelcost;
	private static double acclerationValue; // the value of acceleration when engine turn on, using accleration.norm()

	public FuelCostProbe(Vector3d current_velocity, Vector3d postVelocity) {
		this.currentVelocity = current_velocity;
		this.postVelocity = postVelocity;
	}

	/**
	 * Method for calculating the change in velocity
	 * @return deltaVelocity: the change in velocity in a Vector3d
	 */
	public Vector3d changeVelocity() {
		// deltaVelocity between engine on.
		deltaVelocity = (Vector3d) postVelocity.sub(currentVelocity);
		return deltaVelocity;
	}

	/**
	 * First calculate acceleration of the probe using the formula M*a=Ve*mass flow rate
	 * M=Rocket mass Ve=exhuast velocity
	 * a=Ve*mass flow rate/M
	 * 
	 * Second: calculate engine time t=(V(t)-V(0))/a t=delta(V)/a
	 * 
	 * @return time
	 */
	public double calcTime() {
		acclerationValue = effective_exhaust_velocity * massFlowRate / MASS_EMPTY_CRAFT;
		engineTime = (deltaVelocity.norm()) / acclerationValue;

		return engineTime;
	}

	/**
	 * calculate the direction of the acceleration using Vector3d
	 * @return
	 */
	public Vector3d calAce() {
		acceleration = (Vector3d) deltaVelocity.mul(1 / engineTime);
		return acceleration;
	}

	public void fuelcost() {
		changeVelocity();
		calcTime();

		fuelcost = engineTime * massFlowRate;

		currentMassFuel = MASS_totalfuel - fuelcost;

		if (currentMassFuel < 0) {
			System.out.println("NO FUEL LEFT,PLEASE TRY AGAIN");
		}
	}

	public double getfuelcost() {
		return fuelcost;
	}

	public static void main(String[] args) {
		Vector3d v0 = new Vector3d(60000, 60000, 60000);
		Vector3d vt = new Vector3d(59999, 59999, 59999);

		FuelCostProbe f = new FuelCostProbe(v0, vt);
		f.fuelcost();
		double fuelcost = f.getfuelcost();
		Vector3d accleration = f.calAce();

		System.out.println("fuelcost: " + fuelcost + " kg");
		System.out.println("The accleration after thrust is :" + accleration);
	}
}
