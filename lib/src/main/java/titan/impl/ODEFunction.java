package titan.impl;

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
	
	private static final double G = 6.67300E-11; // universal gravitational constant (m3 kg-1 s-2)
	private int numberOfPlanet;
	private AllPlanets planets; // = AllPlanet.getListOfPlanets().size();
	
	private boolean landingEnabledOpenController = false;
	private boolean landingEnabledFeedbackController = false;
	private final double G_TITAN = 1.352;
	
	public ODEFunction() {
		
		planets = new AllPlanets();
		planets.createPlanets();
		numberOfPlanet = planets.getListOfPlanets().size();
	}
	
	public void setLandingOpen(boolean landing) {
		this.landingEnabledOpenController = landing;
	}
	
	public void setLandingFeedback(boolean landing) {
		this.landingEnabledFeedbackController = landing;
	}

	
	@Override
	public Rate call(double t, StateInterface y) {

		if(landingEnabledOpenController == true) {
			
			OpenLoopControllerNew openController = new OpenLoopControllerNew();
			double u = openController.functionU(y);
			double v = openController.functionV(y);
			double eta = openController.calculateEta(y);
			
			//System.out.println("u: " + u);
			//System.out.println("v: " + v);
			//System.out.println("eta: " + eta);
			
			double xDoubleDot = u*Math.sin(eta);
			double yDoubleDot = u*Math.cos(eta)-G_TITAN;
			System.out.println("x**: " + xDoubleDot);
			System.out.println("y**: " + yDoubleDot);
			
			Vector3d newAccelLandingModule = new Vector3d(-xDoubleDot, -yDoubleDot, 0);
			Vector3d newAccelTitan = new Vector3d(0, 0, 0);
			Vector3d[] newAcceleration = {newAccelLandingModule, newAccelTitan};
			
			Vector3d[] currentVelocities = ((State) y).getVelocity();
			Vector3d newVelocityLandingModule = (Vector3d) currentVelocities[0].addMul(t, newAccelLandingModule);
			Vector3d newVelocityTitan = (Vector3d) currentVelocities[1].addMul(t, newAccelLandingModule);
			Vector3d[] newVelocity = {newVelocityLandingModule, newVelocityTitan};
			
			return new Rate(newVelocity, newAcceleration);	
		}
		else if(landingEnabledFeedbackController == true) {
			//TODO
			throw new RuntimeException("THIS HAS STILL TO BE IMPLEMENTED --> ERROR");
		}
		else {
			Vector3d[] accelerationShip = ShipFuelCosts.acceleration(t, (State)y); // acceleration provided by the thrusters 
			Vector3d[] accelerationPlanet = accelerationForce((State) y); // acceleration force of the planets
			
			Vector3d[] acceleration = new Vector3d[((State)y).getVelocity().length];
			
			for (int i=0; i<((State)y).getVelocity().length; i++) {
				if(1==0) { // engine-off
					
				//if (((State)y).getisShip()[i] && t>400) { // checking if the element of the list correspond to the ship as well as the current time 
				
					acceleration[i] = (Vector3d) accelerationShip[i].add(accelerationPlanet[i]); // acceleration of the thrusters combined with the attraction forces of the surroundings planets
				}
				else {
					acceleration[i] = accelerationPlanet[i]; // if not a ship, apply only the attraction forces 
				}
			}
			Rate newRate = new Rate(((State) y).getVelocity(), acceleration);
			
			return newRate;
		}
	}
	
	
	/*
	 * This formula consist of the (G*m1*m2 )*(xj-xi/||xi-xj||^3)
	 */
	public Vector3d[] gravitationalForce(State y) {
		
		Vector3d[] allForces = new Vector3d[numberOfPlanet];
		
		// calculate force of all the surrounding planets except that same planet
		for (int i = 0; i < numberOfPlanet; i++) {

			Vector3d force = new Vector3d(0, 0, 0);

			for (int j = 0; j < numberOfPlanet; j++) {
				
				// do not consider the current planet
				if (i != j) { 
					
					Vector3dInterface xi = y.getPosition()[i];
					Vector3dInterface xj = y.getPosition()[j];
					
					Vector3dInterface nTop = xj.sub(xi); // Vector from our planet towards the other planet (attractive force)
					Vector3dInterface nBottom = xi.sub(xj); // Vector from our planet towards the other planet (attractive force)
					
					double GMM = G * planets.getListOfPlanets().get(i).getMass() * planets.getListOfPlanets().get(j).getMass();
					double GMMdivNorm = GMM / Math.pow(nBottom.norm(), 3);
	
					force = (Vector3d) force.addMul(GMMdivNorm, nTop); // add contribution planet p at each iteration
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