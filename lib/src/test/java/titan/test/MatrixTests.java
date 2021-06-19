package titan.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import titan.impl.ODESolverNewtonRaphson;

public class MatrixTests {
	
	public final double ACCURACY = 1E-3;
	
<<<<<<< HEAD
	@Test
	public void testInverse6By6() {
		
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
		
		double[][] inversedIdentityMatrix = ODESolverNewtonRaphson.inverse6x6JacobianMatrix(identityMatrix);
		for (int i=0; i<identityMatrix.length; i++) {
			for (int j=0; j<identityMatrix[0].length; j++) {
				System.out.println(inversedIdentityMatrix[i][j]);
				assertEquals(identityMatrix[i][j], inversedIdentityMatrix[i][j], ACCURACY);
			}
		}
	}

	/*
	@Test
	//Don't know how to make sure that we can generate a 6by6 invertible matrix
	ODESolverNewtonRaphson solver = new ODESolverNewtonRaphson();
	double[][] A = { {1, 0, 0, 0, 0, 0}, {};
	double[][] inverse = solver.inverse(A);
	double[][] inverseMatlab = { {0.05263157894736846, 0.26315789473684204, -0.10526315789473685},
								 {0.14912280701754377, -0.5877192982456139, 0.2017543859649123},
								 {-0.10964912280701754, 0.2850877192982456, -0.030701754385964914} };
	
	//use a double for loop to check every element in the inversed matrix
	for(int i = 0; i < inverse.length; i++) {
		for (int j = 0; j < inverse[0].length; j++) {
			assertEquals(inverseMatlab[i][j], inverse[i][j], ACCURACY);
		}
	}
	*/
=======
>>>>>>> branch 'main' of https://github.com/Basaratojo/Titan.git
	
	@Test
	public void testMultiplication1() {
		
		double[][] A = { {1, 18}, {2, 7} };
		double[] v = { 5, 6 };
		
		double[] multiplicationAv = ODESolverNewtonRaphson.matrixMultiplication(A, v);
		double[] resultMatlab = { 113, 52 };
		
		for (int i = 0; i < resultMatlab.length; i++) {
			assertEquals(resultMatlab[i], multiplicationAv[i], ACCURACY);
		}
<<<<<<< HEAD
	}
	
	@Test
	public void testMultiplication() {
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
		
		double[] multiplication = ODESolverNewtonRaphson.matrixMultiplication(identityMatrix1, identityMatrix2);
		
		
		for (int i=0; i < multiplication.length; i++) {
			
			assertEquals(1, multiplication[i], ACCURACY);
		}
	}
}
=======
	}	
}
>>>>>>> branch 'main' of https://github.com/Basaratojo/Titan.git
