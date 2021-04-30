package titan.impl;

import titan.ProbeSimulatorInterface;
import titan.Vector3dInterface;

public class ProbeSimulator implements ProbeSimulatorInterface {

	public final double H = 60 * 60;

	/*
	 * Simulate the solar system, including a probe fired from Earth at 00:00h on 1
	 * April 2020.
	 *
	 * @param p0 the starting position of the probe, relative to the earth's
	 * position.
	 * 
	 * @param v0 the starting velocity of the probe, relative to the earth's
	 * velocity.
	 * 
	 * @param ts the times at which the states should be output, with ts[0] being
	 * the initial time.
	 * 
	 * @return an array of size ts.length giving the position of the probe at each
	 * time stated, taken relative to the Solar System barycentre.
	 */
	@Override
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {

		Vector3dInterface[] positions = new Vector3d[ts.length + 1];

		Planet ship = Planet.SHIP;

		int i = 0;

		for (double d : ts) {

			Vector3dInterface accelerationForce = ship.accelerationForce();
			Vector3dInterface lastPosition = ship.getPosition();
			Vector3dInterface speed = ship.getSpeed();
			Vector3dInterface newPosition = lastPosition.add(speed.mul(d));
			Vector3dInterface newSpeed = speed.add(accelerationForce.mul(d));
			ship.addPosition(newPosition);
			ship.addSpeed(newSpeed);

			positions[i++] = newPosition;
		}
		return positions;
	}

	/*
	 * Simulate the solar system with steps of an equal size. The final step may
	 * have a smaller size, if the step-size does not exactly divide the solution
	 * time range.
	 *
	 * @param tf the final time of the evolution.
	 * 
	 * @param h the size of step to be taken
	 * 
	 * @return an array of size round(tf/h)+1 giving the position of the probe at
	 * each time stated, taken relative to the Solar System barycentre
	 */

	@Override
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {

		//Planet ship = Planet.SHIP;

		// StateInterface state = new StateInterface(..)
		// tf = next time step we want to have
		// h = the step to get to the next time
		// y0 = current position/current state/current velocity
		// f = ODEFunction
		// Then we want to get the last output of the solver because that contains it at
		// the new time step h.

		Vector3dInterface[] positions = new Vector3d[((int) Math.round(tf / h) + 1) + 1];
		int i = 0;
		positions[i] = p0;
		State passMe = new State(p0.getX(), p0.getY(), p0.getZ(), v0.getX(), v0.getY(), v0.getZ());
		i = 1; // lock the position[0] position to be the intial position

		ODESolver solvy = new ODESolver();
		State[] arr = (State[]) solvy.solve(new ODEFunction(v0), passMe, tf, h);
		for (State px : arr) {
			positions[i] = (Vector3d) px.position;
			i++; // update the i'th position
		}

		return positions;

	}
}