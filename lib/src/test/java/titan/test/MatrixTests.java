package titan.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Arrays;

import titan.impl.ODESolverNewtonRaphson;

public class MatrixTests {
	
	private static final double ACCURACY = 1E-6;
	private static final boolean PRINT = false;
	
	@Test
	public void testInverseMatrixOnRandomMatrix() {
		
		double[][] A = { { 9,5,2 }, {4,3,6}, {5,10,16} };
		double[][] inverse = ODESolverNewtonRaphson.inverseMatrix(A);
		double[][] inverseMatlab = { {0.05263157894736846, 0.26315789473684204, -0.10526315789473685},
									 {0.14912280701754377, -0.5877192982456139, 0.2017543859649123},
									 {-0.10964912280701754, 0.2850877192982456, -0.030701754385964914} };
		
		if(PRINT) {
			System.out.println("Calculated inverse:");
			System.out.println(Arrays.deepToString(inverse));
		}
		
		for(int i = 0; i < inverse.length; i++) {
			for (int j = 0; j < inverse[0].length; j++) {
				assertEquals(inverseMatlab[i][j], inverse[i][j], ACCURACY);
			}
		}
	}
	
	
	@Test
	public void testInverseMatrixOnIdentityMatrix() {
		
		double[][] identityMatrix = new double[6][6];
		
		for (int i = 0; i < identityMatrix.length; i++) {
			identityMatrix[i][i] = 1;
		}
		
		double[][] inversedIdentityMatrix = ODESolverNewtonRaphson.inverseMatrix(identityMatrix);
		
		if(PRINT) {
			System.out.println("Calculated inverse:");
			System.out.println(Arrays.deepToString(inversedIdentityMatrix));
		}
		
		for (int i=0; i<identityMatrix.length; i++) {
			for (int j=0; j<identityMatrix[0].length; j++) {
				assertEquals(identityMatrix[i][j], inversedIdentityMatrix[i][j], ACCURACY);
			}
		}
	}

	
	@Test
	public void testMultiplicationRandomMatrixOnRandomVector() {
		
		double[][] A = { {1, 18}, {2, 7} };
		double[] v = { 5, 6 };
		
		double[] multiplicationAv = ODESolverNewtonRaphson.multipliesMatrices(A, v);
		double[] resultMatlab = { 113, 52 };
		
		if(PRINT) {
			System.out.println("Calculated multiplication:");
			System.out.println(Arrays.toString(multiplicationAv));
		}
		
		for (int i = 0; i < resultMatlab.length; i++) {
			
			assertEquals(resultMatlab[i], multiplicationAv[i], ACCURACY);
		}
	}
	
	
	@Test
	public void testMultiplicationIdentityMatrixOnOnesVector() {
		
		double[][] identityMatrix = new double[6][6];
		double[] onesVector = new double[6];
		
		for (int i = 0; i < identityMatrix.length; i++) {
			identityMatrix[i][i] = 1;
		}
		
		for (int i = 0; i < onesVector.length; i++) {
			onesVector[i] = 1;
		}
		
		double[] multiplication = ODESolverNewtonRaphson.multipliesMatrices(identityMatrix, onesVector);
		
		if(PRINT) {
			System.out.println("Calculated multiplication:");
			System.out.println(Arrays.toString(multiplication));
		}
		
		for (int i=0; i < multiplication.length; i++) {
			assertEquals(1, multiplication[i], ACCURACY);
		}
	}
}
