package titan.impl;

import titan.ODEFunctionInterface;
import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public class ODEFunction implements ODEFunctionInterface {

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
	 * ),(1.471922101663588e+11 + δt f (t, y) ,-2.860995816266412e+10 + δt f (t,
	 * y) ,8.278183193596080e+06 + δt f (t, y) ) ]
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

	public void callAll(double t) {

		for (Planet p : Planet.values()) {
			call(t, p);
		}

	}

}