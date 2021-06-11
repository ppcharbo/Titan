package titan.impl.normalnumbers;

import java.util.ArrayList;

public class Simulator {
	public static void main(String[] args) {
		
		EulerSimulator sim = new EulerSimulator();
		State initialState = new State(3, 1);
		
		ArrayList list = sim.tada(initialState, 2, 0.5, new ODEFunction());
		
		System.out.println(list);
		System.out.println(2*(3-2*Math.log(2)));
	}
}