package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

public class ODESolverRungeKutta implements ODESolverInterface {

	@Override
	public State[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {

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

		Rate k1 = (Rate) f.call(t, y);
		Rate k2 = (Rate) f.call(t + h / 2, y.addMul(h / 2, k1));
		Rate k3 = (Rate) f.call(t + h / 2, y.addMul(h / 2, k2));
		Rate k4 = (Rate) f.call(t + h, y.addMul(h, k3));

		State newY = (State) y.addMul(h / 6, k1.addMul(2, k2).addMul(2, k3).addMul(1, k4));
		
		return  newY;
	}
}
