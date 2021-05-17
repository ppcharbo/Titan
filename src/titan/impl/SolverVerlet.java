package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public class SolverVerlet implements ODESolverInterface {

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
		
		State[] arr = new State[(int) Math.round((tf / h) + 1)];
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
	public State step(ODEFunctionInterface f, double t, StateInterface y, double h) {
		
		Rate currentRate = (Rate) f.call(t, y); // current rate
		
		Vector3d currentPosition[] = ((State)y).getPosition(); // ri(t)
		Vector3d currentVelocity[]= ((State)y).getVelocity(); // vi(t)
		Vector3d currentAcceleration[] = currentRate.getAcceleration(); // current acceleration
		
		Vector3d nextPosition[] = new Vector3d[currentPosition.length]; // following position
		Vector3d intermediateVelocity[] = new Vector3d[currentVelocity.length]; // intermediate velocity
		Vector3d nextVelocity[] = new Vector3d[currentVelocity.length]; // following velocity
		
		for (int i = 0; i < currentVelocity.length; i++) {
			
			nextPosition[i] = (Vector3d) currentPosition[i].addMul(h, currentVelocity[i]).addMul(0.5*(Math.pow(h, 2)), currentAcceleration[i]); // first step: updating the position at t+h
			intermediateVelocity[i] = (Vector3d) currentVelocity[i].addMul(0.5*h, currentAcceleration[i]); // second step: updating the velocity at an intermediate state t+1/2h
		}
			
		State intermediateState = new State(nextPosition, intermediateVelocity, t+h);
		Vector3d nextAcceleration[] = ((Rate)f.call(t+h, intermediateState)).getAcceleration(); // third step: updating the acceleration at t+h
		
		for (int i = 0; i < currentVelocity.length; i++) {
			
			nextVelocity[i] = (Vector3d) intermediateVelocity[i].addMul(0.5*h, nextAcceleration[i]); // fourth step: updating the velocity at t+h
		}
		
		State nextState = new State(nextPosition, nextVelocity, t+h); // following state
		
		
		return nextState;
	}
}
