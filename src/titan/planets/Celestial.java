package titan.planets;

import example.CelestialBody;

public class Celestial {

	public String name;
	public double mass;
	public double x;
	public double y;
	public double z;
	public double vx;
	public double vy;
	public double vz;
	public double radius;

	public Celestial(String name, double mass, double x, double y, double z, double vx, double vy, double vz) {
		this.name = name;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.z = z;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;

	}

	public static Celestial create(String name, double mass, double x, double y, double z, double vx, double vy, double vz) {

		return new Celestial(name, mass, x, y, vx, vy, vz, vz);
	}

	public static Celestial create(String name, double mass, double radius, double x, double y, double z, double vx, double vy, double vz) {

		Celestial p = new Celestial(name, mass, x, y, vx, vy, vz, vz);
		p.radius = radius;
		return p;
	}

}
