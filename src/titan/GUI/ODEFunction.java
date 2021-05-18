package titan.GUI;

import titan.ODEFunctionInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

/*
 * This is an interface for the function f that represents the
 * differential equation dy/dt = f(t,y).
 * You need to implement this function to represent to the laws of physics.
 *
 * For example, consider the differential equation
 *   dy[0]/dt = y[1];  dy[1]/dt=cos(t)-sin(y[0])
 * Then this function would be
 *   f(t,y) = (y[1],cos(t)-sin(y[0])).
 *
 * @param   t   the time at which to evaluate the function
 * @param   y   the state at which to evaluate the function
 * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
 */

public class ODEFunction implements ODEFunctionInterface {
	// universal gravitational constant (m3 kg-1 s-2)
	private static final double G = 6.67300E-11;
	private int numberOfPlanet;
	private AllPlanet planets;
	//= AllPlanet.getListOfPlanets().size();
	
	public ODEFunction() {
		planets = new AllPlanet();
		planets.createPlanets();
		numberOfPlanet = planets.getListOfPlanets().size();
	}

	@Override
	public Rate call(double t, StateInterface y) {
		
		Vector3d[] accelaration = accelerationForce((State) y);
		Rate newRate = new Rate(((State) y).getVelocity(), accelaration);
		
		return newRate;
	}
	
	/*
	 * This formula consist of the (G*m1*m2 )*(xj-xi/||xi-xj||^3)
	 */
	public Vector3d[] gravitationalForce(State y) {
		
		Vector3d[] allForces = new Vector3d[numberOfPlanet];
		// Calculate force of all the surrounding planets except that same planet
		for (int i = 0; i < numberOfPlanet; i++) {

			Vector3d force = new Vector3d(0, 0, 0);

			for (int j = 0; j < numberOfPlanet; j++) {
				// Do not consider the current planet
				if (i != j) {
					
					Vector3dInterface xi = y.getPosition()[i];
					Vector3dInterface xj = y.getPosition()[j];
					
					// Vector from our planet towards the other planet (attractive force)
					Vector3dInterface nTop = xj.sub(xi);
					
					// Vector from our planet towards the other planet (attractive force)
					Vector3dInterface nBottom = xi.sub(xj);
					
					double GMM = G * planets.getListOfPlanets().get(i).getMass() * planets.getListOfPlanets().get(j).getMass();
					double GMMdivNorm = GMM / Math.pow(nBottom.norm(), 3);
					
					// Add contribution planet p at each iteration
					force = (Vector3d) force.addMul(GMMdivNorm, nTop);
				}
			}
			allForces[i] = force;
		}
		return allForces;
	}

	/*
	 * We divide by the mass to get the acceleration
	 * This corresponds to the formula: Fr = m*a --> a = Fr/a
	 */
	public Vector3d[] accelerationForce(State y) {
		
		Vector3d[] forces = gravitationalForce(y);
		Vector3d[] acceleration = new Vector3d[numberOfPlanet];
		
		for (int i = 0; i < numberOfPlanet; i++) {
			
			acceleration[i] = (Vector3d) forces[i].mul(1 / (planets.getListOfPlanets().get(i).getMass()));
		}
		
		return acceleration;
	}
}