package titan.impl;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

import org.apache.commons.math3.linear.MatrixUtils;

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

		while (currentTime < tf) {
			
			arr[i+1] = step(f, currentTime, arr[i], h); // calculate the next step
			((State) arr[i+1]).setTime(currentTime+h); // set time for the next step
			i += 1; 
			currentTime += h; 
		}
		return arr;
	}

	
	/**
	 * https://commons.apache.org/proper/commons-math/javadocs/api-3.5/org/apache/commons/math3/linear/RealMatrix.html
	 * @param jacobianMatrix: matrix to be inverted
	 * @return inverted matrix: inverse of the jacobianMatrix
	 */
	public static double[][] inverseMatrix(double[][] jacobianMatrix) {
		
		return MatrixUtils.inverse(MatrixUtils.createRealMatrix(jacobianMatrix)).getData();
	}
	
	
	/**
	 * Multiplies a matrix with a vector
	 * @param jacobianMatrix: 2D matrix
	 * @param functionsMatrix: 1D matrix (vector)
	 * @return result of the multiplication of: jacobianMatrix*functionsMatrix
	 */
	public static double[] multipliesMatrices(double[][] jacobianMatrix, double[] functionsMatrix) {
		double[] result = new double [functionsMatrix.length];
		if(functionsMatrix.length != jacobianMatrix[0].length) {
			throw new RuntimeException("Matrix dimensions don't match.");
		}
		else {
			for(int i = 0; i < jacobianMatrix.length; i++) {
				double element = 0;
				for(int j = 0; j < functionsMatrix.length; j++) {
					element += jacobianMatrix[i][j]*functionsMatrix[j];
				}
				result[i] = element;
			}
		}
		return result;
	}
	
	
	/*
	 * Update rule for one step: we apply the Multivariate Newton-Raphson Method: https://skill-lync.com/projects/week-6-multivariate-newton-rhapson-solver-18 
	 *
	 * @param   f   the function defining the differential equation dy/dt=f(t,y)
	 * @param   t   the time
	 * @param   y   the state
	 * @param   h   the step size
	 * @return  the new state after taking one step
	 */
	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
		
		// current state for all planets
		Rate currentRate = (Rate) f.call(t, y);	
		Vector3d currentPosition[] = ((State)y).getPosition();
		Vector3d currentVelocity[]= ((State)y).getVelocity();
		boolean[] isShip = ((State)y).getisShip();
		
		// previous state for all planets
		State previousState = (State)y.addMul(t-h, currentRate);
		Vector3d previousPosition[] = ((State)y).getPosition();
		Vector3d previousVelocity[]= ((State)y).getVelocity();
		
		// next state for all planets
		State nextState = (State)y.addMul(t+h, currentRate);
		Vector3d nextPosition[] = ((State)y).getPosition();
		Vector3d nextVelocity[]= ((State)y).getVelocity();
		
		
		/* functionsMatrix:
		 * 
		 * 	[ positionX ]
		 * 	| positionY |
		 * 	| positionZ	|	6x1 matrix of the current state only
		 *  | velocityX |
		 *  | velocityY |
		 *  [ velocityZ ]
		 *    
		 * jacobianMatrix:
		 * 
		 * [ (delta positionX / delta positionX) . . . (delta positionX / delta velocityZ) ]
		 * | (delta positionY / delta positionX) . . . (delta positionY / delta velocityZ) |
		 * | (delta positionZ / delta positionX) . . . (delta positionZ / delta velocityZ) |	6x6 matrix of the current state only
		 * | (delta velocityX / delta positionX) . . . (delta velocityX / delta velocityZ) |
		 * | (delta velocityY / delta positionX) . . . (delta velocityY / delta velocityZ) |
		 * [ (delta velocityZ / delta positionX) . . . (delta velocityZ / delta velocityZ) ]
		 */
		
		
		// for all planets (additional first dimensions)
		double[][] functionsMatrix = new double[currentPosition.length][6];  
		double[][][] jacobianMatrix = new double[currentPosition.length][6][6]; 
		double[][][] inversedJacobianMatrix = new double[currentPosition.length][6][6];
		Vector3d[] velocity = new Vector3d[currentPosition.length];
		Vector3d[] acceleration = new Vector3d[currentVelocity.length];
		
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
		for (int i=0; i<currentPosition.length; i++) { 
			for (int j=0; j<jacobianMatrix[i].length; j++) { 
				for (int k=0; k<jacobianMatrix[i][j].length; k++) { 
					
					currentPosition = ((State)y).getPosition(); // re-initialization 
					currentVelocity = ((State)y).getVelocity();
					// compute previous Y	
						// set position vector coordinates
						if (k==0) {
							
							currentPosition[i].setX(currentPosition[i].getX() - h);
						}
						else if (k==1) {
							
							currentPosition[i].setY(currentPosition[i].getY() - h);
						}
						else if (k==2) {
							
							currentPosition[i].setZ(currentPosition[i].getZ() - h);
						}
						// set velocity vector coordinates
						else if (k==3) {
							
							currentVelocity[i].setX(currentVelocity[i].getX() - h);
						}
						else if (k==4) {
							
							currentVelocity[i].setY(currentVelocity[i].getY() - h);
						}
						else if (k==5) {
							
							currentVelocity[i].setZ(currentVelocity[i].getZ() - h);
						}
						State previousY = new State(currentPosition, currentVelocity, isShip, t); // initialize a variable previous state
					
						
						currentPosition = ((State)y).getPosition(); // re-initialization (avoid -h+h --> current and not next)
						currentVelocity = ((State)y).getVelocity();
					// compute next Y
						// set position vector coordinates
						if (k==0) {
							
							currentPosition[i].setX(currentPosition[i].getX() + h);
						}
						else if (k==1) {
							
							currentPosition[i].setY(currentPosition[i].getY() + h);
						}
						else if (k==2) {
							
							currentPosition[i].setZ(currentPosition[i].getZ() + h);
						}
						// set velocity vector coordinates
						else if (k==3) {
							
							currentVelocity[i].setX(currentVelocity[i].getX() + h);
						}
						else if (k==4) {
							
							currentVelocity[i].setY(currentVelocity[i].getY() + h);
						}
						else if (k==5) {
							
							currentVelocity[i].setZ(currentVelocity[i].getZ() + h);
						}
					State nextY = new State(currentPosition, currentVelocity, isShip, t); // initialize a variable following state
					
					Rate previousRate = (Rate) f.call(t, previousY); // correspond to the functions: f1 ... fn=f6 of the previous state
					Rate nextRate = (Rate) f.call(t, nextY); // correspond to the functions: f1 ... fn=f6 of the following state
			
					
					// lets calculate the deltas
					double derivative;
					// derivatives of position vector coordinates: delta position = (nextSate velocity - previousState velocity) / 2h
					if (j==0) { 
						
						derivative = (nextRate.getVelocity()[i].getX() - previousRate.getVelocity()[i].getX()) / (2*h);  				
					}
					else if (j==1) {
						
						derivative = (nextRate.getVelocity()[i].getY() - previousRate.getVelocity()[i].getY()) / (2*h);  				
					}
					else if (j==2) {
						
						derivative = (nextRate.getVelocity()[i].getZ() - previousRate.getVelocity()[i].getZ()) / (2*h);  				
					}
					// derivatives of velocity vector coordinates: delta velocity = (nextSate acceleration - previousState acceleration) / 2h
					else if (j==3) {
						
						derivative = (nextRate.getAcceleration()[i].getX() - previousRate.getAcceleration()[i].getX()) / (2*h);  				
					}
					else if (j==4) {
						
						derivative = (nextRate.getAcceleration()[i].getY() - previousRate.getAcceleration()[i].getY()) / (2*h);  				
					}
					else {
						
						derivative = (nextRate.getAcceleration()[i].getZ() - previousRate.getAcceleration()[i].getZ()) / (2*h);  				
					}

					jacobianMatrix[i][j][k] = derivative;
				}
			}
			inversedJacobianMatrix[i] = inverseMatrix(jacobianMatrix[i]);
			double[] tmp = multipliesMatrices(inversedJacobianMatrix[i], functionsMatrix[i]);
			
			velocity[i] = new Vector3d(-tmp[0], -tmp[1], -tmp[2]);
			acceleration[i] = new Vector3d(-tmp[3], -tmp[4], -tmp[5]);
		}
		
		Rate newRate = new Rate(velocity, acceleration);
		
		//Xk+1 = Xk - (J^-1 * F(Xk)) <=> nextState = currentState - (invertedJacobianMatrix * functionsMatrix)
	
		 return y.addMul(1, newRate);
	}
}