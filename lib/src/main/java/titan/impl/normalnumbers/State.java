package titan.impl.normalnumbers;

/**
 * An interface representing the state of a system described by a differential equation.
 */
public class State {
	
	private double w;
	private double time;

	public State(double we, double t) {
		this.w = we;
		this.time = t;
		//System.out.println(we);
	}

	
	public double getElement() {
		return this.w;
	}

	public double getTime() {
		return this.time;
	}

    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step   The time-step of the update
     * @param rate   The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update. Required to have the same class as 'this'.
     */
	public State addMul(double step, Rate rate) {
		// w1 = w0+h*f(t,y) at t(w1) = t(w0)+h
		State returner = new State(this.w + step*rate.getRate(), this.time + step);
		return returner;
	}
}