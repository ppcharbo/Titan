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

	
	/**
	 * 
	 * @param planet to get its trajectory over a year
	 */
	private static void getTrajectory(String planet) {
		
		int element = 0; // simulate ship by default
		
		if(planet.equals("titan")){
			
			element = 10;
		}
		Vector3dInterface[] trajectory = simulateOneYear(element);
		
		try {
			
			FileWriter writer = new FileWriter("trajectoryUniversal.csv");
		
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

	
	/**
	 * 
	 * @param element
	 * @return the trajectory over a year
	 */
	private static Vector3dInterface[] simulateOneYear(int element) {
		
		/* Provided from the test case:
		Vector3dInterface probe_relative_position = new Vector3d(6371e3, 0, 0);
		Vector3dInterface probe_relative_velocity = new Vector3d(52500.0, -27000.0, 0); // 12.0 months
		*/
		Vector3dInterface probe_relative_position = new Vector3d(3.609867510498535E6, -5.249581360565903E6, 0.019826634766418E6);
		Vector3dInterface probe_relative_velocity = new Vector3d(3.697963122066227E6, -4.724895451097348E6, 0.020777970011329E6);
		
		double day = 24 * 60 * 60;
		double year = 365.25 * day;
		
		Vector3dInterface[] trajectory = trajectory(probe_relative_position, probe_relative_velocity, year, day, element); // TODO print several different trajectories
		
		return trajectory;
	}

	
	/*
	 * Simulate the solar system, including a probe fired from Earth at 00:00h on 1
	 * April 2020.
	 *
	 * @param p0 the starting position of the probe, relative to the earth's
	 * position.
	 * 
	 * @param v0 the starting velocity of the probe, relative to the earth's
	 * velocity.
	 * 
	 * @param ts the times at which the states should be output, with ts[0] being
	 * the initial time.
	 * 
	 * @return an array of size ts.length giving the position of the probe at each
	 * time stated, taken relative to the Solar System barycentre.
	 */
	private static Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h, int element) {
		
		AllPlanets allPlanets = new AllPlanets();
		allPlanets.createPlanets();
		ArrayList<Planet> listOfPlanets = allPlanets.getListOfPlanets();
		
		boolean[] isShip = new boolean[listOfPlanets.size()];
		isShip[0] = true;
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
		State beginState = new State(beginPositions, beginVelocities, isShip, 0);
		ODESolverEuler solver = new ODESolverEuler();
		StateInterface[] solvedStates = solver.solve(new ODEFunction(), beginState, tf, h); 
		
		Vector3dInterface[] returnPositions = new Vector3d[((int) Math.ceil(tf / h) + 1)];
		
		for (int a = 0; a < solvedStates.length; a++) {
			
			returnPositions[a] = ((State) solvedStates[a]).getPosition()[element]; //this should be position element
		}
		return returnPositions; 
	}
	
	
	/**
	 * 
	 * @param element
	 * @return the fuel costs of an X-days journey
	 */
	private static double simulateXDays(int element) { // X to be found
			
			/* Provided from the test case:
			Vector3dInterface probe_relative_position = new Vector3d(6371e3, 0, 0);
			Vector3dInterface probe_relative_velocity = new Vector3d(52500.0, -27000.0, 0); // 12.0 months
			*/
			Vector3dInterface probe_relative_position = new Vector3d(3.609867510498535E6, -5.249581360565903E6, 0.019826634766418E6);
			Vector3dInterface probe_relative_velocity = new Vector3d(3.697963122066227E6, -4.724895451097348E6, 0.020777970011329E6);
			
			double day = 24 * 60 * 60;
			double year = 365.25 * day;
			double fuelCost = fuelCosts(probe_relative_position, probe_relative_velocity, year, day, element);
			
			return fuelCost;
		}
	
	
	/**
	 * 
	 * @param p0
	 * @param v0
	 * @param tf
	 * @param h
	 * @param element
	 * @return the fuel costs of a ship following the trajectory calculated by the method parameters 
	 */
	private static double fuelCosts(Vector3dInterface p0, Vector3dInterface v0, double tf, double h, int element) {
		
		AllPlanets allPlanets = new AllPlanets();
		allPlanets.createPlanets();
		ArrayList<Planet> listOfPlanets = allPlanets.getListOfPlanets();
		
		boolean[] isShip = new boolean[listOfPlanets.size()];
		isShip[0] = true;
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
		State beginState = new State(beginPositions, beginVelocities, isShip, 0);
		ODESolverEuler solver = new ODESolverEuler();
		StateInterface[] solvedStates = solver.solve(new ODEFunction(), beginState, tf, h); 
		
		Vector3dInterface[] returnPositions = new Vector3d[((int) Math.ceil(tf / h) + 1)];
		
		for (int a = 0; a < solvedStates.length; a++) {
			
			returnPositions[a] = ((State) solvedStates[a]).getPosition()[element]; //this should be position element
		}
		return ShipFuelCosts.fuelCost(tf); // need to find the fastest trajectory to optimize the fuel costs
	}	
}