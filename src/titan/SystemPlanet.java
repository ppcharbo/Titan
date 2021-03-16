package titan;

public class SystemPlanet {
	public double G = 6.66667 * Math.pow(10, -11);// Gravity universally 
	public double earthMass = 5.97219 * Math.pow(10, 24);
	public double solarMass = 1.988500 * Math.pow(10, 30);
	public double mercuryMass = 3.302e23;

	public Vector3dInterface sunPosition = new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06);
	public Vector3dInterface earthInitialPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	public Vector3dInterface earthActualPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	public Vector3dInterface mercuryActualPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);

	public Vector3dInterface[] trajectoryGeneric(Vector3dInterface p0, Vector3dInterface v0, double tf, double h, Vector3dInterface centerposition, Vector3dInterface positionMoving, double massCerter, double massMoving) {
		double time = 0;
		int i = 0;
		Vector3dInterface vitesse = new Vector3d();
		vitesse = v0;
		Vector3dInterface[] positions = new Vector3dInterface[(int) (tf / h) + 1];
		Vector3dInterface position = new Vector3d();
		position = p0;
		Vector3dInterface force = new Vector3d();
		Vector3dInterface acce = new Vector3d();
		double forceMagnietude = 0;
		while (time < tf) {
			i++;
			time = time + h;

			forceMagnietude = (G * massMoving * massCerter) / Math.pow((positionMoving.sub(centerposition).norm()), 2);
			force = (centerposition.sub(positionMoving));
			force = force.mul(forceMagnietude / force.norm());
			acce = force.mul(1 / massMoving);

			vitesse = acce.mul(h).add(vitesse);
			position = acce.mul(Math.pow(h, 2) / 2).add(vitesse.mul(h)).add(position);
			positions[i - 1] = position;

		}

		return positions;
	}
	public void changeTime(double tf,double h) {
		
		//trajectoryGeneric(earthInitialPosition, earthActualPosition, tf, h, sunPosition, mercuryPosition, tf, h);
		
	}

}
