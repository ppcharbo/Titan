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
			
			arr[i+1] = step(f, currentTime, arr[i], h); // calculate the next step
			((State) arr[i+1]).setTime(currentTime+h); // set time for the next step
			i += 1; 
			currentTime += h; 
		}
		return arr;
	}


	// TODO
	private double[][] inverseMatrix(double[][] jacobianMatrix) {
		
		return null;
	}
	
	
	// TODO
	private double[] matrixMultiplication(double[][] jacobianMatrix, double[] functionsMatrix) {
		
		return null;
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
		
		// current state
		Rate currentRate = (Rate) f.call(t, y);	
		Vector3d currentPosition[] = ((State)y).getPosition();
		Vector3d currentVelocity[]= ((State)y).getVelocity();
		boolean[] isShip = ((State)y).getisShip();
		
		// previous state
		State previousState = (State)y.addMul(t-h, currentRate);
		Vector3d previousPosition[] = ((State)y).getPosition();
		Vector3d previousVelocity[]= ((State)y).getVelocity();
		
		// next state
		State nextState = (State)y.addMul(t+h, currentRate);
		Vector3d nextPosition[] = ((State)y).getPosition();
		Vector3d nextVelocity[]= ((State)y).getVelocity();
		
		// for all planets (first dimensions)
		double[][] functionsMatrix = new double[currentPosition.length][6]; // [positionX, positionY, positionZ, velocityX, velocityY, velocityZ] - only on the current state --> 1 column
		double[][][] jacobianMatrix = new double[currentPosition.length][6][6]; 
		
		// fill in functionsMatrix
		for (int i=0; i<currentPosition.length; i++) {
			
				functionsMatrix[i][0] = currentPosition[i].getX();
				functionsMatrix[i][1] = currentPosition[i].getY();
				functionsMatrix[i][2] = currentPosition[i].getZ();
				functionsMatrix[i][3] = currentVelocity[i].getX();
				functionsMatrix[i][4] = currentVelocity[i].getY();
				functionsMatrix[i][5] = currentVelocity[i].getZ();
		}
		
		// fill in jacobianMatrix
		for (int i=0; i<currentPosition.length; i++) { // = number of planets
			for (int j=0; j<jacobianMatrix[i].length; j++) { // = 6 (rows)
				for (int k=0; k<jacobianMatrix[i][j].length; k++) { // = 6 (columns)
					
					State previousY = new State(currentPosition, currentVelocity, isShip, t);
					State nextY = new State(currentPosition, currentVelocity, isShip, t);
					
					boolean isPrevious = true;
					boolean isNext = true;
					
					if (isPrevious) {
						
						isNext = false;
						
						if (k==0) {
							
							currentPosition[i].setX(currentPosition[i].getX() - h);
						}
						else if (k==1) {
							
							currentPosition[i].setY(currentPosition[i].getY() - h);
						}
						else if (k==2) {
							
							currentPosition[i].setZ(currentPosition[i].getZ() - h);
						}
						// switch to velocities 
						else if (k==3) {
							
							currentVelocity[i].setX(currentVelocity[i].getX() - h);
						}
						else if (k==4) {
							
							currentVelocity[i].setY(currentVelocity[i].getY() - h);
						}
						else if (k==5) {
							
							currentVelocity[i].setZ(currentVelocity[i].getZ() - h);
						}
					}
					
					if (isNext) {
						
						isPrevious = false;
						
						if (k==0) {
							
							currentPosition[i].setX(currentPosition[i].getX() + h);
						}
						else if (k==1) {
							
							currentPosition[i].setY(currentPosition[i].getY() + h);
						}
						else if (k==2) {
							
							currentPosition[i].setZ(currentPosition[i].getZ() + h);
						}
						// switch to velocities 
						else if (k==3) {
							
							currentVelocity[i].setX(currentVelocity[i].getX() + h);
						}
						else if (k==4) {
							
							currentVelocity[i].setY(currentVelocity[i].getY() + h);
						}
						else if (k==5) {
							
							currentVelocity[i].setZ(currentVelocity[i].getZ() + h);
						}
					}
					
					Rate previousRate = (Rate) f.call(t, previousY);
					Rate nextRate = (Rate) f.call(t, nextY);
					
					
					double derivative;
					//derivatives calculations  
					if (j==0) { 
						
						derivative = (nextRate.getVelocity()[i].getX() - previousRate.getVelocity()[i].getX()) / 2*h;  				
					}
					else if (j==1) {
						
						derivative = (nextRate.getVelocity()[i].getY() - previousRate.getVelocity()[i].getY()) / 2*h;  				
					}
					else if (j==2) {
						
						derivative = (nextRate.getVelocity()[i].getZ() - previousRate.getVelocity()[i].getZ()) / 2*h;  				
					}
					// switch to velocities
					else if (j==3) {
						
						derivative = (nextRate.getAcceleration()[i].getX() - previousRate.getAcceleration()[i].getX()) / 2*h;  				
					}
					else if (j==4) {
						
						derivative = (nextRate.getAcceleration()[i].getY() - previousRate.getAcceleration()[i].getY()) / 2*h;  				
					}
					else {
						
						derivative = (nextRate.getAcceleration()[i].getZ() - previousRate.getAcceleration()[i].getZ()) / 2*h;  				
					}

					jacobianMatrix[i][j][k] = derivative;
				}		
			}
		}
		
		/* TODO
		 * 1) inverse jacobianMatrix
		 * 2) multiply both inverted jacobianMatrix with functionsMatrix
		 * 3) calculate the corresponding new Rate
		 * 4) return y.addMul(1, -Rate);
		*/
		
		return null; 
	}
}