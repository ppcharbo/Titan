
package titan.impl;

import titan.RateInterface;
import titan.StateInterface;

public class State implements StateInterface {

	Vector3d position = new Vector3d();
	Vector3d velocity = new Vector3d();

	public State(double xPosition, double yPosition, double zPosition, double xVelocity, double yVelocity,
			double zVelocity) {

		position.setX(xPosition);
		position.setY(yPosition);
		position.setZ(zPosition);
		velocity.setX(xVelocity);
		velocity.setY(yVelocity);
		velocity.setZ(zVelocity);
	}

	@Override
	public StateInterface addMul(double step, RateInterface rate) {

		Rate arate = (Rate) rate;

		position = (Vector3d) position.addMul(step, arate.position());
		velocity = (Vector3d) velocity.addMul(step, arate.speed());

		return this;
	}

}
