package titan.impl;

import titan.RateInterface;

public class Rate implements RateInterface {
	private Vector3d[] velocity;
	private Vector3d[] accelaration;

	public Rate(Vector3d[] velocity, Vector3d[] accelaration) {
		this.velocity = new Vector3d[velocity.length];
		this.accelaration = new Vector3d[accelaration.length];
		System.arraycopy(velocity, 0, this.velocity, 0, velocity.length);
		System.arraycopy(accelaration, 0, this.accelaration, 0, accelaration.length);
	}

	public void setAccelaration(Vector3d[] accelaration) {
		System.arraycopy(accelaration, 0, this.accelaration, 0, accelaration.length);
	}

	public Vector3d[] getAccelaration() {
		return accelaration;
	}

	public void setVelocity(Vector3d[] velocity) {
		System.arraycopy(velocity, 0, this.velocity, 0, velocity.length);
	}

	public Vector3d[] getVelocity() {
		return velocity;

	}

	public Rate mul(double scalar) {

		Rate newRate = new Rate(new Vector3d[velocity.length], new Vector3d[accelaration.length]);
		newRate.setAccelaration(accelaration);
		newRate.setVelocity(velocity);
		for (int i = 0; i < accelaration.length; i++) {
			Vector3d newAcce = (Vector3d) newRate.getAccelaration()[i].mul(scalar);
			Vector3d newVelo = (Vector3d) newRate.getVelocity()[i].mul(scalar);
			newRate.getAccelaration()[i] = newAcce;
			newRate.getVelocity()[i] = newVelo;
		}
		return newRate;
	}

//rpoblem here
	public Rate addMul(double scalar, Rate vector) {

		Rate newRate = new Rate(new Vector3d[velocity.length], new Vector3d[accelaration.length]);
		newRate.setAccelaration(accelaration);
		newRate.setVelocity(velocity);
		for (int i = 0; i < accelaration.length; i++) {
			Vector3d newAcce = (Vector3d) newRate.getAccelaration()[i].addMul(scalar, vector.getAccelaration()[i]);
			Vector3d newVelo = (Vector3d) newRate.getVelocity()[i].addMul(scalar, vector.getVelocity()[i]);
			newRate.getAccelaration()[i] = newAcce;
			newRate.getVelocity()[i] = newVelo;
		}
		return newRate;
	}
}
