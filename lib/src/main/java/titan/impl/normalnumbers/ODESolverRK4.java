package titan.impl.normalnumbers;

public class ODESolverRK4 {

	public State[] solve(ODEFunction f, State y0, double tf, double h) {
		
		State[] arr = new State[(int) Math.ceil(tf / h -1)];
		arr[0] = y0;
		double currentTime = y0.getTime();
		int i = 1;

		while(currentTime < tf) {
			arr[i] = step(f, currentTime, arr[i - 1], h);
			//System.out.println(currentTime);
			currentTime += h;
			i++;
		}
		/*
		for (int i = 1; i < arr.length; i++) {
		
			arr[i] = step(f, currentTime, arr[i - 1], h);
			System.out.println(currentTime);
			currentTime += h;
		}
		*/
		return arr;	
	}

	// Fourth-order Runge-Kutta formula
	public State step(ODEFunction f, double t, State y, double h) {
		
		Rate k1, k2, k3, k4, ki;
		
		k1 = f.call(t, y);
		k2 = f.call(t + (0.5*h), y.addMul(0.5*h, k1));
		k3 = f.call(t + (0.5*h), y.addMul(0.5*h, k2));
		k4 = f.call(t + h, y.addMul(h, k3));
		
		ki = k1.addMul(2, k2);
		ki = ki.addMul(2, k3);
		ki = ki.addMul(1, k4);
		
		ki = ki.mul(h/6);

		return new State((y.addMul(1, ki)).getElement(), t+h);
		//return (State) y.addMul(0.5, ki);
	}
}