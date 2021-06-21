package titan.impl;

import titan.ODEFunctionInterface;

import titan.ODESolverInterface;
import titan.RateInterface;
import titan.StateInterface;

/**
 * A class that use Runge-Kutta ODE Solver properties to calculate the next position
 * @author Group 12
 */
public class ODESolverRungeKutta implements ODESolverInterface {
	
	/**
	 * Solve the differential equation by taking multiple steps.
	 *
	 * @param   f       the function defining the differential equation dy/dt=f(t,y)
	 * @param   y0      the starting state
	 * @param   ts      the times at which the states should be output, with ts[0] being the initial time
	 * @return  an array of size ts.length with all intermediate states along the path
	 */
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

	/**
	 * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
	 * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
	 *
	 * @param   f       the function defining the differential equation dy/dt=f(t,y)
	 * @param   y0      the starting state
	 * @param   tf      the final time
	 * @param   h       the size of step to be taken
	 * @return  an array of size round(tf/h)+1 including all intermediate states along the path
	 */
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
		
		return arr;
	}
	
	/**
	 * Update rule for one step using the fourth-order Runge-Kutta.
	 *
	 * @param   f   the function defining the differential equation dy/dt=f(t,y)
	 * @param   t   the time
	 * @param   y   the state
	 * @param   h   the step size
	 * @return  the new state after taking one step
	 */
	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

		
		Rate k1 = (Rate) f.call(t, y);
		Rate k2 = (Rate) f.call(t + h / 2, y.addMul(h / 2, k1));
		Rate k3 = (Rate) f.call(t + h / 2, y.addMul(h / 2, k2));
		Rate k4 = (Rate) f.call(t + h, y.addMul(h, k3));
		
		Rate ki; // compute in summation
		ki = k1.addMul(2, k2);
		ki = ki.addMul(2, k3);
		ki = ki.addMul(1, k4);
		
		StateInterface newY = y.addMul(h / 6, (RateInterface) ki);
		
		return newY;
	}
}
