package titan.impl;

import java.util.ArrayList;

import titan.ProbeSimulatorInterface;
import titan.Vector3dInterface;
import titan.impl.State;
import titan.StateInterface;

public class ProbeSimulatorRungaKutta implements ProbeSimulatorInterface {

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
		/*
		Vector3dInterface[] positions = new Vector3d[ts.length + 1];

		Planet ship = AllPlanet.getListOfPlanet().get(0);

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
		*/
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
		
		// Planet ship = Planet.SHIP;

		// StateInterface state = new StateInterface(..)
		// tf = next time step we want to have
		// h = the step to get to the next time
		// y0 = current position/current state/current velocity
		// f = ODEFunction
		// Then we want to get the last output of the solver because that contains it at
		// the new time step h.
		
		
		AllPlanet allPlanets = new AllPlanet();
		allPlanets.createPlanets();
		ArrayList<Planet> listOfPlanets = allPlanets.getListOfPlanets();
		
		
		Vector3d[] beginPositions = new Vector3d[listOfPlanets.size()];
		Vector3d[] beginVelocities = new Vector3d[listOfPlanets.size()];
		
		Vector3d beginEarthPosition = new Vector3d();
		Vector3d beginEarthVelocity = new Vector3d();
		
		for (Planet planets : listOfPlanets) {
			
			if (planets.getName().equals("EARTH")) {
				
				beginEarthPosition.setX(planets.getPosition().getX());
				beginEarthPosition.setY(planets.getPosition().getY());
				beginEarthPosition.setZ(planets.getPosition().getZ());
				beginEarthVelocity.setX(planets.getVelocity().getX());
				beginEarthVelocity.setY(planets.getVelocity().getY());
				beginEarthVelocity.setZ(planets.getVelocity().getZ());
			}
		}
		listOfPlanets.get(0).setPosition(p0.add(beginEarthPosition));
		listOfPlanets.get(0).setVelocity(v0.add(beginEarthVelocity));
		
		int i = 0;
		
		for (Planet body : listOfPlanets) {
			
			beginPositions[i] = (Vector3d) body.getPosition();
			beginVelocities[i] = (Vector3d) body.getVelocity();
			i += 1;
		}
		State beginState = new State(beginPositions, beginVelocities, 0);
		ODESolverRungeKutta solver = new ODESolverRungeKutta();
		StateInterface[] solvedStates = solver.solve(new ODEFunction(), beginState, tf, h);
		
		Vector3dInterface[] returnPositions = new Vector3d[((int) Math.ceil(tf / h) + 1)];
		
		for (int a = 0; a < solvedStates.length; a++) {
			
			returnPositions[a] = ((State) solvedStates[a]).getPosition()[0];
		}
		
		return returnPositions;
	}
}