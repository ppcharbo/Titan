package titan.impl;

import titan.Vector3dInterface;
/**
 * This class implements the Vector3dInterface
 * @author Group 12
 */
public class Vector3d implements Vector3dInterface {

	private double x;
	private double y;
	private double z;

	/**
	 * Default constructor for the Vector3d objects
	 */
	public Vector3d() {
		
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public vector2d(double x,double y){
		this.x=x;
		this.y=y;
	}

	/**
	 * 
	 * @param x: x-coordinate
	 * @param y: y-coordinate
	 * @param z: z-coordinate
	 */
	public Vector3d(double x, double y, double z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
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
	public double getZ() {

		return z;
	}

	/**
	 * @param z: the z to be set
	 */
	public void setZ(double z) {
		
		this.z = z;
	}

	/**
	 * @param other: adds the object vector to the passed/other vector
	 * @return new added vector
	 */
	public Vector3dInterface add(Vector3dInterface other) {
		
		return new Vector3d(this.getX() + other.getX(), this.getY() + other.getY(), this.getZ() + other.getZ());
	}

	/**
	 * @param other: substracts the object vector to the passed/other vector
	 * @return new substracted vector
	 */
	public Vector3dInterface sub(Vector3dInterface other) {
		
		return new Vector3d(this.getX() - other.getX(), this.getY() - other.getY(), this.getZ() - other.getZ());
	}

	/**
	 * @param scalar: scales the vector with a scalar
	 * @return new scaled vector
	 */
	public Vector3dInterface mul(double scalar) {
		
		return new Vector3d(this.getX() * scalar, this.getY() * scalar, this.getZ() * scalar);
	}

	
    /**
     * Scalar x vector multiplication, followed by an addition
     *
     * @param scalar the double used in the multiplication step
     * @param other  the vector used in the multiplication step
     * @return the result of the multiplication step added to this vector
     */
	public Vector3dInterface addMul(double scalar, Vector3dInterface other) {

		return this.add(other.mul(scalar));
	}

	
	/**
	 * Calculates the euclidian norm of a vector
	 * @return the Euclidean norm of a vector
	 */
	public double norm() {

		return Math.sqrt((this.getX() * this.getX()) + (this.getY() * this.getY()) + (this.getZ() * this.getZ()));
	}

	
	/**
	 * Calculates the Euclidean distance between two vectors
	 * @return the Euclidean distance between two vectors
	 */
	public double dist(Vector3dInterface other) {

		return this.sub(other).norm();
	}

	
	/**
	 * Prints the vector's x, y and z coordinates
	 * @return A string in this format: Vector3d(-1.0, 2, -3.0) should print out
	 *         (-1.0,2.0,-3.0)
	 */
	public String toString() {

		return "(" + x + "," + y + "," + z + ")";
	}
}