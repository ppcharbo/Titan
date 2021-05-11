package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

public class ODESolverRungeKutta implements ODESolverInterface {

	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {

		StateInterface[] states = new StateInterface[ts.length];
		
		states[0] = y0;

		for (int i = 1; i < states.length; i++) {

			double stepSize = ts[i] - ts[i - 1];
			states[i] = step(f, ts[i-1], states[i - 1], stepSize);
		}
		return states;
	}

	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {

		StateInterface[] states = new StateInterface[(int) Math.round((tf / h) + 1)];
		
		states[0] = y0;
		
		double stepSize = h;
		double currentTime = 0;

		for (int i = 1; i < states.length; i++) {

			if (i == states.length - 1) {
				// we are in last step and have to check our remaining step size
				stepSize = tf - currentTime;
			}
			states[i] = step(f, currentTime, states[i - 1], stepSize);
			currentTime += stepSize;
		}
		return states;
	}

	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

		Rate k1 = (Rate) f.call(t, y);
		Rate k2 = (Rate) f.call(t + h / 2, y.addMul(h / 2, k1));
		Rate k3 = (Rate) f.call(t + h / 2, y.addMul(h / 2, k2));
		Rate k4 = (Rate) f.call(t + h, y.addMul(h, k3));

		return y.addMul(h / 6, Rate.addRates(k1, k2, k3, k4));
	}
}
