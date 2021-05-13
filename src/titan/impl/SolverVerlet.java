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
// swich the two lines
			arr[i] = step(f, currentTime, arr[i - 1], stepSize);
			currentTime += stepSize;
		}
		return arr;
	}

	@Override
	public State step(ODEFunctionInterface f, double t, StateInterface y, double h) {
	Vector3d v1[]= ((State)y).getVelocity();
	
	RateInterface call = f.call(t, y);
	
	return null;

	}

}
