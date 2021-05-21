package titan.impl;

import titan.RateInterface;

/**
 * An interface representing the time-derivative (rate-of-change) of the state of a system.
 *
 * The only uses of this interface are to be the output of the ODEFunctionInterface,
 * and to participate in the addMul method of StateInterface. A concrete State class
 * must cast the rate argument of addMul to a concrete Rate class of the expected type.
 *
 * For example, a Vector2d object might implement both StateInterface and RateInterface,
 * and define an addMul method taking and returning Vector2d object. The overriden addMul
 * from StateInterface would then be implemented by casting the rate to Vector2d, and
 * dispatching to the addMul method taking a Vector2d.
 */
public class Rate implements RateInterface {
	
	private Vector3d[] velocity;
	private Vector3d[] acceleration;

	public Rate(Vector3d[] velo, Vector3d[] accel) {
		
		this.velocity = new Vector3d[velo.length];
		this.acceleration = new Vector3d[accel.length];
		System.arraycopy(velo, 0, this.velocity, 0, velo.length);
		System.arraycopy(accel, 0, this.acceleration, 0, accel.length);
	}
	
	public Vector3d[] getVelocity() {
			
			return velocity;
	}
	
	public void setVelocity(Vector3d[] velo) {
		
		System.arraycopy(velo, 0, this.velocity, 0, velo.length);
	}
	
	public Vector3d[] getAcceleration() {
		
		return acceleration;
	}
	
	public void setAcceleration(Vector3d[] accel) {
		
		System.arraycopy(accel, 0, this.acceleration, 0, accel.length);
	}

	public Rate mul(double scalar) {

		Rate newRate = new Rate(new Vector3d[velocity.length], new Vector3d[acceleration.length]);
		newRate.setVelocity(velocity);
		newRate.setAcceleration(acceleration);
		
		for (int i = 0; i < acceleration.length; i++) {
			
			Vector3d newVelo = (Vector3d) newRate.getVelocity()[i].mul(scalar);
			Vector3d newAcce = (Vector3d) newRate.getAcceleration()[i].mul(scalar);
			newRate.getVelocity()[i] = newVelo;
			newRate.getAcceleration()[i] = newAcce;
		}
		return newRate;
	}
	
	public Rate addMul(double scalar, Rate vector) {
		
		Vector3d newVelo[] = new Vector3d[this.velocity.length];
		Vector3d newAcce[] = new Vector3d[this.acceleration.length]; 
			
		for (int i = 0; i < acceleration.length; i++) {
			
			newVelo[i] = (Vector3d) this.velocity[i].addMul(scalar, vector.getVelocity()[i]);
			newAcce[i] = (Vector3d) this.acceleration[i].addMul(scalar, vector.getAcceleration()[i]);
		}
		Rate newRate = new Rate(newVelo, newAcce);
		
		return newRate;
	}
}
