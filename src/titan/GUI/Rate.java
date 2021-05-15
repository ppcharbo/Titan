package titan.GUI;

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
	private Vector3d[] accelaration;

	public Rate(Vector3d[] velo, Vector3d[] accel) {
		
		this.velocity = new Vector3d[velo.length];
		this.accelaration = new Vector3d[accel.length];
		System.arraycopy(velo, 0, this.velocity, 0, velo.length);
		System.arraycopy(accel, 0, this.accelaration, 0, accel.length);
	}
	
	public Vector3d[] getVelocity() {
			
			return velocity;
		}
	
	public void setVelocity(Vector3d[] velo) {
		
		System.arraycopy(velo, 0, this.velocity, 0, velo.length);
	}
	
	public Vector3d[] getAccelaration() {
		
		return accelaration;
	}
	
	public void setAccelaration(Vector3d[] accel) {
		
		System.arraycopy(accel, 0, this.accelaration, 0, accel.length);
	}

	public Rate mul(double scalar) {

		Rate newRate = new Rate(new Vector3d[velocity.length], new Vector3d[accelaration.length]);
		newRate.setVelocity(velocity);
		newRate.setAccelaration(accelaration);
		
		for (int i = 0; i < accelaration.length; i++) {
			
			Vector3d newVelo = (Vector3d) newRate.getVelocity()[i].mul(scalar);
			Vector3d newAcce = (Vector3d) newRate.getAccelaration()[i].mul(scalar);
			newRate.getVelocity()[i] = newVelo;
			newRate.getAccelaration()[i] = newAcce;
		}
		return newRate;
	}
	
	// Problem here
	public Rate addMul(double scalar, Rate vector) {

		Rate newRate = new Rate(new Vector3d[velocity.length], new Vector3d[accelaration.length]);
		for (int i = 0; i < accelaration.length; i++) {
			
			Vector3d newVelo = (Vector3d) newRate.getVelocity()[i].addMul(scalar, vector.getVelocity()[i]);
			Vector3d newAcce = (Vector3d) newRate.getAccelaration()[i].addMul(scalar, vector.getAccelaration()[i]);
			
			newRate.getAccelaration()[i] = newAcce;
			newRate.getVelocity()[i] = newVelo;
			
			/* Alternative way to update:
			newRate.getVelocity()[i].setX(newVelo.getX());
			newRate.getVelocity()[i].setY(newVelo.getY());
			newRate.getVelocity()[i].setZ(newVelo.getZ());
			
			newRate.getAccelaration()[i].setX(newAcce.getX());
			newRate.getAccelaration()[i].setY(newAcce.getY());
			newRate.getAccelaration()[i].setZ(newAcce.getZ());
			*/
		}
		return newRate;
	}
}
