
package titan;

public class Vector3d implements Vector3dInterface {

	private double x;
	private double y;
	private double z;

	public Vector3d() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		//return the para
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vector3dInterface add(Vector3dInterface other) {
		return new Vector3d(this.getX() + other.getX(), this.getY() + other.getY(), this.getZ() + other.getZ());
	}

	public Vector3dInterface sub(Vector3dInterface other) {
		return new Vector3d(this.getX() - other.getX(), this.getY() - other.getY(), this.getZ() - other.getZ());
	}

	public Vector3dInterface mul(double scalar) {
		return new Vector3d(this.getX() * scalar, this.getY() * scalar, this.getZ() * scalar);
	}

	/**
	 * Scalar x vector multiplication, followed by an addition
	 * 
	 * @param scalar the double used in the multiplication step
	 * @param other  the vector used in the multiplication step
	 * @return the result of the multiplication step added to this vector, for
	 *         example:
	 * 
	 *         Vector3d a = Vector(); double h = 2; Vector3d b = Vector(); ahb =
	 *         a.addMul(h, b);
	 * 
	 *         ahb should now contain the result of this mathematical operation:
	 *         a+h*b
	 */
	public Vector3dInterface addMul(double scalar, Vector3dInterface other) {

		return this.add(other.mul(scalar));
	}

	/**
	 * @return the Euclidean norm of a vector
	 */
	public double norm() {

		return Math.sqrt((this.getX() * this.getX()) + (this.getY() * this.getY()) + (this.getZ() * this.getZ()));
	}

	/**
	 * @return the Euclidean distance between two vectors
	 */
	public double dist(Vector3dInterface other) {

		
		return this.sub(other).norm();


	}

	/**
	 * @return A string in this format: Vector3d(-1.0, 2, -3.0) should print out
	 *         (-1.0,2.0,-3.0)
	 */
	public String toString() {

		return "(" + x + "," + y + "," + z + ")";
	}
}
