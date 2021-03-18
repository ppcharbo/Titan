package titan.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import titan.RateInterface;
import titan.impl.ODEFunction;
import titan.impl.Planet;

public class ODEFunctionTest {

	@Test
	public void testCall() {
		
		ODEFunction odeFunction = new ODEFunction();
		System.out.println("Eath Position  "+Planet.EARTH.getPosition());
		//after 1 hours
		for (int time = 0; time < 3600*24*365.25; time++) {
		RateInterface call = odeFunction.call(time, Planet.EARTH);
		}
		System.out.println("Eath Position  "+Planet.EARTH.getPosition());
		
	}

	@Test
	public void testCallAll() {
		
		ODEFunction odeFunction = new ODEFunction();
		
		
		for (int time = 0; time < 3600*24*365.25; time++) {
		Planet[] call = odeFunction.callForAllPlanets(time,Planet.values());
		}
		
		
	}

}
