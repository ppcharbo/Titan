package titan.tests;

import org.junit.Test;

import titan.RateInterface;
import titan.impl.ODEFunction;
import titan.impl.Planet;

public class ODEFunctionTest {

	@Test
	public void testCall() {
		
		ODEFunction odeFunction = new ODEFunction();
		
		Planet planet = Planet.EARTH;
		double deltaT = 1;
		System.out.println("Earth Position  "+planet.getPosition());
	
		//checking new planet position after every seconds (over a year)
		for (int time = 0; time < 365.25*24*3600; time++) {
			RateInterface call = odeFunction.call(time, planet);
			planet = (Planet) planet.addMul(deltaT, call);
		}
		System.out.println("Earth Position  "+planet.getPosition());
	}
}
