package titan.impl;

public class OpenLoopController {
	
	public InputFunctionLanding u;
	public InputFunctionLanding v;
	
	public InputFunctionLanding OpenLoopLandingSimulatorWRTU(State initialLaunchState) {
		
		//TODO Change shipWhenDistanceIsSmallest to ship
		int shipWhenDistanceIsSmallest = 0;
		Vector3d positionLaunch = initialLaunchState.getPosition()[shipWhenDistanceIsSmallest];
		Vector3d velocityLaunch = initialLaunchState.getVelocity()[shipWhenDistanceIsSmallest];
		double x = positionLaunch.getX();
		double y = positionLaunch.getY();
		double vx = velocityLaunch.getX();
		double vy = velocityLaunch.getY();
		
		//TODO define eta's
		double eta = 0;
		double eta2 = 0;
		InputFunctionLanding f = new InputFunctionLanding(x, y, eta, vx, vy, eta2);
		return u;
	}
	
	public InputFunctionLanding OpenLoopLandingSimulatorWRTV(State initialLaunchState) {
		//TODO Change shipWhenDistanceIsSmallest to ship
		int shipWhenDistanceIsSmallest = 0;
		Vector3d positionLaunch = initialLaunchState.getPosition()[shipWhenDistanceIsSmallest];
		Vector3d velocityLaunch = initialLaunchState.getVelocity()[shipWhenDistanceIsSmallest];
		double x = positionLaunch.getX();
		double y = positionLaunch.getY();
		double vx = velocityLaunch.getX();
		double vy = velocityLaunch.getY();
		
		//TODO define eta's
		double eta = 0;
		double eta2 = 0;
		InputFunctionLanding f = new InputFunctionLanding(x, y, eta, vx, vy, eta2);
		
		return v;
	}
	
}
