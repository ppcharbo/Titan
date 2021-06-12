package titan.impl.normalnumbers;

public class Rate {
	private double change;
	
	public Rate(double rate) {
		this.change = rate;
	}
	
	public double getRate() {
		return this.change;
	}
	
	public Rate mul(double scalar) {
		return new Rate(this.change*scalar);
	}
	
	public Rate addMul(double scalar, Rate rat) {
		return new Rate(this.change + scalar*rat.getRate());
	}
}