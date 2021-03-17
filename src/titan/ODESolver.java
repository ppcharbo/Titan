package titan;

public class ODESolver implements ODESolverInterface {

	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {

		return null;
	}

	@Override
	public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
		StateInterface[] arr = new StateInterface[(int) Math.round((tf / h) + 1)];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = step(f, h * i, y0, h);
		}
		return arr;
	}

	@Override
	public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

		return y.addMul(h, f.call(t, y));

	}

}
