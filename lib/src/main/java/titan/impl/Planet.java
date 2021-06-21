package titan.impl;

import titan.Vector3dInterface;
/**
 * A class that can set and get the name, the postion, the velocity, the radius and the mass of every planets
 *  @author Group 12
 */

public class Planet {

	private final double mass; // kg
	private final double radius; // m

	private Vector3dInterface position;
	private Vector3dInterface velocity;
	private String name;

	Planet(double mass, double radius, double xPosition, double yPosition, double zPosition, double xSpeed, double ySpeed, double zSpeed, String name) {

		this.mass = mass;
		this.radius = radius;
		position = new Vector3d(xPosition, yPosition, zPosition);
		velocity = new Vector3d(xSpeed, ySpeed, zSpeed);
		this.name = name;
	}

	
	public Vector3dInterface getPosition() {
		
		return position;
	}

	
	public void setPosition(Vector3dInterface position) {
		
		this.position = position;
	}

	
	public Vector3dInterface getVelocity() {
		
		return velocity;
	}

	
	public void setVelocity(Vector3dInterface velocity) {
		
		this.velocity = velocity;
	}

	public String getName() {
		
		return name;
	}

	
	public void setName(String name) {
		
		this.name = name;
	}

	
	public double getMass() {
		
		return mass;
	}

	
	public double getRadius() {
		
		return radius;
	}
}