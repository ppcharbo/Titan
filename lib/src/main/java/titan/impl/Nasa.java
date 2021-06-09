package titan.impl;

import titan.StateInterface;

public class Nasa {

	private final static boolean INITIAL = false;
	private final static boolean END = true;
	
	private final static double STEP_SIZE = 24 * 60 * 60; // 1 day
	private final static double FINAL_TIME = 365.25 * 24 * 60 * 60; // 1 year
	
	/* From the NASA Horizon website:
	 * Ephemeris Type [change] : 	VECTORS
	 * Target Body [change] : 	Titan (SVI) [606]
	 * Coordinate Origin [change] : 	Solar System Barycenter (SSB) [500@0]
	 * Time Span [change] : 	Start=2020-04-01, Stop=2021-04-01, Step=1 d
	 * Table Settings [change] : 	quantities code=2; output units=KM-S
	 * Display/Output [change] : 	default (formatted HTML)
	 * 
	 * 
	 * 
	 * 2458940.500000000 = A.D.
	 * 2020-Apr-01 00:00:00.0000 TDB
	 *  
	 * X =  6.332873118527889E+08
	 * Y = -1.357175556995868E+09
	 * Z = -2.134637041453660E+06
	 * 
	 * VX = 3.056877965721629E+00
	 * VY = 6.125612956428791E+00
	 * VZ = -9.523587380845593E-01
	 * 
	 * --> difference with text file is 10^3 for x,y,z,vx,vy,vz
	 * 
	 * 
	 * ---------------------------------------------------------------
	 * 
	 * 
	 * 2459305.500000000 = A.D.
	 * 2021-Apr-01 00:00:00.0000 TDB
	 * 
	 * X =  8.789384100968744E+08
	 * Y = -1.204002291074617E+09
	 * Z = -1.435729928774685E+07
	 * 
	 * VX =  4.600295683062070E+00
	 * VY =  1.023830877803223E+01
	 * VZ = -2.479030926015208E+00
	 * 
	 * 
	 */
	
	public static void main(String[] args) {
        long start = System.nanoTime();
        
        if(INITIAL) {
            System.out.println("From the text file:");
            System.out.println("x= 6.332873118527889e+11");
            System.out.println("y=-1.357175556995868e+12");
            System.out.println("z=-2.134637041453660e+09");
            
            System.out.println("vx= 3.056877965721629e+03");
            System.out.println("vy= 6.125612956428791e+03");
            System.out.println("vz= -9.523587380845593e+02");
            
    		System.out.println();
            
            initialStateEuler();
            initialStateRK4();
            initialStateVerlet();
        }
        
        if(END) {
    		runEuler();
    		runRK4();
    		runVerlet();
        }
        
		System.out.println();
		System.out.println("\nThe time needed to perform this analysis was: " + (System.nanoTime() - start) / 1000000.0 + " ms.");
	}

