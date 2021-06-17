package titan.impl;

import java.util.Arrays;

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

		while (currentTime < tf) {
			
			arr[i+1] = step(f, currentTime, arr[i], h); // calculate the next step
			((State) arr[i+1]).setTime(currentTime+h); // set time for the next step
			i += 1; 
			currentTime += h; 
		}
		return arr;
	}


	/**
	 * This is a method for inverting a 3 by 3 matrix that has all entries filled (i.e. no 0 element)
	 * @param jacobianMatrix: matrix to be inverted
	 * @return inverted matrix: inverse of the jacobianMatrix
	 */
	public double[][] inverseMatrix3by3(double[][] jacobianMatrix_tmp) {

		double[][] inverse = new double[jacobianMatrix_tmp.length][jacobianMatrix_tmp[0].length];
		
		//fill the identity matrix
		for (int i = 0; i < jacobianMatrix_tmp.length; i++) {
			
			inverse[i][i] = 1;
		}
        
		//Get the first row to start with 1, ..., ...
		//[1,x,y;...;...]
		double firstRowfirstColumnFactor = 1/(jacobianMatrix_tmp[0][0]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
			
			jacobianMatrix_tmp[0][i] = firstRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[0][i] = firstRowfirstColumnFactor*inverse[0][i];
		}
		
		//Get zero in the first column and second row
		//[1,x,y;0,a,b;e,c,d]
		double secondRowfirstColumnFactor = -1*(jacobianMatrix_tmp[1][0]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
			
			jacobianMatrix_tmp[1][i] = jacobianMatrix_tmp[1][i] + secondRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[1][i] = inverse[1][i] + secondRowfirstColumnFactor*inverse[0][i];
		}
		
        //Get zero in the first column and second&third row
		//[1,x,y;0,a,b;0,c,d]
        double thirdRowfirstColumnFactor = -1*(jacobianMatrix_tmp[2][0]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
			
            jacobianMatrix_tmp[2][i] = jacobianMatrix_tmp[2][i] + thirdRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[2][i] = inverse[2][i] + thirdRowfirstColumnFactor*inverse[0][i];
		}
        
		//Get the second row to start with 0, 1, ...
		////[1,x,y;0,1,b;0,c,d]
		double secondRowsecondColumnFactor = 1/(jacobianMatrix_tmp[1][1]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
			
			jacobianMatrix_tmp[1][i] = secondRowsecondColumnFactor*jacobianMatrix_tmp[1][i];
			inverse[1][i] = secondRowsecondColumnFactor*inverse[1][i];
		}
		
		//Get the third row to start with 0, 1, ...
		//[1,x,y;0,1,b;0,1,d]
		double thirdRowsecondColumnFactor = 1/(jacobianMatrix_tmp[2][1]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
			
			jacobianMatrix_tmp[2][i] = thirdRowsecondColumnFactor*jacobianMatrix_tmp[2][i];
			inverse[2][i] = thirdRowsecondColumnFactor*inverse[2][i];
		}
        
		//Get 1 in the second column and third row
		//[1,x,y;0,1,b;0,0,d]
		double thirdRowsecondColumnFactorSubstraction = -1*(jacobianMatrix_tmp[2][1]); //this should be -1 ;)
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
			
            jacobianMatrix_tmp[2][i] = jacobianMatrix_tmp[2][i] + thirdRowsecondColumnFactorSubstraction*jacobianMatrix_tmp[1][i];
			inverse[2][i] = inverse[2][i] + thirdRowsecondColumnFactorSubstraction*inverse[1][i];
		}
        
		//Get 1 in the third row, third column
		//[1,x,y;0,1,b;0,0,1]
		double thirdRowthirdColumnFactor = 1/(jacobianMatrix_tmp[2][2]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
			
			jacobianMatrix_tmp[2][i] = thirdRowthirdColumnFactor*jacobianMatrix_tmp[2][i];
			inverse[2][i] = thirdRowthirdColumnFactor*inverse[2][i];
		}
		
		//Get 0 in the second row and third column
		//[1,x,y;0,1,0;0,0,1]
		double secondRowThirdColumnFactorSubstraction = -1*(jacobianMatrix_tmp[1][2]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
			
            jacobianMatrix_tmp[1][i] = jacobianMatrix_tmp[1][i] + secondRowThirdColumnFactorSubstraction*jacobianMatrix_tmp[2][i];
			inverse[1][i] = inverse[1][i] + secondRowThirdColumnFactorSubstraction*inverse[2][i];
		}
		
		//Get 0 in the first row and third column
		//[1,x,0;0,1,0;0,0,1]
		double firstRowThirdColumnFactorSubstraction = -1*(jacobianMatrix_tmp[0][2]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
			
            jacobianMatrix_tmp[0][i] = jacobianMatrix_tmp[0][i] + firstRowThirdColumnFactorSubstraction*jacobianMatrix_tmp[2][i];
			inverse[0][i] = inverse[0][i] + firstRowThirdColumnFactorSubstraction*inverse[2][i];
		}
		
		//Get 0 in the first row and second column
		//[1,0,0;0,1,0;0,0,1]
		double firstRowSecondColumnFactorSubstraction = -1*(jacobianMatrix_tmp[0][1]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
			
            jacobianMatrix_tmp[0][i] = jacobianMatrix_tmp[0][i] + firstRowSecondColumnFactorSubstraction*jacobianMatrix_tmp[1][i];
			inverse[0][i] = inverse[0][i] + firstRowSecondColumnFactorSubstraction*inverse[1][i];
		}
		return inverse;
	}
	
	//TODO use this method together with the method of the 2D array to compute the inverse
	private double inverseMatrix3D(double[][][] jacobianMatrix_tmp) {
		return 0;
	}
	
	/**
	 * Method to compute the multiplication of a matrix by a vector
	 * @param jacobianMatrix: a 2d matrix
	 * @param functionsMatrix: a 1d matrix (also know as a vector)
	 * @return: the result of the multiplication of jacobianMatrix*functionsMatrix
	 */
	public double[] matrixMultiplication(double[][] jacobianMatrix, double[] functionsMatrix) {
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
		double[][][] jacobianMatrix_tmp = new double[currentPosition.length][6][6]; // non-inverted
		double[][][] jacobianMatrix = null; // inverted
		
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
			for (int j=0; j<jacobianMatrix_tmp[i].length; j++) { 
				for (int k=0; k<jacobianMatrix_tmp[i][j].length; k++) { 
					
					State previousY = new State(currentPosition, currentVelocity, isShip, t); // initialize a variable previous state
					State nextY = new State(currentPosition, currentVelocity, isShip, t); // initialize a variable following state
					/*
					 * flags
					 */
					boolean isPrevious = true; 
					boolean isNext = true;
					if (isPrevious) {
						
						isNext = false;
						
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
					}
					if (isNext) {
						
						isPrevious = false;
						
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
					}
					Rate previousRate = (Rate) f.call(t, previousY); // correspond to the functions: f1 ... fn=f6 of the previous state
					Rate nextRate = (Rate) f.call(t, nextY); // correspond to the functions: f1 ... fn=f6 of the following state
					
					
					// lets calculate the deltas
					double derivative;
					// derivatives of position vector coordinates: delta position = (nextSate velocity - previousState velocity) / 2h
					if (j==0) { 
						
						derivative = (nextRate.getVelocity()[i].getX() - previousRate.getVelocity()[i].getX()) / 2*h;  				
					}
					else if (j==1) {
						
						derivative = (nextRate.getVelocity()[i].getY() - previousRate.getVelocity()[i].getY()) / 2*h;  				
					}
					else if (j==2) {
						
						derivative = (nextRate.getVelocity()[i].getZ() - previousRate.getVelocity()[i].getZ()) / 2*h;  				
					}
					// derivatives of velocity vector coordinates: delta velocity = (nextSate acceleration - previousState acceleration) / 2h
					else if (j==3) {
						
						derivative = (nextRate.getAcceleration()[i].getX() - previousRate.getAcceleration()[i].getX()) / 2*h;  				
					}
					else if (j==4) {
						
						derivative = (nextRate.getAcceleration()[i].getY() - previousRate.getAcceleration()[i].getY()) / 2*h;  				
					}
					else {
						
						derivative = (nextRate.getAcceleration()[i].getZ() - previousRate.getAcceleration()[i].getZ()) / 2*h;  				
					}

					jacobianMatrix_tmp[i][j][k] = derivative;
					jacobianMatrix[i][j][k] = inverseMatrix3D(jacobianMatrix_tmp);
				}		
			}
		}
		
		//Xk+1 = Xk - (J^-1 * F(Xk)) <=> nextState = currentState - (invertedJacobianMatrix * functionsMatrix)
		
		/* TODO
		 * 1) inverse jacobianMatrix 6x6 --> jacobianMatrix[i][6][6]
		 * 2) multiply both inverted jacobianMatrix with functionsMatrix (6x6 * 6x1)
		 * 
		 * 3) calculate the corresponding new Rate
		 * 4) return y.addMul(1, -newRate);  
		*/
		
		return null; 
	}
}