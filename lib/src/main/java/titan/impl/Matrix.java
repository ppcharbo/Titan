package titan.impl;

public class Matrix {

	public int [][] matrixMultiplication(int [][] A, int[][]B) {
	int M[][] =new int [3][3];
	 A =new int [3][3];
     B =new int [3][3];
	for(int i=0;i<3;i++) {
		for(int j=0;j<3;j++) {
			for(int k=0;k<3;k++) {
				M[i][j]+=A[i][k]*B[k][j];	
			}
		}
	}
	return M;
	}
	public int [] vectorMultiplication(int[][]M, int[]v) {
		int [] v1 = new int [3];
		for(int i=0;i<3;i++) {
			int element =0;
			for(int j=0;j<3;j++) {
				element +=M[i][j]*v[j];
				
			}
			v1[i]=element;
			
		}
		return v1;
	
	}
	public static int[][] minor(int[][]M, int row,int column){
		int[][] minor= new int[2][2];
		for(int i=0;i<3;i++) {
			for(int j=0;j<M[i].length && i!=row ;j++) {
				if(j!=column) {
					minor[i<row?i:i-1][j<column?j:j-1]=M[i][j];	
				}
			}
		}
		return minor;
	}
	public static int determinant(int [][]M){
		int determinant;
		determinant = (M[0][0]*((M[1][1]*M[2][2])-(M[1][2]*M[2][1])))-(M[0][1]*((M[1][0]*M[2][2])-(M[1][2]*M[0][2])))-(M[0][2]*((M[1][0]*M[2][1])-(M[1][1]*M[2][0])));
		return determinant;
	}
	public static int[][]inverse(int M[][]){
	int [][]M1 = new int [3][3];
	int determinant = determinant(M);
	for(int i=0;i<3;i++) {
		for(int j=0; j<M[i].length;j++) {
			M1[i][j]= determinant(minor(M,i,j))*(int)Math.pow(-1, i+j);		
		}
	}
	for(int i=0;i<3;i++) {
		for(int j=0; j<=i;j++) {
			int temp = M1[i][j];
			M1[i][j]= M1[j][i]/determinant;
			M1[j][i]= determinant *temp;
		}
	}
	
	return M1;	
	}
}
	
