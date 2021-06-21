package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

/**
 *  A class for solving a general differential equation dy/dt = f(t,y) using the Euler method
 *  y(t) describes the state of the system at time t
 *  f(t,y(t)) defines the derivative of y(t) with respect to time t
 *  @author Group 12 
 */
public class ODESolverEuler implements ODESolverInterface {
	
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
			arr[i] = step(f, ts[i-1], arr[i - 1], stepSize);
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
	 * Update rule for one step.
	 *
	 * @param   f   the function defining the differential equation dy/dt=f(t,y)
	 * @param   t   the time
	 * @param   y   the state
	 * @param   h   the step size
	 * @return  the new state after taking one step
	 */
	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
		return y.addMul(h, f.call(t, y));
	}
}
