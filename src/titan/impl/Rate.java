package titan.impl;

import titan.RateInterface;

public class Rate implements RateInterface {
	
	/*
	 * creating this class rate that implements the rateInterface that is empty.. , we are just return the vector speed
	 */
	private Vector3d position;
	private Vector3d speed;

	public Rate(Vector3d position, Vector3d speed) {
		
		this.position = position; 
		this.speed = speed;
	}

	public Rate(double xPosition, double yPosition, double zPosition, double xSpeed, double ySpeed, double zSpeed) {
		
		position = new Vector3d(xPosition, yPosition, zPosition);
		speed = new Vector3d(xSpeed, ySpeed, zSpeed);
	}
	
	public Vector3d position() {

		return position;
	}

	public Vector3d speed() {

		return speed;
	}
}
