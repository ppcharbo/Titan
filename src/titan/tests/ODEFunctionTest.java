package titan.tests;

import org.junit.Test;

import titan.RateInterface;
import titan.impl.ODEFunction;
import titan.impl.Planet;
import titan.impl.Vector3d;

public class ODEFunctionTest {

	/*
	 * Testing accuracy of the new earth position after a year (iterating every seconds) compared to the initial earth position
	 * @return initial earth position, positions Difference, and new earth position
	 */
	@Test
	public void testCall() {
		
		ODEFunction odeFunction = new ODEFunction();
		
		Planet planet = Planet.EARTH;
		Vector3d initialPosition = (Vector3d) planet.getPosition();
		double deltaT = 1;
		System.out.println("Initial Earth Position  "+planet.getPosition());
		System.out.println("");
		
		//checking new planet position after every seconds (over a year)
		for (int time = 0; time < 365.25*24*3600; time++) {
			RateInterface call = odeFunction.call(time, planet);
			planet = (Planet) planet.addMul(deltaT, call);
		}
		System.out.println("Positions Difference "+planet.getPosition().sub(initialPosition));
		System.out.println("");
		System.out.println("New Earth Position "+planet.getPosition());
	}
}
