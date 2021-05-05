package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;


public class ODESolver implements ODESolverInterface {

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
		
		double currentTime = 0;
		
		int i = 1;
		while(currentTime <= (tf-h)) {
			
			states[i] = step(f, currentTime, states[i-1], h);
			
			currentTime = currentTime + h;
			i++;
		}
		return states;
	}

	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

		return y.addMul(h, f.call(t, y));
	}
}
