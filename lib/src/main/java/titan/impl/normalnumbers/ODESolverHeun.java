package titan.impl.normalnumbers;

public class ODESolverHeun {

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

	// Heun's formula
	public State step(ODEFunction f, double t, State y, double h) {
		
		Rate k1, k2, k3, ki;
		
		k1 = f.call(t, y);
		k2 = f.call(t + ((1/3)*h), y.addMul((1/3)*h, k1));
		k3 = f.call(t + ((2/3)*h), y.addMul((2/3)*h, k2));
		
		ki = k1.addMul(3, k3);
		
		ki = ki.mul(h/4);

		return new State((y.addMul(1, ki)).getElement(), t+h);
		//return (State) y.addMul(0.5, ki);
	}
}