package titan.impl;

import java.util.ArrayList;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

public class ODESolverVerlet implements ODESolverInterface {

	@Override
	public State[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
		// TODO Auto-generated method stub
		State[] arr = new State[ts.length];
		arr[0] = (State) y0;

		for (int i = 1; i < arr.length; i++) {

			double stepSize = ts[i] - ts[i - 1];
			arr[i] = step(f, ts[i - 1], arr[i - 1], stepSize);
		}
		return arr;
	}

	
	@Override
	public State[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
		
		State[] arr = new State[(int) Math.ceil((tf / h) + 1)];
		arr[0] = (State) y0;
		double stepSize = h;
		double currentTime = 0;

		for (int i = 1; i < arr.length; i++) {

			if (i == arr.length - 1) {
				
				stepSize = tf - currentTime; // we are in last step and have to check our remaining step size
			}
			arr[i] = step(f, currentTime, arr[i - 1], stepSize);
			currentTime += stepSize;
		}
		return arr;
	}

	
	// Four-step velocity Verlet algorithm
	@Override
	public State step(ODEFunctionInterface f, double t, StateInterface y, double h) { 
		
		AllPlanets allPlanets = new AllPlanets();
		allPlanets.createPlanets();
		ArrayList<Planet> listOfPlanets = allPlanets.getListOfPlanets();
		boolean[] isShip = new boolean[listOfPlanets.size()];
		isShip[0] = true;
		
		Rate currentRate = (Rate) f.call(t, y); 
		
		Vector3d currentPosition[] = ((State)y).getPosition(); 
		Vector3d currentVelocity[]= ((State)y).getVelocity(); 
		Vector3d currentAcceleration[] = currentRate.getAcceleration(); 
		
		Vector3d nextPosition[] = new Vector3d[currentPosition.length];  
		Vector3d intermediateVelocity[] = new Vector3d[currentVelocity.length]; // v(t+h/2)
		Vector3d nextVelocity[] = new Vector3d[currentVelocity.length]; 
		
		for (int i = 0; i < currentVelocity.length; i++) { // update positions(at t+h) and velocities(at the intermediate state) for every corresponding planet 
			
			nextPosition[i] = (Vector3d) currentPosition[i].addMul(h, currentVelocity[i]).addMul(0.5*(Math.pow(h, 2)), currentAcceleration[i]); // first step: updating position at t+h
			intermediateVelocity[i] = (Vector3d) currentVelocity[i].addMul(0.5*h, currentAcceleration[i]); // second step: updating velocity at the midpoint t+(h/2) 
		}
			
		State intermediateState = new State(nextPosition, intermediateVelocity, isShip, t+h); 
		Vector3d nextAcceleration[] = ((Rate)f.call(t+h, intermediateState)).getAcceleration(); // third step: updating acceleration at t+h
		
		for (int i = 0; i < currentVelocity.length; i++) { // update velocities(at t+h) for every corresponding planet 
			
			nextVelocity[i] = (Vector3d) intermediateVelocity[i].addMul(0.5*h, nextAcceleration[i]); // fourth step: updating velocity at t+h
		}
		
		State nextState = new State(nextPosition, nextVelocity, isShip, t+h); 
		
		
		return nextState; 
	}
}