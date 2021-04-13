package titan.impl;

import titan.RateInterface;
import titan.StateInterface;

public class State implements StateInterface {
	
/*
 * we create this class to implement the StateInterface,  the position and velocity  are set in the constructor and return the position in the function addMul
 */
	
	Vector3d position = new Vector3d();
	Vector3d speed = new Vector3d();
	
	public State(double xPosition, double yPosition, double zPosition, double xSpeed, double ySpeed, double zSpeed) {
		
		position.setX(xPosition);
		position.setY(yPosition);
		position.setZ(zPosition);
		speed.setX(xSpeed);
		speed.setY(ySpeed);
		speed.setZ(zSpeed);
	}

	@Override
	public StateInterface addMul(double step, RateInterface rate) {

		Rate arate = (Rate) rate;
		
		position = (Vector3d) position.addMul(step, arate.position());
		speed = (Vector3d) speed.addMul(step, arate.speed());
		
		return this;
	}
}
