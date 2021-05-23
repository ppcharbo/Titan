package titan.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import titan.StateInterface;
import titan.Vector3dInterface;

public class Simulator {

	public static void main(String[] args) {
		String planet = "titan";
		getTrajectory(planet);

	}

	private static void getTrajectory(String planet) {
		int element = 0; // Simulate ship by default
		if(planet.equals("titan")){
			element = 10;
		}
		
		Vector3dInterface[] trajectory = simulateOneYear(element);
		try {
			FileWriter writer = new FileWriter("trajectoryUniversal.csv");
			//System.out.println("trajectory length: " + trajectory.length);
			String header = "day, x, y, z";
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

	}

	private static Vector3dInterface[] simulateOneYear(int element) {
		
		/* Provided from the test case:
		Vector3dInterface probe_relative_position = new Vector3d(6371e3, 0, 0);
		Vector3dInterface probe_relative_velocity = new Vector3d(52500.0, -27000.0, 0); // 12.0 months
		*/
		
		Vector3dInterface probe_relative_position = new Vector3d(3.926620508447322E6, -5.017051486490201E6, 0.022062741157029E6);
		Vector3dInterface probe_relative_velocity = new Vector3d(3.697963122066227E6, -4.724895451097348E6, 0.020777970011329E6);
		
		double day = 24 * 60 * 60;
		double year = 365.25 * day;
		Vector3dInterface[] trajectory = trajectory(probe_relative_position, probe_relative_velocity, year, day, element);
		return trajectory;
	}

	private static Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h, int element) {
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
		ODESolver solver = new ODESolver();
		StateInterface[] solvedStates = solver.solve(new ODEFunction(), beginState, tf, h);
		
		Vector3dInterface[] returnPositions = new Vector3d[((int) Math.ceil(tf / h) + 1)];
		
		for (int a = 0; a < solvedStates.length; a++) {
			
			returnPositions[a] = ((State) solvedStates[a]).getPosition()[element]; //this should be position element
		}
		
		return returnPositions;
	}
		
		
}
