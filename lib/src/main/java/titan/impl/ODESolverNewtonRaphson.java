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
	
	
	/**
	 * This is a method for inverting a 6 by 6 matrix that has all entries filled (i.e. no 0 element)
	 * @param jacobianMatrix: matrix to be inverted
	 * @return inverted matrix: inverse of the jacobianMatrix
	 */
	public double[][] inverseMatrix6by6(double[][] jacobianMatrixInput) {
		
		//Copy the input matrix so we do not change it
		double[][] jacobianMatrix_tmp = new double[jacobianMatrixInput.length][jacobianMatrixInput[0].length];
		for (int i = 0; i < jacobianMatrix_tmp.length; i++) {
			for (int j = 0; j < jacobianMatrix_tmp[0].length; j++) {
				jacobianMatrix_tmp[i][j] = jacobianMatrixInput[i][j];
			}
		}
		double[][] inverse = new double[jacobianMatrix_tmp.length][jacobianMatrix_tmp[0].length];
		
		//fill the identity matrix
		for (int i = 0; i < jacobianMatrix_tmp.length; i++) {
			inverse[i][i] = 1;
		}
		
		/*the 6x6 matrix looks like:
		[a1,b1,c1,d1,e1,f1];
		[a2,b2,c2,d2,e2,f2];
		[a3,b3,c3,d3,e3,f3];
		[a4,b4,c4,d4,e4,f4];
		[a5,b5,c5,d5,e5,f5];
		[a6,b6,c6,d6,e6,f6];
        */
		
		//Get the a1 to start with 1.
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[a2,b2,c2,d2,e2,f2];
		[a3,b3,c3,d3,e3,f3];
		[a4,b4,c4,d4,e4,f4];
		[a5,b5,c5,d5,e5,f5];
		[a6,b6,c6,d6,e6,f6];
        */
		double firstRowfirstColumnFactor = 1/(jacobianMatrix_tmp[0][0]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
			jacobianMatrix_tmp[0][i] = firstRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[0][i] = firstRowfirstColumnFactor*inverse[0][i];
		}
		
		//Get zero in the all entries of the first column (except a1)
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,b2,c2,d2,e2,f2];
		[0,b3,c3,d3,e3,f3];
		[0,b4,c4,d4,e4,f4];
		[0,b5,c5,d5,e5,f5];
		[0,b6,c6,d6,e6,f6];
        */
		double secondRowfirstColumnFactor = -1*(jacobianMatrix_tmp[1][0]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
			jacobianMatrix_tmp[1][i] = jacobianMatrix_tmp[1][i] + secondRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[1][i] = inverse[1][i] + secondRowfirstColumnFactor*inverse[0][i];
		}
        double thirdRowfirstColumnFactor = -1*(jacobianMatrix_tmp[2][0]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
            jacobianMatrix_tmp[2][i] = jacobianMatrix_tmp[2][i] + thirdRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[2][i] = inverse[2][i] + thirdRowfirstColumnFactor*inverse[0][i];
		}
        double fourthRowfirstColumnFactor = -1*(jacobianMatrix_tmp[3][0]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
            jacobianMatrix_tmp[3][i] = jacobianMatrix_tmp[3][i] + fourthRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[3][i] = inverse[3][i] + fourthRowfirstColumnFactor*inverse[0][i];
		}
        double fifthRowfirstColumnFactor = -1*(jacobianMatrix_tmp[4][0]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
            jacobianMatrix_tmp[4][i] = jacobianMatrix_tmp[4][i] + fifthRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[4][i] = inverse[4][i] + fifthRowfirstColumnFactor*inverse[0][i];
		}
        double sixthRowfirstColumnFactor = -1*(jacobianMatrix_tmp[5][0]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
            jacobianMatrix_tmp[5][i] = jacobianMatrix_tmp[5][i] + sixthRowfirstColumnFactor*jacobianMatrix_tmp[0][i];
			inverse[5][i] = inverse[5][i] + sixthRowfirstColumnFactor*inverse[0][i];
		}
        
		//Get the rows (except 1st) to start with 0, 1, ...
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,1,c3,d3,e3,f3];
		[0,1,c4,d4,e4,f4];
		[0,1,c5,d5,e5,f5];
		[0,1,c6,d6,e6,f6];
        */
		double secondRowsecondColumnFactor = 1/(jacobianMatrix_tmp[1][1]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
			jacobianMatrix_tmp[1][i] = secondRowsecondColumnFactor*jacobianMatrix_tmp[1][i];
			inverse[1][i] = secondRowsecondColumnFactor*inverse[1][i];
		}
		double thirdRowsecondColumnFactor = 1/(jacobianMatrix_tmp[2][1]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
			jacobianMatrix_tmp[2][i] = thirdRowsecondColumnFactor*jacobianMatrix_tmp[2][i];
			inverse[2][i] = thirdRowsecondColumnFactor*inverse[2][i];
		}
		double fourthRowSecondColumnFactor = 1/(jacobianMatrix_tmp[3][1]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
			jacobianMatrix_tmp[3][i] = fourthRowSecondColumnFactor*jacobianMatrix_tmp[3][i];
			inverse[3][i] = fourthRowSecondColumnFactor*inverse[3][i];
		}
		double fifthRowSecondColumnFactor = 1/(jacobianMatrix_tmp[4][1]);
		for (int i = 0; i < jacobianMatrix_tmp[4].length; i++) {
			jacobianMatrix_tmp[4][i] = fifthRowSecondColumnFactor*jacobianMatrix_tmp[4][i];
			inverse[4][i] = fifthRowSecondColumnFactor*inverse[4][i];
		}
		double sixthRowSecondColumnFactor = 1/(jacobianMatrix_tmp[5][1]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
			jacobianMatrix_tmp[5][i] = sixthRowSecondColumnFactor*jacobianMatrix_tmp[5][i];
			inverse[5][i] = sixthRowSecondColumnFactor*inverse[5][i];
		}
		
		
		//Get the columns from 2 onwards to start with 0
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,0,c3,d3,e3,f3];
		[0,0,c4,d4,e4,f4];
		[0,0,c5,d5,e5,f5];
		[0,0,c6,d6,e6,f6];
        */
		double thirdRowsecondColumnFactorSubstraction = -1*(jacobianMatrix_tmp[2][1]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
            jacobianMatrix_tmp[2][i] = jacobianMatrix_tmp[2][i] + thirdRowsecondColumnFactorSubstraction*jacobianMatrix_tmp[1][i];
			inverse[2][i] = inverse[2][i] + thirdRowsecondColumnFactorSubstraction*inverse[1][i];
		}
		double fourthRowsecondColumnFactorSubstraction = -1*(jacobianMatrix_tmp[3][1]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
            jacobianMatrix_tmp[3][i] = jacobianMatrix_tmp[3][i] + fourthRowsecondColumnFactorSubstraction*jacobianMatrix_tmp[1][i];
			inverse[3][i] = inverse[3][i] + fourthRowsecondColumnFactorSubstraction*inverse[1][i];
		}
		double fifthRowsecondColumnFactorSubstraction = -1*(jacobianMatrix_tmp[4][1]);
		for (int i = 0; i < jacobianMatrix_tmp[4].length; i++) {
            jacobianMatrix_tmp[4][i] = jacobianMatrix_tmp[4][i] + fifthRowsecondColumnFactorSubstraction*jacobianMatrix_tmp[1][i];
			inverse[4][i] = inverse[4][i] + fifthRowsecondColumnFactorSubstraction*inverse[1][i];
		}
		double sixthRowsecondColumnFactorSubstraction = -1*(jacobianMatrix_tmp[5][1]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
            jacobianMatrix_tmp[5][i] = jacobianMatrix_tmp[5][i] + sixthRowsecondColumnFactorSubstraction*jacobianMatrix_tmp[1][i];
			inverse[5][i] = inverse[5][i] + sixthRowsecondColumnFactorSubstraction*inverse[1][i];
		}
		
		
		//Get the rows (except 1st, 2nd) to start with 0, 0, 1, ...
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,0,1,d3,e3,f3];
		[0,0,1,d4,e4,f4];
		[0,0,1,d5,e5,f5];
		[0,0,1,d6,e6,f6];
		*/
		double thirdRowThirdColumnFactor = 1 / (jacobianMatrix_tmp[2][2]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
			jacobianMatrix_tmp[2][i] = thirdRowThirdColumnFactor * jacobianMatrix_tmp[2][i];
			inverse[2][i] = thirdRowsecondColumnFactor * inverse[2][i];
		}
		double fourthRowThirdColumnFactor = 1 / (jacobianMatrix_tmp[3][2]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
			jacobianMatrix_tmp[3][i] = fourthRowThirdColumnFactor * jacobianMatrix_tmp[3][i];
			inverse[3][i] = fourthRowThirdColumnFactor * inverse[3][i];
		}
		double fifthRowThirdColumnFactor = 1 / (jacobianMatrix_tmp[4][2]);
		for (int i = 0; i < jacobianMatrix_tmp[4].length; i++) {
			jacobianMatrix_tmp[4][i] = fifthRowThirdColumnFactor * jacobianMatrix_tmp[4][i];
			inverse[4][i] = fifthRowThirdColumnFactor * inverse[4][i];
		}
		double sixthRowThirdColumnFactor = 1 / (jacobianMatrix_tmp[5][2]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
			jacobianMatrix_tmp[5][i] = sixthRowThirdColumnFactor * jacobianMatrix_tmp[5][i];
			inverse[5][i] = sixthRowThirdColumnFactor * inverse[5][i];
		}
		
		
		//Get the columns from 3 onwards to start with 0
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,0,1,d3,e3,f3];
		[0,0,0,d4,e4,f4];
		[0,0,0,d5,e5,f5];
		[0,0,0,d6,e6,f6];
		*/
		double fourthRowThirdColumnFactorSubstraction = -1*(jacobianMatrix_tmp[3][2]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
            jacobianMatrix_tmp[3][i] = jacobianMatrix_tmp[2][i] + fourthRowThirdColumnFactorSubstraction*jacobianMatrix_tmp[2][i];
			inverse[3][i] = inverse[3][i] + fourthRowThirdColumnFactorSubstraction*inverse[2][i];
		}
		double fifthRowThirdColumnFactorSubstraction = -1*(jacobianMatrix_tmp[4][2]);
		for (int i = 0; i < jacobianMatrix_tmp[4].length; i++) {
            jacobianMatrix_tmp[4][i] = jacobianMatrix_tmp[4][i] + fifthRowThirdColumnFactorSubstraction*jacobianMatrix_tmp[2][i];
			inverse[4][i] = inverse[4][i] + fifthRowThirdColumnFactorSubstraction*inverse[2][i];
		}
		double sixthRowThirdColumnFactorSubstraction = -1*(jacobianMatrix_tmp[5][2]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
            jacobianMatrix_tmp[5][i] = jacobianMatrix_tmp[5][i] + sixthRowThirdColumnFactorSubstraction*jacobianMatrix_tmp[2][i];
			inverse[5][i] = inverse[5][i] + sixthRowThirdColumnFactorSubstraction*inverse[2][i];
		}
		
		
		//Get the rows (except 1-3) to start with 0, 0, 0, 1, ...
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,0,1,d3,e3,f3];
		[0,0,0,1,e4,f4];
		[0,0,0,1,e5,f5];
		[0,0,0,1,e6,f6];
		*/
		double fourthRowFourthColumnFactor = 1 / (jacobianMatrix_tmp[3][3]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
			jacobianMatrix_tmp[3][i] = fourthRowFourthColumnFactor * jacobianMatrix_tmp[3][i];
			inverse[3][i] = fourthRowFourthColumnFactor * inverse[3][i];
		}
		double fifthRowFourthColumnFactor = 1 / (jacobianMatrix_tmp[4][3]);
		for (int i = 0; i < jacobianMatrix_tmp[4].length; i++) {
			jacobianMatrix_tmp[4][i] = fifthRowFourthColumnFactor * jacobianMatrix_tmp[4][i];
			inverse[4][i] = fifthRowFourthColumnFactor * inverse[4][i];
		}
		double sixthRowFourthColumnFactor = 1 / (jacobianMatrix_tmp[5][3]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
			jacobianMatrix_tmp[5][i] = sixthRowFourthColumnFactor * jacobianMatrix_tmp[5][i];
			inverse[5][i] = sixthRowFourthColumnFactor * inverse[5][i];
		}
		
		
		//Get the columns from 4 onwards to start with 0
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,0,1,d3,e3,f3];
		[0,0,0,1,e4,f4];
		[0,0,0,0,e5,f5];
		[0,0,0,0,e6,f6];
		*/
		double fifthRowFourthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[4][3]);
		for (int i = 0; i < jacobianMatrix_tmp[4].length; i++) {
            jacobianMatrix_tmp[4][i] = jacobianMatrix_tmp[4][i] + fifthRowFourthColumnFactorSubstraction*jacobianMatrix_tmp[3][i];
			inverse[4][i] = inverse[4][i] + fifthRowFourthColumnFactorSubstraction*inverse[3][i];
		}
		double sixthRowFourthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[5][3]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
            jacobianMatrix_tmp[5][i] = jacobianMatrix_tmp[5][i] + sixthRowFourthColumnFactorSubstraction*jacobianMatrix_tmp[3][i];
			inverse[5][i] = inverse[5][i] + sixthRowFourthColumnFactorSubstraction*inverse[3][i];
		}
		
		
		//Get the rows (except 1-4) to start with 0, 0, 0, 0,1, ...
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,0,1,d3,e3,f3];
		[0,0,0,1,e4,f4];
		[0,0,0,0,1,f5];
		[0,0,0,0,1,f6];
		*/
		double fifthRowfifthColumnFactor = 1 / (jacobianMatrix_tmp[4][4]);
		for (int i = 0; i < jacobianMatrix_tmp[4].length; i++) {
			jacobianMatrix_tmp[4][i] = fifthRowfifthColumnFactor * jacobianMatrix_tmp[4][i];
			inverse[4][i] = fifthRowfifthColumnFactor * inverse[4][i];
		}
		double sixthRowFifthColumnFactor = 1 / (jacobianMatrix_tmp[5][4]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
			jacobianMatrix_tmp[5][i] = sixthRowFifthColumnFactor * jacobianMatrix_tmp[5][i];
			inverse[5][i] = sixthRowFifthColumnFactor * inverse[5][i];
		}
		
		//Get the columns from 5 onwards to start with 0
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,0,1,d3,e3,f3];
		[0,0,0,1,e4,f4];
		[0,0,0,0,1,f5];
		[0,0,0,0,0,f6];
		*/
		double sixthRowFifthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[5][4]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
            jacobianMatrix_tmp[5][i] = jacobianMatrix_tmp[5][i] + sixthRowFifthColumnFactorSubstraction*jacobianMatrix_tmp[4][i];
			inverse[5][i] = inverse[5][i] + sixthRowFifthColumnFactorSubstraction*inverse[4][i];
		}
		
		//Get the rows (except 1-5) to start with 0, 0, 0, 0,1, ...
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,f1];
		[0,1,c2,d2,e2,f2];
		[0,0,1,d3,e3,f3];
		[0,0,0,1,e4,f4];
		[0,0,0,0,1,f5];
		[0,0,0,0,0,1];
		*/
		double sixthRowSixthColumnFactor = 1 / (jacobianMatrix_tmp[5][5]);
		for (int i = 0; i < jacobianMatrix_tmp[5].length; i++) {
			jacobianMatrix_tmp[5][i] = sixthRowSixthColumnFactor * jacobianMatrix_tmp[5][i];
			inverse[5][i] = sixthRowSixthColumnFactor * inverse[5][i];
		}
		
		
		//Get 0 in rows 1-5 at column 6
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,e1,0];
		[0,1,c2,d2,e2,0];
		[0,0,1,d3,e3,0];
		[0,0,0,1,e4,0];
		[0,0,0,0,1,0];
		[0,0,0,0,0,1];
		*/
		double fifthRowSixthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[4][5]);
		for (int i = 0; i < jacobianMatrix_tmp[4].length; i++) {
            jacobianMatrix_tmp[4][i] = jacobianMatrix_tmp[4][i] + fifthRowSixthColumnFactorSubstraction*jacobianMatrix_tmp[5][i];
			inverse[4][i] = inverse[4][i] + fifthRowSixthColumnFactorSubstraction*inverse[5][i];
		}
		double fourthRowSixthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[3][5]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
            jacobianMatrix_tmp[3][i] = jacobianMatrix_tmp[3][i] + fourthRowSixthColumnFactorSubstraction*jacobianMatrix_tmp[5][i];
			inverse[3][i] = inverse[3][i] + fourthRowSixthColumnFactorSubstraction*inverse[5][i];
		}
		double thirdRowSixthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[2][5]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
            jacobianMatrix_tmp[2][i] = jacobianMatrix_tmp[2][i] + thirdRowSixthColumnFactorSubstraction*jacobianMatrix_tmp[5][i];
			inverse[2][i] = inverse[2][i] + thirdRowSixthColumnFactorSubstraction*inverse[5][i];
		}
		double secondRowSixthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[1][5]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
            jacobianMatrix_tmp[1][i] = jacobianMatrix_tmp[1][i] + secondRowSixthColumnFactorSubstraction*jacobianMatrix_tmp[5][i];
			inverse[1][i] = inverse[1][i] + secondRowSixthColumnFactorSubstraction*inverse[5][i];
		}
		double firstRowSixthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[0][5]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
            jacobianMatrix_tmp[0][i] = jacobianMatrix_tmp[0][i] + firstRowSixthColumnFactorSubstraction*jacobianMatrix_tmp[5][i];
			inverse[0][i] = inverse[0][i] + firstRowSixthColumnFactorSubstraction*inverse[5][i];
		}

		
		//Get 0 in rows 1-4 at column 5
		/*the 6x6 matrix looks like:
		[1,b1,c1,d1,0,0];
		[0,1,c2,d2,0,0];
		[0,0,1,d3,0,0];
		[0,0,0,1,0,0];
		[0,0,0,0,1,0];
		[0,0,0,0,0,1];
		*/
		double fourthRowFifthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[3][4]);
		for (int i = 0; i < jacobianMatrix_tmp[3].length; i++) {
            jacobianMatrix_tmp[3][i] = jacobianMatrix_tmp[3][i] + fourthRowFifthColumnFactorSubstraction*jacobianMatrix_tmp[4][i];
			inverse[3][i] = inverse[3][i] + fourthRowFifthColumnFactorSubstraction*inverse[4][i];
		}
		double thirdRowFifthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[2][4]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
            jacobianMatrix_tmp[2][i] = jacobianMatrix_tmp[2][i] + thirdRowFifthColumnFactorSubstraction*jacobianMatrix_tmp[4][i];
			inverse[2][i] = inverse[2][i] + thirdRowFifthColumnFactorSubstraction*inverse[4][i];
		}
		double secondRowFifthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[1][4]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
            jacobianMatrix_tmp[1][i] = jacobianMatrix_tmp[1][i] + secondRowFifthColumnFactorSubstraction*jacobianMatrix_tmp[4][i];
			inverse[1][i] = inverse[1][i] + secondRowFifthColumnFactorSubstraction*inverse[4][i];
		}
		double firstRowFifthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[0][4]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
            jacobianMatrix_tmp[0][i] = jacobianMatrix_tmp[0][i] + firstRowFifthColumnFactorSubstraction*jacobianMatrix_tmp[4][i];
			inverse[0][i] = inverse[0][i] + firstRowFifthColumnFactorSubstraction*inverse[4][i];
		}
		
		
		//Get 0 in rows 1-3 at column 4
		/*the 6x6 matrix looks like:
		[1,b1,c1,0,0,0];
		[0,1,c2,0,0,0];
		[0,0,1,0,0,0];
		[0,0,0,1,0,0];
		[0,0,0,0,1,0];
		[0,0,0,0,0,1];
		*/
		double thirdRowFourthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[2][3]);
		for (int i = 0; i < jacobianMatrix_tmp[2].length; i++) {
            jacobianMatrix_tmp[2][i] = jacobianMatrix_tmp[2][i] + thirdRowFourthColumnFactorSubstraction*jacobianMatrix_tmp[3][i];
			inverse[2][i] = inverse[2][i] + thirdRowFourthColumnFactorSubstraction*inverse[3][i];
		}
		double secondRowFourthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[1][3]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
            jacobianMatrix_tmp[1][i] = jacobianMatrix_tmp[1][i] + secondRowFourthColumnFactorSubstraction*jacobianMatrix_tmp[3][i];
			inverse[1][i] = inverse[1][i] + secondRowFourthColumnFactorSubstraction*inverse[3][i];
		}
		double firstRowFourthColumnFactorSubstraction = -1*(jacobianMatrix_tmp[0][3]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
            jacobianMatrix_tmp[0][i] = jacobianMatrix_tmp[0][i] + firstRowFourthColumnFactorSubstraction*jacobianMatrix_tmp[3][i];
			inverse[0][i] = inverse[0][i] + firstRowFourthColumnFactorSubstraction*inverse[3][i];
		}
		
		
		//Get 0 in rows 1-2 at column 3
		/*the 6x6 matrix looks like:
		[1,b1,0,0,0,0];
		[0,1,0,0,0,0];
		[0,0,1,0,0,0];
		[0,0,0,1,0,0];
		[0,0,0,0,1,0];
		[0,0,0,0,0,1];
		*/
		double secondRowThirdColumnFactorSubstraction = -1*(jacobianMatrix_tmp[1][2]);
		for (int i = 0; i < jacobianMatrix_tmp[1].length; i++) {
            jacobianMatrix_tmp[1][i] = jacobianMatrix_tmp[1][i] + secondRowThirdColumnFactorSubstraction*jacobianMatrix_tmp[2][i];
			inverse[1][i] = inverse[1][i] + secondRowThirdColumnFactorSubstraction*inverse[2][i];
		}
		double firstRowThirdColumnFactorSubstraction = -1*(jacobianMatrix_tmp[0][2]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
            jacobianMatrix_tmp[0][i] = jacobianMatrix_tmp[0][i] + firstRowThirdColumnFactorSubstraction*jacobianMatrix_tmp[2][i];
			inverse[0][i] = inverse[0][i] + firstRowThirdColumnFactorSubstraction*inverse[2][i];
		}
		
		
		//Get 0 in row 1 at column 2
		/*the 6x6 matrix looks like:
		[1,0,0,0,0,0];
		[0,1,0,0,0,0];
		[0,0,1,0,0,0];
		[0,0,0,1,0,0];
		[0,0,0,0,1,0];
		[0,0,0,0,0,1];
		*/
		double firstRowSecondColumnFactorSubstraction = -1*(jacobianMatrix_tmp[0][1]);
		for (int i = 0; i < jacobianMatrix_tmp[0].length; i++) {
            jacobianMatrix_tmp[0][i] = jacobianMatrix_tmp[0][i] + firstRowSecondColumnFactorSubstraction*jacobianMatrix_tmp[1][i];
			inverse[0][i] = inverse[0][i] + firstRowSecondColumnFactorSubstraction*inverse[1][i];
		}
		
		
		//IDENTITY MATRIX --> inverse is the inverse matrix of the input
		return inverse;
	}
	
	//TODO use this method together with the method of the 2D array to compute the inverse
	private double[][][] inverseMatrix3D(double[][][] jacobianMatrix_tmp) {
		
		double[][][] inversedMatrix = new double[jacobianMatrix_tmp.length][6][6];
		
		for (int i = 0; i < jacobianMatrix_tmp.length; i++) {
			
			//Get the 2D matrix (6 by 6)
			double[][] passingArray = new double[jacobianMatrix_tmp[i].length][jacobianMatrix_tmp[0][i].length];
			for (int j = 0; j < jacobianMatrix_tmp[i].length; j++) {
				for (int j2 = 0; j2 < jacobianMatrix_tmp[i][j].length; j2++) {
					passingArray[j][j2] = jacobianMatrix_tmp[i][j][j2];
				}
			}
			//Inverse the 2D matrix
			double[][] invertedPassing = inverseMatrix6by6(passingArray);
			
			//Put the inverted 2D matrix back
			for (int j = 0; j < 6; j++) {
				for (int j2 = 0; j2 < 6; j2++) {
					//passingArray[j][j2] = jacobianMatrix_tmp[i][j][j2];
					inversedMatrix[i][j][j2] = invertedPassing[j][j2];
				}
			}
		}
		
		return inversedMatrix;
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
					//jacobianMatrix[i][j][k] = inverseMatrix3D(jacobianMatrix_tmp);
				}
			}
		}
		jacobianMatrix = inverseMatrix3D(jacobianMatrix_tmp);
		
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