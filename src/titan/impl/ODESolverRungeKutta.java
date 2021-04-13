package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

public class ODESolverRungeKutta implements ODESolverInterface {

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
					stepSize = tf - currentTime;
				}
				currentTime += stepSize;
				arr[i] = step(f, currentTime, arr[i - 1], stepSize);
			}
			return arr;
		}

		@Override
		public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
			
			Rate k1 = (Rate) f.call(t, y);
			Rate k2 = (Rate) f.call(t+h/2, y.addMul(h/2, k1));
			Rate k3 = (Rate) f.call(t+h/2, y.addMul(h/2, k2));
			Rate k4 = (Rate) f.call(t+h, y.addMul(h, k3));
			
			return y.addMul(h/6, Rate.addRates(k1, k2, k3, k4));
		}
	}

