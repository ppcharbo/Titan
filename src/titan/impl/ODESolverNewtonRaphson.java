package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

/*
 * A class for solving a general differential equation dy/dt = f(t,y)
 *     y(t) describes the state of the system at time t
 *     f(t,y(t)) defines the derivative of y(t) with respect to time t
 */
public class ODESolverNewtonRaphson implements ODESolverInterface {
	
}
	
	/*
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
	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
		
		StateInterface[] arr = new StateInterface[(int) Math.ceil((tf / h) + 1)];
		int i = 0;
		arr[i] = y0;
		((State) y0).setTime(0);
		
		double currentTime = 0;

		while(currentTime < tf) {
			
			// operations:
			// w(i+1) = w(i) + h*f(t,y)
			// y_next = y_current + h*v
			// w(i+1) = w(i) + h*f(t,y)
			arr[i+1] = step(f, currentTime, arr[i], h); // calculate the next step
			((State) arr[i+1]).setTime(currentTime+h); // set time for the next step
			i += 1; // update array position
			currentTime += h; // update time
		}
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
	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
		
		Rate currentRate = (Rate) f.call(t, y);
		
		Vector3d currentPosition[] = ((State)y).getPosition();
		Vector3d currentVelocity[]= ((State)y).getVelocity();
		
		State previousState = (State)y.addMul(t-h, currentRate);
		
		Vector3d previousPosition[] = ((State)y).getPosition();
		Vector3d previousVelocity[]= ((State)y).getVelocity();
		
		State nextState = (State)y.addMul(t+h, currentRate);
		
		Vector3d nextPosition[] = ((State)y).getPosition();
		Vector3d nextVelocity[]= ((State)y).getVelocity();
		
		
		double [][] jacobianMatrix = new double [3][3];
		
		double[] nextPositionX = new double [nextPosition.length];
		double[] nextPositionY = new double [nextPosition.length];
		double[] nextPositionZ = new double [nextPosition.length];
		
		double[] previousPositionX = new double [previousPosition.length];
		double[] previousPositionY = new double [previousPosition.length];
		double[] previousPositionZ = new double [previousPosition.length];
		
				
		for (int i=0; i<nextPosition.length; i++) {
			
			nextPositionX[i] = nextPosition[i].getX();
			nextPositionY[i] = nextPosition[i].getY();
			nextPositionZ[i] = nextPosition[i].getZ();
			
			previousPositionX[i] = previousPosition[i].getX();
			previousPositionY[i] = previousPosition[i].getY();
			previousPositionZ[i] = previousPosition[i].getZ();
			
			double derivativeX = (double) (nextPositionX[i] - previousPositionX[i]) / 2*h; // f(x+h) - f(x-h) / 2h
			double derivativeY = (double) (nextPositionY[i] - previousPositionY[i]) / 2*h; // f(y+h) - f(y-h) / 2h
			double derivativeZ = (double) (nextPositionZ[i] - previousPositionZ[i]) / 2*h; // f(z+h) - f(z-h) / 2h
			
			for (int j=0; j<jacobianMatrix.length; j++) {
				for (int k=0; k<jacobianMatrix.length-1; k++) {
					
					jacobianMatrix[j][k] = null;
				}
			}
		}
		
		return null; //TODO 
	}
}
