package titan.impl.normalnumbers;

import java.util.ArrayList;

/**
 * Simulator of a probe with an Euler ODE solver 
 * @author Group 12
 *
 */

public class SimulatorHeun {
	
	public ArrayList tada(State initialState, double t_final, double stepSize, ODEFunction function) {
		ArrayList list = new ArrayList<>();
		
		ODESolverHeun rk3 = new ODESolverHeun();
		State[] states = rk3.solve(function, initialState, t_final, stepSize);
		int i = 0;
		for(State state : states) {
			if(state == null) {
				break;
			}
			list.add(i, ((State) state).getElement());
			i++;
		}
		return list;
	}
}