package titan.impl;

import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

/*
*
 */
public class State implements StateInterface {
	private Vector3d position[];
	private Vector3d velocity[];
	private double time;

	public State(Vector3d[] position, Vector3d[] velocity, double time) {
		this.position = new Vector3d[position.length];
		this.velocity = new Vector3d[velocity.length];
		System.arraycopy(position, 0, this.position, 0, position.length);
		System.arraycopy(velocity, 0, this.velocity, 0, velocity.length);
		this.time = time;
	}

	public Vector3d[] getPosition() {
		return position;

	}
 


	public void setPosition(Vector3d[] position) {
		System.arraycopy(position, 0, this.position, 0, position.length);
	}

	public Vector3d[] getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3d[] velocity) {
		System.arraycopy(velocity, 0, this.velocity, 0, velocity.length);
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public State addMul(double step, RateInterface rate) {
		State newState = new State(position, velocity, time + step);
		newState.setPosition(position);
		newState.setVelocity(velocity);
		for (int i = 0; i < position.length; i++) {
			Vector3d newPosition = (Vector3d) newState.getPosition()[i].addMul(step, ((Rate) rate).getVelocity()[i]);
			Vector3d newVelo = (Vector3d) newState.getVelocity()[i].addMul(step, ((Rate) rate).getAccelaration()[i]);
			newState.getPosition()[i] = newPosition;
			newState.getVelocity()[i] = newVelo;
		}
		return newState;
	}

}
