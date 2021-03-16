package titan;

public class ProbeSimulator extends SystemPlanet implements ProbeSimulatorInterface  {
	public double G = 6.66667 * Math.pow(10, -11);// Gravity universally 
	public double earthMass = 5.97219 * Math.pow(10, 24);
	public double solarMass = 1.988500 * Math.pow(10, 30);
	public Vector3dInterface sunPosition = new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06);
	public Vector3dInterface earthInitialPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	public Vector3dInterface earthActualPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	
	/*
	* Simulate the solar system, including a probe fired from Earth at 00:00h on 1 April 2020.
	*
	* @param   p0      the starting position of the probe, relative to the earth's position.
	* @param   v0      the starting velocity of the probe, relative to the earth's velocity.
	* @param   ts      the times at which the states should be output, with ts[0] being the initial time.
	* @return  an array of size ts.length giving the position of the probe at each time stated, 
	*          taken relative to the Solar System barycentre.
	*/
	@Override
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
		int time = 0;
		int i = 0;
		Vector3dInterface vitesse = new Vector3d();
		vitesse = v0;
		Vector3dInterface[] positions = new Vector3dInterface[ts.length];
		Vector3dInterface position = new Vector3d();
		position = p0;
		Vector3dInterface force = new Vector3d();
		Vector3dInterface acce = new Vector3d();
		double forceMagnietude = 0;
		while (time < ts[ts.length - 1]) {
			i++;

			forceMagnietude = (G * earthMass * solarMass) / Math.pow((earthActualPosition.sub(sunPosition).norm()), 2);
			force = (sunPosition.sub(earthActualPosition));
			force = force.mul(forceMagnietude / force.norm());
			acce = force.mul(1 / earthMass);
			double timeStep = ts[i] - ts[i - 1];
			vitesse = acce.mul(timeStep).add(vitesse);
			position = acce.mul(Math.pow(timeStep, 2) / 2).add(vitesse.mul(timeStep)).add(position);
			positions[i - 1] = position;
			//	p[i]=1/2*(a*t^2)+v*t+p0;
		}

		return positions;
	}
	/*
	* Simulate the solar system with steps of an equal size.
	* The final step may have a smaller size, if the step-size does not exactly divide the solution time range.
	*
	* @param   tf      the final time of the evolution.
	* @param   h       the size of step to be taken
	* @return  an array of size round(tf/h)+1 giving the position of the probe at each time stated, 
	*          taken relative to the Solar System barycentre
	*/
// le temps homogene entre tf et h
	@Override
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {

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

			forceMagnietude = (G * earthMass * solarMass) / Math.pow((earthActualPosition.sub(sunPosition).norm()), 2);
			force = (sunPosition.sub(earthActualPosition));
			force = force.mul(forceMagnietude / force.norm());
			acce = force.mul(1 / earthMass);

			vitesse = acce.mul(h).add(vitesse);
			position = acce.mul(Math.pow(h, 2) / 2).add(vitesse.mul(h)).add(position);
			positions[i - 1] = position;

		}

		return positions;

	}
	}