package titan.impl;

import titan.RateInterface;
import titan.StateInterface;

public class State implements StateInterface {
	
/*
 * we create this class to implement the StateInterface,  the position and velocity  are set in the constructor and return the position in the function addMul
 */
	Vector3d position = new Vector3d();
	Vector3d speed = new Vector3d();
	//TODO
	public State(double x, double y, double z, double vx, double vy, double vz) {
		position.setX(x);
		position.setY(y);
		position.setZ(z);
		speed.setX(vx);
		speed.setY(vy);
		speed.setZ(vz);
	}

	@Override
	public StateInterface addMul(double step, RateInterface rate) {

		Rate arate = (Rate) rate;
		position = (Vector3d) position.addMul(step, arate.position());
		speed = (Vector3d) speed.addMul(step, arate.speed());
		return this;
	}
}
