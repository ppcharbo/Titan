package titan.impl.normalnumbers;

import java.util.ArrayList;

public class Simulator {
	public static void main(String[] args) {
		
		System.out.println("Running Euler");
		State initialState = new State(3, 1);
		
		
		EulerSimulator simEuler = new EulerSimulator();
		ArrayList list1 = simEuler.tada(initialState, 2, 0.5, new ODEFunction());
		System.out.println(list1);
		
		
		System.out.println("Running RK4");
		RungeKuttaSimulator simRK4 = new RungeKuttaSimulator();
		ArrayList list2 = simRK4.tada(initialState, 2, 0.5, new ODEFunction());
		System.out.println(list2);
		
		/*
		System.out.println("Running Verlet");
		RungeKuttaSimulator simVerlet = new RungeKuttaSimulator();
		ArrayList list3 = simVerlet.tada(initialState, 2, 0.5, new ODEFunction());
		System.out.println(list3);
		*/
		
		System.out.println(2*(3-2*Math.log(2)));
		
	}
}