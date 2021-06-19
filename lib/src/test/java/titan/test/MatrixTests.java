package titan.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import titan.impl.ODESolverNewtonRaphson;

public class MatrixTests {
	
	public final double ACCURACY = 1E-03;
	
	@Test
	public void testInverse3By3() {

		ODESolverNewtonRaphson solver = new ODESolverNewtonRaphson();
		double[][] A = { { 9,5,2 }, {4,3,6}, {5,10,16} };
		double[][] inverse = solver.inverseMatrix3by3(A);
		double[][] inverseMatlab = { {0.05263157894736846, 0.26315789473684204, -0.10526315789473685},
									 {0.14912280701754377, -0.5877192982456139, 0.2017543859649123},
									 {-0.10964912280701754, 0.2850877192982456, -0.030701754385964914} };
		
		//use a double for loop to check every element in the inversed matrix
		for(int i = 0; i < inverse.length; i++) {
			for (int j = 0; j < inverse[0].length; j++) {
				assertEquals(inverseMatlab[i][j], inverse[i][j], ACCURACY);
			}
		}
	}
	
	
	@Test
	public void testMultiplication(){
		ODESolverNewtonRaphson solver = new ODESolverNewtonRaphson();
		double[][] A = { {1, 18}, {2, 7} };
		double[] v = { 5, 6 };
		
		double[] multiplicationAv = solver.matrixMultiplication(A, v);
		double[] resultMatlab = { 113, 52 };
		
		for (int i = 0; i < resultMatlab.length; i++) {
			assertEquals(resultMatlab[i], multiplicationAv[i], ACCURACY);
		}
	}	
}
