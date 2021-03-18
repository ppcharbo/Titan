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
		Vector3dInterface[] positions = probeSimulator.trajectory(Planet.SHIP.getPosition(), Planet.SHIP.getVelocity(),ts );
		
		for (Vector3dInterface vector3dInterface : positions) {
			System.out.println("posistion "+vector3dInterface);
		}
	}

 

}
