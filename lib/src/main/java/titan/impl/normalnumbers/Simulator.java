package titan.impl.normalnumbers;

import java.util.ArrayList;

public class Simulator {
	public static void main(String[] args) {
		
		double y0 = 3;
		double t0 = 1;
		double h = 0.50; //also for h=0.25
		double tf = 2; //also for h=0.50
		
		State initialState = new State(y0, t0);
		
		System.out.println("Running Euler");
		SimulatorEuler simEuler = new SimulatorEuler();
		ArrayList list1 = simEuler.tada(initialState, tf, h, new ODEFunction());
		System.out.println(list1);
		//System.out.println(list1.get(list1.size()-1));
		System.out.println();
		
		
		System.out.println("Running RK4");
		SimulatorRK4 simRK4 = new SimulatorRK4();
		ArrayList list4 = simRK4.tada(initialState, tf, h, new ODEFunction());
		System.out.println(list4);
		//System.out.println(list4.get(list4.size()-1));
		System.out.println();
		
		
		System.out.println("Exact solution");
		System.out.println(tf*(3-2*Math.log(tf)));
		
	}
}