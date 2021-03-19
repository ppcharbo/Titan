package titan.impl;

import titan.RateInterface;

public class Rate implements RateInterface {
	/*
	 * creating this class rate that implements the rateInterface that is empty.. , we are just return the vector speed
	 */
	private Vector3d speed;

	public Rate(Vector3d vector) {
		speed = vector;
	}

	public Rate(double x, double y, double z) {
		speed = new Vector3d(x, y, z);
	}

	public Vector3d speedy() {

		return speed;

	}
}
