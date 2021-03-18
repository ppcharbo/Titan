package titan;

import static org.junit.Assert.*;

import org.junit.Test;

import titan.impl.Planet;
import titan.impl.ProbeSimulator;

public class ProbeSimulatorInterfaceTest {

	@Test
	public void testTrajectoryVector3dInterfaceVector3dInterfaceDoubleArray() {
		 
		ProbeSimulatorInterface probeSimulator = new ProbeSimulator();
		
		double[] ts = {100D,200D};
		probeSimulator.trajectory(Planet.SHIP.getPosition(), Planet.SHIP.getPosition(),ts );
	}

	@Test
	public void testTrajectoryVector3dInterfaceVector3dInterfaceDoubleDouble() {
		fail("Not yet implemented");
	}

}
