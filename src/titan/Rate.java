package titan;

public class Rate implements RateInterface {
	private Vector3d speed;

	public Rate(Vector3d vector) {
		speed = vector;
	}

	public Rate(double x, double y, double z) {
		speed = new Vector3d(x, y, z);
	}

	public Vector3d speedy() {

		return speed;

	}
}
