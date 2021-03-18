package titan.impl;

import java.util.ArrayList;

import titan.ProbeSimulatorInterface;
import titan.Vector3dInterface;

public class ProbeSimulator extends SystemPlanet implements ProbeSimulatorInterface {

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

		Vector3dInterface[] positions = new Vector3d[ts.length];

		Planet ship = Planet.SHIP;
		int i = 0;
		for (double d : ts) {

			Vector3dInterface accelerationForce = ship.accelerationForce();
			Vector3dInterface lastPosition = ship.getPosition();
			Vector3dInterface velocity = ship.getVelocity();
			Vector3dInterface newPosition = lastPosition.add(velocity.mul(d));
			Vector3dInterface newVelocity = velocity.add(accelerationForce.mul(d));
			ship.addPosition(newPosition);
			ship.addVelocity(newVelocity);

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
	 * each time stated, taken relative to the Solar System barycentre // le temps
	 * homogene entre tf et h
	 */
	@Override
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
		double[] tsMe = new double[(int) (tf / h) + 1];
		tsMe[0] = 0;
		for (int i = 1; i < tsMe.length; i++) {
			tsMe[i] = tsMe[i - 1] + h;
		}
		if (tsMe[tsMe.length - 1] != tf) {
			tsMe[tsMe.length - 1] = tf;
		}

		return trajectory(p0, v0, tsMe);

	}
}