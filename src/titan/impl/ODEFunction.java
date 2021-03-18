package titan.impl;

import titan.ODEFunctionInterface;
import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

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
	 * a=dy/dt = f(t,y) a =acceleration dy[0]/dt = y[1];
	 * 
	 * dy[1]/dt=cos(t)-sin(y[0])
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
	 * 
	 * 
	 * 
	 * y(t) is an n-dimensional vector describing the state of the system at time t0
	 * t1 (1h) earth [
	 * (1.471922101663588e+11,-2.860995816266412e+10,8.278183193596080e+06
	 * ),(1.471922101663588e+11 + δt f (t, y) ,-2.860995816266412e+10 + δt f (t, y)
	 * ,8.278183193596080e+06 + δt f (t, y) ) ]
	 *
	 * earth= 1.471922101663588e+11 + δt f (t, y) f (t, y) ???? F Gi ----> f (t, y)
	 */
	@Override
	public RateInterface call(double t, StateInterface y) {

		Planet p = (Planet) y;
		Vector3dInterface accelerationForce = p.accelerationForce();
		Vector3dInterface lastPosition = p.getPosition();
		Vector3dInterface velocity = p.getVelocity();
		Vector3dInterface newPosition = lastPosition.add(velocity.mul(t));
		Vector3dInterface newVelocity = velocity.add(accelerationForce.mul(t));
		p.addPosition(newPosition);
		p.addVelocity(newVelocity);

		return p;
	}

	public Planet[] callForAllPlanets(double t, Planet[] planets) {

		for (Planet p : planets) {
			Vector3dInterface accelerationForce = p.accelerationForce();
			Vector3dInterface lastPosition = p.getPosition();
			Vector3dInterface velocity = p.getVelocity();
			Vector3dInterface newPosition = lastPosition.add(velocity.mul(t));
			Vector3dInterface newVelocity = velocity.add(accelerationForce.mul(t));
			p.addPosition(newPosition);
			p.addVelocity(newVelocity);
		}

		return Planet.values();
	}

}
