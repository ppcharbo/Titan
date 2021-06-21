package titan.impl;

import titan.ODEFunctionInterface;

import titan.ODESolverInterface;
import titan.RateInterface;
import titan.StateInterface;

/**
 * A class that use Runge-Kutta ODE Solver properties to calculate the next position
 * @author Group 12
 *
 */
public class ODESolverRungeKutta implements ODESolverInterface {
	
	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {

		StateInterface[] arr = new StateInterface[ts.length];
		arr[0] = y0;

		for (int i = 1; i < arr.length; i++) {

			double stepSize = ts[i] - ts[i - 1];
			arr[i] = step(f, ts[i - 1], arr[i - 1], stepSize);
		}
		return arr;
	}

	
	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
		
		StateInterface[] arr = new StateInterface[(int) Math.ceil(tf / h)+1];
		arr[0] = y0;
		double currentTime = 0;
		int i = 1;

		while(currentTime < tf) {
			arr[i] = step(f, currentTime, arr[i - 1], h);
			//System.out.println(currentTime);
			currentTime += h;
			i++;
		}
		
		/*
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
		*/
		
		/*
		StateInterface[] arr = new StateInterface[(int) Math.ceil((tf / h) + 1)];
		int i = 0;
		arr[i] = y0;
		((State) y0).setTime(0);
		
		double currentTime = 0;

		while(currentTime < tf) {
	
			arr[i+1] = step(f, currentTime, arr[i], h); // calculate the next step
			((State) arr[i+1]).setTime(currentTime+h); // set time for the next step
			i += 1; 
			currentTime += h; 
		}
		*/
		return arr;
	}

	
	// Fourth-order Runge-Kutta formula
	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

		/*
		Rate k1 = (Rate) f.call(t, y);
		Rate k2 = (Rate) f.call(t + h / 2, y.addMul(h / 2, k1));
		Rate k3 = (Rate) f.call(t + h / 2, y.addMul(h / 2, k2));
		Rate k4 = (Rate) f.call(t + h, y.addMul(h, k3));
		*/
		Rate k1, k2, k3, k4, ki;
		
		k1 = (Rate) f.call(t, y);
		k2 = (Rate) f.call(t + (0.5*h), y.addMul(0.5*h, (RateInterface) k1));
		k3 = (Rate) f.call(t + (0.5*h), y.addMul(0.5*h, (RateInterface) k2));
		k4 = (Rate) f.call(t + h, y.addMul(h, (RateInterface) k3));
		
		ki = k1.addMul(2, k2);
		ki = ki.addMul(2, k3);
		ki = ki.addMul(1, k4);
		
		//ki = ki.mul(h/6);
		
		StateInterface newY = y.addMul(h / 6, (RateInterface) ki);
		
		return newY;
	}
}