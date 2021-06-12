package titan.impl.normalnumbers;

/*
 * This is an interface for the function f that represents the
 * differential equation dy/dt = f(t,y).
 * You need to implement this function to represent to the laws of physics.
 *
 * For example, consider the differential equation
 *   dy[0]/dt = y[1];  dy[1]/dt=cos(t)-sin(y[0])
 * Then this function would be
 *   f(t,y) = (y[1],cos(t)-sin(y[0])).
 *
 */
public class ODEFunction {
	/*
	 * @param   t   the time at which to evaluate the function
	 * @param   y   the state at which to evaluate the function
	 * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
	 *
	*/
	public Rate call(double t, State y) {
		//System.out.println(y.getElement());
		//System.out.println(t);
		double rate = y.getElement()/t-2;
		//System.out.println(rate);
		return new Rate(rate);
	}
}