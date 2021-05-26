package titan.impl;

import java.util.ArrayList;


public class NewtonRaphson {
	/*
	 AllPlanets allPlanets = new AllPlanets();
	allPlanets.createPlanets();
	ArrayList<Planet> listOfPlanets = allPlanets.getListOfPlanets();
	Vector3d initialPosition = new Vector3d();
	Vector3d nextPosition = new Vector3d();
	//Rate raty = new Rate(listOfPlanets., null); //get acceleration
	Vector3d velocity = (Vector3d) listOfPlanets.get(0).getVelocity();
	//Vector3d accel = allPlanets.getListOfPlanets().get(0).
	// p(n+1) = p(n) - f(p(n))/f'(p(n))
	//nextPosition = (Vector3d) initialPosition.addMul(-1, velocity);
	 
	 */
	 
	 
	
	
	
	//create the jacobian matrix
	//vk+1 = vk - J^-1*Q
	// Q = distance vector  --> Position of titan - position of the probe (Tx-Px, Ty-Py, Tz-Pz)
	
	public static int[][] jacobianMatrix(){
		int [][] jacobian = new int[3][3];
		
		return jacobian;
	}


}