	private static void initialStateEuler() {
		System.out.println("Initial State Euler");
		ProbeSimulatorEuler euler = new ProbeSimulatorEuler();
		
		//Taken from SystemPlanet.java
		Vector3d probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752); // row 367
		Vector3d probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); // row 133 with speed 130E3

		StateInterface[] solvedStates = euler.trajectoryGUI(probe_pos, probe_vel, STEP_SIZE, FINAL_TIME);
		
		System.out.println("Position at beginning for Titan:");
		System.out.println("t = " + ((State) solvedStates[0]).getTime());
		System.out.println("Position = " + ((State) solvedStates[0]).getPosition()[10]);
		System.out.println("Velocity = " + ((State) solvedStates[0]).getVelocity()[10]);
		
		System.out.println();
		System.out.println();
		
	}
	
	private static void initialStateRK4() {
		System.out.println("Initial State RK4");
		ProbeSimulatorRungaKutta rk = new ProbeSimulatorRungaKutta();
		
		//Taken from SystemPlanet.java
		Vector3d probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752); // row 367
		Vector3d probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); // row 133 with speed 130E3
		
		StateInterface[] solvedStates = rk.trajectoryGUI(probe_pos, probe_vel, STEP_SIZE, FINAL_TIME);
		
		System.out.println("Position at beginning for Titan:");
		System.out.println("t = " + ((State) solvedStates[0]).getTime());
		System.out.println("Position = " + ((State) solvedStates[0]).getPosition()[10]);
		System.out.println("Velocity = " + ((State) solvedStates[0]).getVelocity()[10]);
		
		System.out.println();
		System.out.println();
		
	}
	
	private static void initialStateVerlet() {
		System.out.println("Initial State Verlet");
		ProbeSimulatorVerlet verlet = new ProbeSimulatorVerlet();
		
		//Taken from SystemPlanet.java
		Vector3d probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752); // row 367
		Vector3d probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); // row 133 with speed 130E3
		
		StateInterface[] solvedStates = verlet.trajectoryGUI(probe_pos, probe_vel, STEP_SIZE, FINAL_TIME);
		
		System.out.println("Position at beginning for Titan:");
		System.out.println("t = " + ((State) solvedStates[0]).getTime());
		System.out.println("Position = " + ((State) solvedStates[0]).getPosition()[10]);
		System.out.println("Velocity = " + ((State) solvedStates[0]).getVelocity()[10]);
		
		System.out.println();
		System.out.println();
		
	}

	private static void runVerlet() {
		System.out.println("Doing Verlet");
		ProbeSimulatorVerlet verlet = new ProbeSimulatorVerlet();
		
		//Taken from SystemPlanet.java
		Vector3d probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752); // row 367
		Vector3d probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); // row 133 with speed 130E3
		
		StateInterface[] solvedStates = verlet.trajectoryGUI(probe_pos, probe_vel, STEP_SIZE, FINAL_TIME);
		
		System.out.println("Position at last for Titan:");
		System.out.println("t = " + ((State) solvedStates[solvedStates.length-1]).getTime());
		System.out.println("Position = " + ((State) solvedStates[solvedStates.length-1]).getPosition()[10]);
		System.out.println("Velocity = " + ((State) solvedStates[solvedStates.length-1]).getVelocity()[10]);
	}

	private static void runRK4() {
		System.out.println("Doing RK4");
		ProbeSimulatorRungaKutta rk = new ProbeSimulatorRungaKutta();
		
		//Taken from SystemPlanet.java
		Vector3d probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752); // row 367
		Vector3d probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); // row 133 with speed 130E3
		
		StateInterface[] solvedStates = rk.trajectoryGUI(probe_pos, probe_vel, STEP_SIZE, FINAL_TIME);
		
		System.out.println("Position at last for Titan:");
		System.out.println("t = " + ((State) solvedStates[solvedStates.length-1]).getTime());
		System.out.println("Position = " + ((State) solvedStates[solvedStates.length-1]).getPosition()[10]);
		System.out.println("Velocity = " + ((State) solvedStates[solvedStates.length-1]).getVelocity()[10]);
		
		System.out.println();
		System.out.println();
	}

	private static void runEuler() {
		System.out.println("Doing Euler");
		ProbeSimulatorEuler euler = new ProbeSimulatorEuler();
		
		//Taken from SystemPlanet.java
		Vector3d probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752); // row 367
		Vector3d probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); // row 133 with speed 130E3
		
		StateInterface[] solvedStates = euler.trajectoryGUI(probe_pos, probe_vel, STEP_SIZE, FINAL_TIME);
		
		System.out.println("Position at last for Titan:");
		System.out.println("t = " + ((State) solvedStates[solvedStates.length-1]).getTime());
		System.out.println("Position = " + ((State) solvedStates[solvedStates.length-1]).getPosition()[10]);
		System.out.println("Velocity = " + ((State) solvedStates[solvedStates.length-1]).getVelocity()[10]);
		
		System.out.println();
		System.out.println();
		
	}

}
