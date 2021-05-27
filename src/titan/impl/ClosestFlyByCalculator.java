package titan.impl;

import java.util.ArrayList;

import titan.StateInterface;
import titan.Vector3dInterface;

public class ClosestFlyByCalculator {

	public ArrayList<PlanetGUI> allPlanets = new ArrayList<PlanetGUI>();
	public StateInterface[] solvedStates;

	public double size = 1;
	public static int delay = 25;
	public int currentState = 0;
	public boolean stop = true;
	public int defaultCounter = 0;
	
	public Vector3dInterface p0 = new Vector3d(4154116.78496650,	-4830374.71365795,	20853.3573652752);
	public Vector3dInterface v0 = new Vector3d(72684.6410404669,	-107781.235228466,	385.083685268718);

	public ClosestFlyByCalculator() {
		
		solvedStates = simulateOneYear(p0, v0);
		
		//PlanetGUI(String string, int r, int g, int b, Vector3d position, double d) {
		allPlanets.add(new PlanetGUI("SHIP", 0, 255, 0, ((State) solvedStates[0]).getPosition()[0], 10));
		allPlanets.add(new PlanetGUI("SUN", 255, 140, 0, ((State) solvedStates[0]).getPosition()[1], 50));
		allPlanets.add(new PlanetGUI("MOON", 192, 192, 192, ((State) solvedStates[0]).getPosition()[2], 10));
		allPlanets.add(new PlanetGUI("MERCURY", 128, 128, 128, ((State) solvedStates[0]).getPosition()[3], 10));
		allPlanets.add(new PlanetGUI("VENUS", 207, 153, 52, ((State) solvedStates[0]).getPosition()[4], 20));
		allPlanets.add(new PlanetGUI("EARTH", 0, 0, 255, ((State) solvedStates[0]).getPosition()[5], 20));
		allPlanets.add(new PlanetGUI("MARS", 255, 0, 0, ((State) solvedStates[0]).getPosition()[6], 15));
		allPlanets.add(new PlanetGUI("JUPITER", 255, 140, 0, ((State) solvedStates[0]).getPosition()[7], 45));
		allPlanets.add(new PlanetGUI("SATURN", 112, 128, 144, ((State) solvedStates[0]).getPosition()[8], 42));
		allPlanets.add(new PlanetGUI("URANUS", 196, 233, 238, ((State) solvedStates[0]).getPosition()[9], 50));
		allPlanets.add(new PlanetGUI("TITAN", 218, 165, 32, ((State) solvedStates[0]).getPosition()[10], 10));
		allPlanets.add(new PlanetGUI("NEPTUNE", 66, 98, 243, ((State) solvedStates[0]).getPosition()[11], 38));
	}
	
	
	public double sim(Vector3dInterface p, Vector3dInterface v) {

		solvedStates = simulateOneYear(p, v);
		
		return doesItComeClose();
	}

	
	public double doesItComeClose() {
		
		Vector3d[] shipPositions = new Vector3d[solvedStates.length];
		Vector3d[] titanPositions = new Vector3d[solvedStates.length];

		for (int i = 0; i < solvedStates.length; i++) {
			
			shipPositions[i] = ((State) solvedStates[i]).getPosition()[0];
			titanPositions[i] = ((State) solvedStates[i]).getPosition()[10];
		}

		double min = Double.MAX_VALUE;
		for (Vector3dInterface shipVector : shipPositions) {
			
			for (Vector3dInterface titanVector : titanPositions) {
				
				if(Math.abs(shipVector.dist(titanVector)) <= 3E10) {
					
				}
				if(Math.abs(shipVector.dist(titanVector)) < min) {
					
					min = Math.abs(shipVector.dist(titanVector));
				}
			}
		}
		return min;
	}

	
	private void gameLoop() {

		while (currentState < solvedStates.length) {
			
			for (int a = 0; a < 12; a++) {
				
				allPlanets.get(a).update(((State) solvedStates[currentState]).getPosition()[a]);
			}
			currentState += 1;

			try {
				
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				
			}
		}
	}

	
	public StateInterface[] simulateOneYear(Vector3dInterface p, Vector3dInterface v) {

		Vector3dInterface probe_vel = v;
		Vector3dInterface probe_pos = p;

		double day = 24*60*60;
		double year = 365.25*24*day;

		ProbeSimulatorEuler simulator = new ProbeSimulatorEuler();
		StateInterface[] states = simulator.trajectoryGUI(probe_pos, probe_vel, year, day);

		return states;
	}

	
	public void startMe() {
		
		Thread thread = new Thread() {

			@Override
			public void run() {
				
				gameLoop();
			}
		};
		thread.start();
	}
}