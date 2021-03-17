package titan;

public class ODEFunction implements ODEFunctionInterface {

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
	 * @param   t   the time at which to evaluate the function
	 * @param   y   the state at which to evaluate the function
	 * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
	 */
	@Override
    public RateInterface call(double t, StateInterface y) {
        //                             ^
        //  ý(t)         =         f(t, y(t))
        //   v
        // return

        // we are function f in this case
        // question: what's our function f(t, y(t))?

        return null;
    }

}
