package titan.impl;

import titan.RateInterface;
import titan.StateInterface;

/**
 * An interface representing the state of a system described by a differential equation.
 */
public class State implements StateInterface {
	
	private Vector3d position[];
	private Vector3d velocity[];
	private double time;
	private boolean isShip[]; 

	public State(Vector3d[] pos, Vector3d[] velo, boolean[] isShip, double t) {
		
		this.position = new Vector3d[pos.length];
		this.velocity = new Vector3d[velo.length];
		this.isShip = isShip;
		
		System.arraycopy(pos, 0, this.position, 0, pos.length);
		System.arraycopy(velo, 0, this.velocity, 0, velo.length);
		this.time = t;
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
	
	
	public boolean[] getisShip() {
		return isShip;
	}
	
	
	public void setisShip(boolean[] isShip) {
		
		System.arraycopy(isShip, 0, this.isShip, 0, isShip.length);
	}

	
	public double getTime() {
		
		return time;
	}

	
	public void setTime(double time) {
		
		this.time = time;
	}
	

    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step   The time-step of the update
     * @param rate   The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update. Required to have the same class as 'this'.
     */
	@Override
	public State addMul(double step, RateInterface rate) {
		
		State newState = new State(this.getPosition(), this.getVelocity(), this.getisShip(), this.getTime() + step);
		//The commented stuff is another approach/implementation
		//Vector3d[] newPosition = new Vector3d[this.position.length];
		//Vector3d[] newVelocity = new Vector3d[this.velocity.length];
		
		for (int i = 0; i < position.length; i++) {

			//newVelocity[i] = (Vector3d) newState.getVelocity()[i].addMul(step, ((Rate) rate).getAcceleration()[i]);
			//newPosition[i] = (Vector3d) newState.getPosition()[i].addMul(step, ((Rate) rate).getVelocity()[i]);
			
			Vector3d newVelo = (Vector3d) newState.getVelocity()[i].addMul(step, ((Rate) rate).getAcceleration()[i]);
			Vector3d newPosition = (Vector3d) newState.getPosition()[i].addMul(step, ((Rate) rate).getVelocity()[i]);
			
			newState.getPosition()[i] = newPosition;
			newState.getVelocity()[i] = newVelo;
		}
		//newState.setPosition(newPosition);
		//newState.setVelocity(newVelocity);
		
		return newState;
	}
}