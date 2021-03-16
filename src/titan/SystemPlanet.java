package titan;

public class SystemPlanet {
	public double G = 6.66667 * Math.pow(10, -11);// Gravity universally 
	public double earthMass = 5.97219e24;
	public double solarMass = 1.988500e30;
	public double mercuryMass = 3.302e23;
	public double venusMass = 4.8685e24;
	public double moonMass = 7.349e22;
	public double marsMass = 6.4171e23;
	public double jupiterMass = 1.89813e27;
	public double saturnMass = 5.6834e26;
	public double titanMass = 1.34553e23;
	public double uranusMass = 8.6813e25;
	public double neptuneMass = 1.02413e26;

	public Vector3dInterface sunPosition = new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06);
	public Vector3dInterface earthInitialPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	public Vector3dInterface earthActualPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	public Vector3dInterface mercuryActualPosition = new Vector3d(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09);
	public Vector3dInterface mercuryInitialPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	public Vector3dInterface VenusActualPosition = new Vector3d(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09);
	public Vector3dInterface VenusInitialPosition = new Vector3d(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09);
	public Vector3dInterface MoonInitialPosition = new Vector3d(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07);
	public Vector3dInterface MoonActualPosition = new Vector3d(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07);
	public Vector3dInterface MarsInitialPosition = new Vector3d(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09);
	public Vector3dInterface MarsActualPosition = new Vector3d(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09);
	public Vector3dInterface JupiterInitialPosition = new Vector3d(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08);
	public Vector3dInterface JupiterActualPosition = new Vector3d(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08);
	public Vector3dInterface SaturnInitialPosition = new Vector3d(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09);
	public Vector3dInterface SaturnActualPosition = new Vector3d(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09);
	public Vector3dInterface TitanInitialPosition = new Vector3d(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09);
	public Vector3dInterface TitanActualPosition = new Vector3d(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09);
	public Vector3dInterface UranusInitialPosition = new Vector3d(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10);
	public Vector3dInterface UranusActualPosition = new Vector3d(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10);
	public Vector3dInterface NeptuneInitialPosition = new Vector3d(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10);
	public Vector3dInterface NeptuneActualPosition = new Vector3d(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10);

	public Vector3dInterface[] trajectoryGeneric(Vector3dInterface p0, Vector3dInterface v0, double tf, double h, Vector3dInterface centerposition, Vector3dInterface positionMoving, double massCenter, double massMoving) {
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

			forceMagnietude = (G * massMoving * massCenter) / Math.pow((positionMoving.sub(centerposition).norm()), 2);
			force = (centerposition.sub(positionMoving));
			force = force.mul(forceMagnietude / force.norm());
			acce = force.mul(1 / massMoving);

			vitesse = acce.mul(h).add(vitesse);
			position = acce.mul(Math.pow(h, 2) / 2).add(vitesse.mul(h)).add(position);
			positions[i - 1] = position;

		}

		return positions;
	}

	public void changeTime(double tf, double h) {

		//trajectoryGeneric(earthInitialPosition, earthActualPosition, tf, h, sunPosition, mercuryPosition, tf, h);

	}

}
