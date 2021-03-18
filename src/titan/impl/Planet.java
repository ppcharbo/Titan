package titan.impl;

import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public enum Planet implements StateInterface {
	SUN(1.988500e30, 6.96e8, -6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06, -1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01),
	MOON(7.349e22, 3e8, -1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07, 4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01),
	MERCURY(3.303e+23, 2.4397e6, 6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09, 3.892585189044652e+04, 2.978342247012996e+03, -3.327964151414740e+03),
	VENUS(4.869e+24, 6.0518e6, -9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09, -1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04),
	EARTH(5.976e+24, 6.37814e6, -1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06, 5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01),
	MARS(6.421e+23, 3.3972e6, -3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09, 2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02),
	JUPITER(1.9e+27, 7.1492e7, 1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08, 1.255852555185220e+04, 3.622680192790968e+03, -2.958620380112444e+02),
	SATURN(5.688e+26, 6.0268e7, 6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09, 8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02),
	URANUS(8.686e+25, 2.5559e7, 2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10, -4.059468635313243e+03, 5.187467354884825e+03, 7.182516236837899e+01),
	TITAN(1.34553e23, 2575.5e3, 6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09, 3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02),
	NEPTUNE(1.024e+26, 2.4746e7, 4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10, 1.068410720964204e+03, 5.354959501569486e+03, -1.343918199987533e+02);

	public final double mass; // in kilograms
	public final double radius; // in meters
	public final double x;
	public final double y;
	public final double z;
	public final double vx;
	public final double vy;
	public final double vz;
	public     Vector3d position;
	public     Vector3d velocity;
	// universal gravitational constant  (m3 kg-1 s-2)
	public static final double G = 6.67300E-11;


	Planet(double mass, double radius, double x, double y, double z, double vx, double vy, double vz) {
		this.mass = mass;
		this.radius = radius;
		this.x = x;
		this.y = y;
		this.z = z;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		position= new Vector3d(x, y, z);
		velocity=new Vector3d(vx, vy, vz);

	}

	public Vector3dInterface gravitationalForce( ) {
		Vector3dInterface result=new Vector3d();
		
		for (Planet p : Planet.values()) 
			 if(p!=this)
			 {
				 Vector3dInterface N= this.position.sub(p.position);
				 double GMM=G *this.mass*p.mass;
				 double GMMdivNorm=GMM/Math.pow(N.norm(),3);
				 result.add(N.addMul(GMMdivNorm, N));
				 
			 }
		return result;
	}

	

	@Override
	public StateInterface addMul(double step, RateInterface rate) {
		Rate arate = (Rate) rate;
		position = (Vector3d) position.addMul(step,arate.speedy());
		return this;
	}
	
	
	public static void main(String[] args) {
		
		 Vector3d velocity2 = Planet.JUPITER.velocity;
		 Vector3d position = Planet.JUPITER.position;
		 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}