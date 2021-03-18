package titan;

public class State implements StateInterface {

	Vector3d position = new Vector3d();

	public State(double x, double y, double z) {
		position.setX(x);
		position.setY(y);
		position.setZ(z);
	}

	@Override
	public StateInterface addMul(double step, RateInterface rate) {
		
		Rate arate = (Rate) rate;
		position = (Vector3d) position.addMul(step,arate.speedy());
		return this;
	}

}
