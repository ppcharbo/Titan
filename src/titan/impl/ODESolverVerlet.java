package titan.impl;

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
				// we are in last step and have to check our remaining step size
				stepSize = tf - currentTime;
			}
			// switch the two lines
			arr[i] = step(f, currentTime, arr[i - 1], stepSize);
			currentTime += stepSize;
		}
		return arr;
	}

	@Override
	public State step(ODEFunctionInterface f, double t, StateInterface y, double h) { // Four-step velocity Verlet algorithm
		
		Rate currentRate = (Rate) f.call(t, y); // current rate to access current acceleration
		
		Vector3d currentPosition[] = ((State)y).getPosition(); // current position
		Vector3d currentVelocity[]= ((State)y).getVelocity(); // current velocity
		Vector3d currentAcceleration[] = currentRate.getAcceleration(); // current acceleration
		
		Vector3d nextPosition[] = new Vector3d[currentPosition.length]; // following position initialization  
		Vector3d intermediateVelocity[] = new Vector3d[currentVelocity.length]; // intermediate velocity initialization - midpoint
		Vector3d nextVelocity[] = new Vector3d[currentVelocity.length]; // following velocity initialization
		
		for (int i = 0; i < currentVelocity.length; i++) { // for loop to update positions(at t+h) and velocities(at an intermediate state) for every corresponding planet present in the array
			
			nextPosition[i] = (Vector3d) currentPosition[i].addMul(h, currentVelocity[i]).addMul(0.5*(Math.pow(h, 2)), currentAcceleration[i]); // first step: updating position at t+h
			intermediateVelocity[i] = (Vector3d) currentVelocity[i].addMul(0.5*h, currentAcceleration[i]); // second step: updating velocity at an intermediate state t+(h/2) 
		}
			
		State intermediateState = new State(nextPosition, intermediateVelocity, t+h); // intermediate state to access corresponding acceleration 
		Vector3d nextAcceleration[] = ((Rate)f.call(t+h, intermediateState)).getAcceleration(); // third step: updating acceleration at t+h
		
		for (int i = 0; i < currentVelocity.length; i++) { // for loop to update velocities(at t+h) for every corresponding planet present in the array
			
			nextVelocity[i] = (Vector3d) intermediateVelocity[i].addMul(0.5*h, nextAcceleration[i]); // fourth step: updating velocity at t+h
		}
		
		State nextState = new State(nextPosition, nextVelocity, t+h); // computed following state
		
		
		return nextState; 
	}
}
