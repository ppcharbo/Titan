package titan.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import titan.impl.ODESolverNewtonRaphson;

public class MatrixTests {
	
	public final double ACCURACY = 1E-3;
	
	@Test
	public void testInverseMatrixOnIdentityMatrix() {
		
		double[][] identityMatrix = new double[6][6];
		
		for (int i=0; i<identityMatrix.length; i++) {
			for (int j=0; j<identityMatrix[0].length; j++) {
				
				if (i==j) {
					identityMatrix[i][j] = 1;
				}
				else {
					identityMatrix[i][j] = 0;
				}
			}
		}
		double[][] inversedIdentityMatrix = ODESolverNewtonRaphson.inverseMatrix(identityMatrix);
		
		for (int i=0; i<identityMatrix.length; i++) {
			for (int j=0; j<identityMatrix[0].length; j++) {
				
				System.out.println(inversedIdentityMatrix[i][j]);
				assertEquals(identityMatrix[i][j], inversedIdentityMatrix[i][j], ACCURACY);
			}
		}
	}

	
	@Test
	public void testMultipliesMatricesOnRandomMatrix() {
		
		double[][] A = { {1, 18}, {2, 7} };
		double[] v = { 5, 6 };
		
		double[] multiplicationAv = ODESolverNewtonRaphson.multipliesMatrices(A, v);
		double[] resultMatlab = { 113, 52 };
		
		for (int i = 0; i < resultMatlab.length; i++) {
			
			assertEquals(resultMatlab[i], multiplicationAv[i], ACCURACY);
		}
	}
	
	
	@Test
	public void testMultipliesMatricesOnIdentityMatrix() {
		
		double[][] identityMatrix1 = new double[6][6];
		double[] identityMatrix2 = new double[6];
		
		for (int i=0; i<identityMatrix1.length; i++) {
			for (int j=0; j<identityMatrix1[0].length; j++) {
				
				if (i==j) {
					identityMatrix1[i][j] = 1;
				}
				else {
					identityMatrix1[i][j] = 0;
				}
			}
		}
		
		for (int i=0; i<identityMatrix2.length; i++) {
			
			identityMatrix2[i] = 1;
		}
		double[] multiplication = ODESolverNewtonRaphson.multipliesMatrices(identityMatrix1, identityMatrix2);
		
		for (int i=0; i < multiplication.length; i++) {
			
			assertEquals(1, multiplication[i], ACCURACY);
		}
	}
}