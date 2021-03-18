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
		
		double time = 0;
		RateInterface call = odeFunction.call(time, Planet.EARTH);
		
		
	}

}
