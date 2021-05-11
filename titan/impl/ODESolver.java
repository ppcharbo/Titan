package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;


public class ODESolver implements ODESolverInterface {

	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
		StateInterface[] arr = new StateInterface[ts.length];
		arr[0] = y0;

		for (int i = 1; i < arr.length; i++) {

			double stepSize = ts[i] - ts[i - 1];
			arr[i] = step(f, ts[i-1], arr[i - 1], stepSize);
		}
		return arr;
	}

	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
		
		StateInterface[] arr = new StateInterface[(int) Math.round((tf / h) + 1)];
		int i = 0;
		arr[i] = y0;
		i = 1;
		
		
		//double stepSize = h;
		double currentTime = 0;
		
		
		//w(i+1) = w(i) + h*f(t,y)
		//y_next = y_current + h*v
		
		while(currentTime < tf) {
			//operations
			//w(i+1) = w(i) + h*f(t,y)
			arr[i] = step(f, currentTime, arr[i-1], h);
			
			currentTime = currentTime + h;
		}
		/*
		for (int i = 1; i < arr.length; i++) {
			
			if (i == arr.length - 1) {
				// we are in last step and have to check our remaining step size
				stepSize = Math.IEEEremainder(tf, h);
			}
			arr[i] = step(f, currentTime, arr[i - 1], stepSize);
			currentTime += stepSize;
		}
		*/
		return arr;
	}

	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

		return y.addMul(h, f.call(t, y));
	}
}
