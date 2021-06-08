package titan.impl;

public class InputFunctionLanding {
	
	private double x; 			// horizontal position
	private double y; 			// vertical position
	private double eta;  		// angle of rotation
	public double XVelocity; 	// velocity of X
	public double YVelocity; 	// velocity of Y
	public double etader; 		// first derivative of eta
	
	//public double u;    // acceleration provided by the main thruster
	//public double v;    // the torque provided by the side thrusters
	//public final double TITAN_GRAVITY = 1.352 ;
	
	
	public InputFunctionLanding(double x, double y, double eta, double XVelocity, double YVelocity, double etader) {
		
		this.x = x;
		this.y = y;
		this.eta = eta;
		this.XVelocity = XVelocity;
		this.YVelocity = YVelocity;
		this.etader = etader;
		
	}
	
	public double getInputX() {
		
		return x;
	}
	
	public double getInputY() {
		
		return y;
	}
	
	public double getRotationAngle() {
		
		return eta;
	}

}
