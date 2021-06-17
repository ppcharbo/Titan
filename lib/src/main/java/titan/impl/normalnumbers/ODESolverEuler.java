package titan.impl.normalnumbers;

/*
 * A class for solving a general differential equation dy/dt = f(t,y)
 *     y(t) describes the state of the system at time t
 *     f(t,y(t)) defines the derivative of y(t) with respect to time t
 */
public class ODESolverEuler {
	
	/*
	 * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
	 * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
	 *
	 * @param   f       the function defining the differential equation dy/dt=f(t,y)
	 * @param   y0      the starting state
	 * @param   tf      the final time
	 * @param   h       the size of step to be taken
	 * @return  an array of size round(tf/h)+1 including all intermediate states along the path
	 */
	public State[] solve(ODEFunction f, State y0, double tf, double h) {
		
		State[] arr = new State[(int) Math.ceil(tf / h-1)];
		arr[0] = y0;
		double currentTime = y0.getTime();
		int i = 1;
		
		while(currentTime < tf) {
			arr[i] = step(f, currentTime, arr[i - 1], h);
			//System.out.println(currentTime);
			currentTime += h;
			i++;
		}
		
		/*
		State[] arr = new State[(int) Math.ceil(tf / h)];
		arr[0] = y0;
		double currentTime = y0.getTime();

		for (int i = 1; i < arr.length; i++) {
			arr[i] = step(f, currentTime, arr[i - 1], h);
			currentTime += h;
		}
		*/
		return arr;
	}

	
	/*
	 * Update rule for one step.
	 *
	 * @param   f   the function defining the differential equation dy/dt=f(t,y)
	 * @param   t   the time
	 * @param   y   the state
	 * @param   h   the step size
	 * @return  the new state after taking one step
	 */
	public State step(ODEFunction f, double t, State y, double h) {

		return (State) y.addMul(h, f.call(t, y));
	}
}