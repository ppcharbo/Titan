package titan.impl;

/**
 * 
 * This class is used to implement the WindModule
 * 
 * @author Group 12
 */
public class Vector2d {

	private double x;
	private double y;

	/**
	 * Default constructor for the Vector2d objects
	 */
	public Vector2d() {

		this.x = 0;
		this.y = 0;
	}

	/**
	 * 
	 * @param x: x-coordinate
	 * @param y: y-coordinate
	 */
	public Vector2d(double x, double y) {

		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param vector2
	 */
	public Vector2d(Vector2d vector2) {

		this.x = vector2.getX();
		this.y = vector2.getY();
	}

	/**
	 * @return x
	 */
	public double getX() {

		return x;
	}

	/**
	 * @param x: the x to be set
	 */
	public void setX(double x) {

		this.x = x;
	}

	/**
	 * @return y
	 */
	public double getY() {

		return y;
	}

	/**
	 * @param y: the y to be set
	 */

	public void setY(double y) {

		this.y = y;
	}

	/**
	 * @return z
	 */

	/**
	 * @param other: adds the object vector to the passed/other vector
	 * @return new added vector
	 */
	public Vector2d add(Vector2d other) {

		return new Vector2d(this.getX() + other.getX(), this.getY() + other.getY());
	}

	/**
	 * @param other: substracts the object vector to the passed/other vector
	 * @return new substracted vector
	 */
	public Vector2d sub(Vector2d other) {

		return new Vector2d(this.getX() - other.getX(), this.getY() - other.getY());
	}

	/**
	 * @param scalar: scales the vector with a scalar
	 * @return new scaled vector
	 */
	public Vector2d mul(double scalar) {

		return new Vector2d(this.getX() * scalar, this.getY() * scalar);
	}

	/**
	 * Scalar x vector multiplication, followed by an addition
	 *
	 * @param scalar the double used in the multiplication step
	 * @param other  the vector used in the multiplication step
	 * @return the result of the multiplication step added to this vector
	 */
	public Vector2d addMul(double scalar, Vector2d other) {

		return this.add(other.mul(scalar));
	}

	/**
	 * Calculates the euclidian norm of a vector
	 * 
	 * @return the Euclidean norm of a vector
	 */
	public double norm() {

		return Math.sqrt((this.getX() * this.getX()) + (this.getY() * this.getY()));
	}

	/**
	 * Calculates the Euclidean distance between two vectors
	 * 
	 * @return the Euclidean distance between two vectors
	 */
	public double dist(Vector2d other) {

		return this.sub(other).norm();
	}

	/**
	 * Prints the vector's x, y and z coordinates
	 * 
	 * @return A string in this format: Vector3d(-1.0, 2, -3.0) should print out
	 *         (-1.0,2.0,-3.0)
	 */
	public String toString() {

		return "(" + x + "," + y + ")";
	}
	
    public Vector2d cloneVector() {
        Vector2d clone = new Vector2d(this.x, this.y);
        return clone;
    }
}