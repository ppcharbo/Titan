package titan.impl;

import titan.RateInterface;

public class Rate implements RateInterface {
	
	/*
	 * creating this class rate that implements the rateInterface that is empty.. , we are just return the vector speed
	 */
	private Vector3d position;
	private Vector3d speed;

	public Rate(Vector3d ratePosition, Vector3d rateSpeed) {
		
		this.position = ratePosition; 
		this.speed = rateSpeed;
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
	
	public static Rate addRates(Rate k1, Rate k2, Rate k3, Rate k4) {
		
		Vector3d p1 = k1.position();
		Vector3d p2 = (Vector3d) k2.position().mul(2);
		Vector3d p3 = (Vector3d) k3.position().mul(2);
		Vector3d p4 = k4.position();
		
		Vector3d s1 = k1.speed();
		Vector3d s2 = (Vector3d) k2.speed().mul(2);
		Vector3d s3 = (Vector3d) k3.speed().mul(2);
		Vector3d s4 = k4.speed();
		
		Vector3d ratePosition = (Vector3d) p1.add(p2).add(p3).add(p4);
		Vector3d rateSpeed = (Vector3d) s1.add(s2).add(s3).add(s4);
		
		return new Rate(ratePosition, rateSpeed);
	}
}
