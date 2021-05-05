package titan.impl;

import titan.ODEFunctionInterface;
import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface; 

public class ODEFunction implements ODEFunctionInterface {

	public Vector3d speed;
	
	public ODEFunction() {
		//nothing;
	}
	
	public ODEFunction(Vector3dInterface v0) {
		
		this.speed = (Vector3d) v0;
	}
	
	@Override
	public RateInterface call(double t, StateInterface y) {

		Planet p = (Planet) y;
		
		Vector3d positionRate = (Vector3d) p.getSpeed();
		Vector3d speedRate = (Vector3d) p.accelerationForce();
		
		Rate rate = new Rate(positionRate, speedRate);
 
		return rate;	
	}
	
	public void callAll(double t) {

		for (Planet p : Planet.values()) {
			
			call(t, p);
		}
	}
}
