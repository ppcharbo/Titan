package titan.impl;

import titan.StateInterface;
import titan.Vector3dInterface;

public class MainTestTrajectoryM {

	public static double globalMinimum = Double.MAX_VALUE;
	public static double globalMinimumi = 0;
	
	public static void main(String[] args) {
		
		TrajectoryM goedZo = new TrajectoryM();
		StateInterface[] solvedStates = goedZo.solvedStates;
		
		Vector3dInterface pEarth = ((State) (solvedStates[0])).getPosition()[5];
		Vector3dInterface vEarth = ((State) (solvedStates[0])).getVelocity()[5];
		
		//System.out.println("Max length of i: " + solvedStates.length);
		
		
		for (int i = 0; i < solvedStates.length; i++) {
			Vector3dInterface pTitan = ((State) (solvedStates[i])).getPosition()[10];
			Vector3dInterface vTitan = ((State) (solvedStates[i])).getVelocity()[10];
			
			Vector3dInterface green = pTitan.sub(pEarth);
			Vector3dInterface vGreen = vTitan.sub(vEarth);
			
			double constantPosition = 1/(green.norm()/(6371E3));
			double constantVelocity = 1/(vGreen.norm()/(60E3)); //60 km/s max launch speed
			
			Vector3dInterface pLaunch = green.mul(constantPosition);
			Vector3dInterface vLaunch = vGreen.mul(constantVelocity);
			
			double min = goedZo.sim(pLaunch, vLaunch);
			if(min < globalMinimum) {
				globalMinimum = min;
				globalMinimumi  = i;
			}
			System.out.println("For iteration i = "+ i+ " is min = " + min);
		}
		
		System.out.println("Ultimate min: " + globalMinimum);
		System.out.println("at i: " + globalMinimumi);
	}
	
	
	public void closest(int i) {
		
		TrajectoryM goedZo = new TrajectoryM();
		StateInterface[] solvedStates = goedZo.solvedStates;
		
		Vector3dInterface pEarth = ((State) (solvedStates[0])).getPosition()[5];
		Vector3dInterface vEarth = ((State) (solvedStates[0])).getVelocity()[5];
		
		Vector3dInterface pTitan = ((State) (solvedStates[i])).getPosition()[10];
		Vector3dInterface vTitan = ((State) (solvedStates[i])).getVelocity()[10];
		
		Vector3dInterface green = pTitan.sub(pEarth);
		Vector3dInterface vGreen = vTitan.sub(vEarth);
		
		double constantPosition = 1/(green.norm()/(6371E3));
		double constantVelocity = 1/(vGreen.norm()/(60E3));
		
		Vector3dInterface pLaunch = green.mul(constantPosition);
		Vector3dInterface vLaunch = green.mul(constantVelocity);
		
		System.out.println("Check min at position i:");
		System.out.println(goedZo.sim(pLaunch, vLaunch));
	}
	

}
