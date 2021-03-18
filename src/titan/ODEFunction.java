package titan;

public class ODEFunction implements ODEFunctionInterface {

	// masses
	public double earthMass = 5.97219e24;
	public double solarMass = 1.988500e30;
	public double mercuryMass = 3.302e23;
	public double venusMass = 4.8685e24;
	public double moonMass = 7.349e22;
	public double marsMass = 6.4171e23;
	public double jupiterMass = 1.89813e27;
	public double saturnMass = 5.6834e26;
	public double titanMass = 1.34553e23;
	public double uranusMass = 8.6813e25;
	public double neptuneMass = 1.02413e26;

	public double G = 6.66667 * Math.pow(10, -11);// Gravity universally

	/*
	 * This is an interface for the function f that represents the differential
	 * equation dy/dt = f(t,y). You need to implement this function to represent to
	 * the laws of physics.
	 *
	 * For example, consider the differential equation dy[0]/dt = y[1];
	 * dy[1]/dt=cos(t)-sin(y[0]) Then this function would be f(t,y) =
	 * (y[1],cos(t)-sin(y[0])).
	 *
	 * @param t the time at which to evaluate the function
	 * 
	 * @param y the state at which to evaluate the function
	 * 
	 * @return The average rate-of-change over the time-step. Has dimensions of
	 * [state]/[time].
	 */
	@Override
	public RateInterface call(double t, StateInterface y) {
		// ^
		// ý(t) = f(t, y(t))
		// v
		// return

		// we are function f in this case
		// question: what's our function f(t, y(t))?

		return null;
	}

	public double gravF() {
		Vector3dInterface force = new Vector3d();
		int i = 0;
		double time = 0;
		double forceMagnietude = 0;
//		//for each planet (not same) --> Fg
//		while (time < tf) { //tEnd
//			i++;
//			time = time + h; //step
//
//			forceMagnietude = (G * m1 * m2) / Math.pow((positionMoving.sub(centerposition).norm()), 2);
//			force = (centerposition.sub(positionMoving));
//			force = force.mul(forceMagnietude / force.norm());
//		}
//
//	}
		return (Double) null;
	
	
}}
