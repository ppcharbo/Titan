package titan.test;

import static org.junit.Assert.assertEquals;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import titan.ProbeSimulatorInterface;
import titan.Vector3dInterface;
import titan.impl.ProbeSimulatorRungeKutta;
import titan.impl.Vector3d;

public class ProbeSimulatorTestRungaKutta {

	static final double ACCURACY = 1E3; // 1 meter (might need to tweak that)

	
	@Test
	public void testTrajectoryOneDayXRK4() {

		Vector3dInterface[] trajectory = simulateOneDayRK4();
		double x1 = -1.4218092965609787E11; // reference implementation
		double x = trajectory[1].getX();
		assertEquals(x1, x, ACCURACY); // delta +-ACCURACY

	}

	@Test
	public void testTrajectoryOneDayYRK4() {

		Vector3dInterface[] trajectory = simulateOneDayRK4();
		double y1 = -3.3475191084301098E10; // reference implementation
		double y = trajectory[1].getY();
		assertEquals(y1, y, ACCURACY); // delta +-ACCURACY

	}

	@Test
	public void testTrajectoryOneDayZRK4() {

		Vector3dInterface[] trajectory = simulateOneDayRK4();
		double z1 = 8334994.892882561; // reference implementation
		assertEquals(z1, trajectory[1].getZ(), ACCURACY); // delta +-ACCURACY

	}
	

	@Test
	public void testTrajectoryOneYearXRK4() {

		Vector3dInterface[] trajectory = simulateOneYearRK4();
		double x366 = -2.4951517995514418E13; // reference implementation
		assertEquals(x366, trajectory[366].getX(), ACCURACY); // delta +-ACCURACY

	}

	@Test
	public void testTrajectoryOneYearYRK4() {

		Vector3dInterface[] trajectory = simulateOneYearRK4();
		double y366 = -1.794349344879982E12; // reference implementation
		assertEquals(y366, trajectory[366].getY(), ACCURACY); // delta +-ACCURACY

	}

	@Test
	public void testTrajectoryOneYearZRK4() {

		Vector3dInterface[] trajectory = simulateOneYearRK4();
		double z366 = 2.901591968932223E7; // reference implementation
		assertEquals(z366, trajectory[366].getZ(), ACCURACY); // delta +-ACCURACY

	}

	
	@Test
	public void testTrajectoryLengthRK4() {

		Vector3dInterface[] trajectory = simulateOneYearRK4();
		try {
			FileWriter writer = new FileWriter("trajectory.csv");
			System.out.println("trajectory length: " + trajectory.length);
			String header = "day,x,y,z";
			System.out.println(header);
			writer.write(header + "\n");
			for (int i = 0; i < trajectory.length; i++) {
				String row = i + "," + trajectory[i].getX() + "," + trajectory[i].getY() + "," + trajectory[i].getZ();
				System.out.println(row);
				writer.write(row + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		assertEquals(367, trajectory.length);

	}

	
	public static Vector3dInterface[] simulateOneDayRK4() {

		Vector3dInterface probe_relative_position = new Vector3d(6371e3, 0, 0);
		Vector3dInterface probe_relative_velocity = new Vector3d(52500.0, -27000.0, 0); // 12.0 months
		double day = 24 * 60 * 60;
		ProbeSimulatorInterface simulator = new ProbeSimulatorRungeKutta();
		Vector3dInterface[] trajectory = simulator.trajectory(probe_relative_position, probe_relative_velocity, day,
				day);
		return trajectory;

	}
	

	public static Vector3dInterface[] simulateOneYearRK4() {

		Vector3dInterface probe_relative_position = new Vector3d(6371e3, 0, 0);
		Vector3dInterface probe_relative_velocity = new Vector3d(52500.0, -27000.0, 0); // 12.0 months
		double day = 24 * 60 * 60;
		double year = 365.25 * day;
		ProbeSimulatorInterface simulator = new ProbeSimulatorRungeKutta();
		Vector3dInterface[] trajectory = simulator.trajectory(probe_relative_position, probe_relative_velocity, year, day);
		return trajectory;

	}
	
	public static Vector3dInterface[] simulateOneYearHalfStepRK4() {

		Vector3dInterface probe_relative_position = new Vector3d(6371e3, 0, 0);
		Vector3dInterface probe_relative_velocity = new Vector3d(52500.0, -27000.0, 0); // 12.0 months
		double twelveHours = 60 * 60 * 12;
		double year = 365.25 * 8 * twelveHours;
		ProbeSimulatorInterface simulator = new ProbeSimulatorRungeKutta();
		Vector3dInterface[] trajectory = simulator.trajectory(probe_relative_position, probe_relative_velocity, year, twelveHours);
		return trajectory;

	}
	
	
	
	@Test
	public void testTrajectoryXInitialRK4() {

		Vector3dInterface[] trajectory = simulateOneDayRK4();
		double x0 = -1.471922101663588e+11 + 6371e3; // reference implementation
		double x = trajectory[0].getX();
		assertEquals(x0, x, ACCURACY); // delta +-ACCURACY

	}

	@Test
	public void testTrajectoryYInitialRK4() {

		Vector3dInterface[] trajectory = simulateOneDayRK4();
		double y0 = -2.860995816266412e+10; // reference implementation
		double y = trajectory[0].getY();
		assertEquals(y0, y, ACCURACY); // delta +-ACCURACY

	}

	@Test
	public void testTrajectoryZInitialRK4() {

		Vector3dInterface[] trajectory = simulateOneDayRK4();
		double z0 = 8.278183193596080e+06; // reference implementation
		double z = trajectory[0].getZ();
		assertEquals(z0, z, ACCURACY); // delta +-ACCURACY

	}
	

}
