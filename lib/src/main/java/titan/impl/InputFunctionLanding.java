package titan.impl;

public class InputFunctionLanding {
	
	private double x; 			// horizontal position
	private double y; 			// vertical position
	private double eta;  		// angle of rotation
	public double XVelocity; 	// velocity of X
	public double YVelocity; 	// velocity of Y
	public double etader; 		// first derivative of eta
	
	//Provided tolerances in the manual
	private final double xTolerance = 0.1;
	private final double etaTolerance = 0.02;
	private final double xDerTolerance = 0.1;
	private final double yDerTolerance = 0.1;
	private final double etaDerTolerance = 0.01;
	
	public double u;    // acceleration provided by the main thruster
	public double v;    // the torque provided by the side thrusters
	public final double TITAN_GRAVITY = 1.352;
	
	/*
	public InputFunctionLanding(double x, double y, double eta, double XVelocity, double YVelocity, double etader) {
		
		this.x = x;
		this.y = y;
		this.eta = eta;
		this.XVelocity = XVelocity;
		this.YVelocity = YVelocity;
		this.etader = etader;
		
	}
	*/
	
	public InputFunctionLanding(double x, double y, double eta, double XVelocity, double YVelocity, double etader) {
		
		this.x = x;
		this.y = y;
		this.eta = eta;
		this.XVelocity = XVelocity;
		this.YVelocity = YVelocity;
		this.etader = etader;
	}
	
	public double getU() {
		return u;
	}
	
	public double getV() {
		return v;
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
	
	public boolean testAllConditionsAtY0() {
		if(testX() && testEta() && testDerX() && testDerY() && testDerEta()) {
			return true;
		}
		return false;
	}

	public boolean testX() {
		if(Math.abs(this.x) <= xTolerance) {
			return true;
		}
		return false;
	}
	
	public boolean testEta() {
		if(Math.abs(this.eta % 2*Math.PI) <= etaTolerance) {
			return true;
		}
		return false;
	}
	
	public boolean testDerX() {
		if(Math.abs(this.XVelocity) <= xDerTolerance) {
			return true;
		}
		return false;
	}
	
	public boolean testDerY() {
		if(Math.abs(this.YVelocity) <= yDerTolerance) {
			return true;
		}
		return false;
	}
	
	public boolean testDerEta() {
		if(Math.abs(this.etader) == etaDerTolerance) {
			return true;
		}
		return false;
	}

}
