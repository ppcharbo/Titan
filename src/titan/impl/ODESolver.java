package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

public class ODESolver implements ODESolverInterface {

	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
		StateInterface[] arr = new StateInterface[ts.length];
		arr[0] = y0;
		double stepSize;
		for (int i = 1; i < arr.length; i++) {
			stepSize = ts[i] - ts[i - 1];
			arr[i] = step(f, ts[i], arr[i - 1], stepSize);
		}
		return arr;
	}

	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
		
		StateInterface[] arr = new StateInterface[(int) Math.round((tf / h) + 1)];
		arr[0] = y0;
		double stepSize = h;
		double currentTime = 0;
		for (int i = 1; i < arr.length; i++) {
			if (i == arr.length - 1) {
				// we are in last step and have to check our remaining step size
				stepSize = Math.IEEEremainder(tf, h);
			}
			currentTime += stepSize;
			arr[i] = step(f, currentTime, arr[i - 1], stepSize);
		}
		return arr;
	}

	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

		return y.addMul(h, f.call(t, y));
	}
}
