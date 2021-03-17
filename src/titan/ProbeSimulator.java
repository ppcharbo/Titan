package titan;

public class ProbeSimulator extends SystemPlanet implements ProbeSimulatorInterface  {
	public double G = 6.66667e-11;// Gravity universally 
	public double probeMass = 0;
	public double earthMass = 5.97219e24;
	public double solarMass = 1.988500e30;
	public Vector3dInterface probeInitialPositon = new Vector3d(0,0,0);
	public Vector3dInterface probeActualPosition = new Vector3d(0,0,0);
	public Vector3dInterface sunPosition = new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06);
	public Vector3dInterface earthInitialPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	public Vector3dInterface earthActualPosition = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
	public Vector3dInterface mercuryInitialPosition = new Vector3d(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09);
	public Vector3dInterface mercuryActualPosition = new Vector3d(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09);
	public Vector3dInterface venusActualPosition = new Vector3d(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09);
	public Vector3dInterface venusInitialPosition =  new Vector3d(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09);
	public Vector3dInterface moonInitialPosition = new Vector3d(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07);
	public Vector3dInterface moonActualPosition = new Vector3d(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07);
	public Vector3dInterface marsInitialPosition = new Vector3d(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09);
	public Vector3dInterface marsActualPosition = new Vector3d(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09);
	public Vector3dInterface jupiterInitialPosition = new Vector3d(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08);
	public Vector3dInterface jupiterActualPosition = new Vector3d(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08);
	public Vector3dInterface saturnInitialPosition = new Vector3d(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09);
	public Vector3dInterface saturnActualPosition = new Vector3d(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09);
	public Vector3dInterface titanInitialPosition = new Vector3d(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09);
	public Vector3dInterface titanActualPosition = new Vector3d(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09);
	public Vector3dInterface uranusInitialPosition = new Vector3d(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10);
	public Vector3dInterface uranusActualPosition = new Vector3d(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10);
	public Vector3dInterface neptuneInitialPosition = new Vector3d(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10);
	public Vector3dInterface neptuneActualPosition = new Vector3d(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10);

	
	private double earthForceMagnitude = 0;
	private double mercuryForceMagnitude = 0;
	private double venusForceMagnitude = 0;
	private double moonForceMagnitude = 0;
	private double marsForceMagnitude = 0;
	private double jupiterForceMagnitude = 0;
	private double saturnForceMagnitude = 0;
	private double titanForceMagnitude = 0;
	private double uranusForceMagnitude = 0;
	private double neptuneForceMagnitude = 0;
	private Vector3dInterface earthForce = new Vector3d();
	private Vector3dInterface mercuryForce = new Vector3d();
	private Vector3dInterface venusForce = new Vector3d();
	private Vector3dInterface moonForce = new Vector3d();
	private Vector3dInterface marsForce = new Vector3d();
	private Vector3dInterface jupiterForce = new Vector3d();
	private Vector3dInterface saturnForce = new Vector3d();
	private Vector3dInterface titanForce = new Vector3d();
	private Vector3dInterface uranusForce = new Vector3d();
	private Vector3dInterface neptuneForce = new Vector3d();

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
		Vector3dInterface force = new Vector3d();
		int time = 0;
		int i = 0;
		Vector3dInterface vitesse = new Vector3d();
		vitesse = v0;
		Vector3dInterface[] positions = new Vector3dInterface[ts.length];
		Vector3dInterface position = new Vector3d();
		position = p0;
		
		
		Vector3dInterface acce = new Vector3d();
		
		
		//double force = 0;
		while (time < ts[ts.length - 1]) {
			i++;

			
		
			//force of the earth on the probe
			earthForceMagnitude = (G * earthMass * probeMass) / Math.pow((earthActualPosition.sub(probeActualPosition).norm()), 2);
			earthForce = (probeActualPosition.sub(earthActualPosition));
			earthForce  = earthForce .mul(earthForceMagnitude / earthForce .norm());
			
			//force of mercury on the probe
			mercuryForceMagnitude = (G * mercuryMass * probeMass) / Math.pow((mercuryActualPosition.sub(probeActualPosition).norm()), 2);
			mercuryForce  = (probeActualPosition.sub(mercuryActualPosition));
			mercuryForce = mercuryForce.mul(mercuryForceMagnitude / mercuryForce.norm());
			
			
			//force of venus on the probe
			venusForceMagnitude = (G * venusMass * probeMass) / Math.pow((venusActualPosition.sub(probeActualPosition).norm()), 2);
			venusForce = (probeActualPosition.sub(venusActualPosition));
			venusForce = venusForce.mul(venusForceMagnitude / venusForce.norm());
			
			//force of the moon on the probe
			moonForceMagnitude = (G * moonMass * probeMass) / Math.pow((moonActualPosition.sub(probeActualPosition).norm()), 2);
			moonForce = (probeActualPosition.sub(moonActualPosition));
			moonForce = moonForce.mul(moonForceMagnitude / moonForce.norm());
			
			//force of mars on the probe
			marsForceMagnitude = (G * marsMass * probeMass) / Math.pow((marsActualPosition.sub(probeActualPosition).norm()), 2);
			marsForce = (probeActualPosition.sub(marsActualPosition));
			marsForce = marsForce.mul(marsForceMagnitude / marsForce.norm());
			
			//force of jupiter on the probe
			jupiterForceMagnitude = (G * jupiterMass * probeMass) / Math.pow((jupiterActualPosition.sub(probeActualPosition).norm()), 2);
			jupiterForce = (probeActualPosition.sub(jupiterActualPosition));
			jupiterForce = jupiterForce.mul(jupiterForceMagnitude / jupiterForce.norm());
			
			//force of saturn on the probe
			saturnForceMagnitude = (G * saturnMass * probeMass) / Math.pow((saturnActualPosition.sub(probeActualPosition).norm()), 2);
			saturnForce = (probeActualPosition.sub(saturnActualPosition));
			saturnForce = saturnForce.mul(saturnForceMagnitude / saturnForce.norm());
			
			//force of titan on the probe
			titanForceMagnitude = (G * titanMass * probeMass) / Math.pow((titanActualPosition.sub(probeActualPosition).norm()), 2);
			titanForce = (probeActualPosition.sub(titanActualPosition));
			titanForce = titanForce.mul(titanForceMagnitude / titanForce.norm());
			
			//force of uranus on the probe
			uranusForceMagnitude = (G * uranusMass * probeMass) / Math.pow((uranusActualPosition.sub(probeActualPosition).norm()), 2);
			uranusForce = (probeActualPosition.sub(uranusActualPosition));
			uranusForce = uranusForce.mul(uranusForceMagnitude / uranusForce.norm());
			
			//force of neptune on the probe
			neptuneForceMagnitude = (G * neptuneMass * probeMass) / Math.pow((neptuneActualPosition.sub(probeActualPosition).norm()), 2);
			neptuneForce = (probeActualPosition.sub(neptuneActualPosition));
			neptuneForce = neptuneForce.mul(neptuneForceMagnitude / neptuneForce.norm());
			
			force = earthForce.add(mercuryForce).add(venusForce).add(moonForce).add(marsForce).add(jupiterForce).add(saturnForce).add(titanForce).add(uranusForce).add(neptuneForce);
			acce = force.mul(1 / probeMass);
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
	*          // le temps homogene entre tf et h
	*/
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
		
		while (time < tf) {
			i++;
			time = time + h;

			//force of the earth on the probe
			earthForceMagnitude = (G * earthMass * probeMass) / Math.pow((earthActualPosition.sub(probeActualPosition).norm()), 2);
			earthForce = (probeActualPosition.sub(earthActualPosition));
			earthForce  = earthForce .mul(earthForceMagnitude / earthForce .norm());
			
			//force of mercury on the probe
			mercuryForceMagnitude = (G * mercuryMass * probeMass) / Math.pow((mercuryActualPosition.sub(probeActualPosition).norm()), 2);
			mercuryForce  = (probeActualPosition.sub(mercuryActualPosition));
			mercuryForce = mercuryForce.mul(mercuryForceMagnitude / mercuryForce.norm());
			
			
			//force of venus on the probe
			venusForceMagnitude = (G * venusMass * probeMass) / Math.pow((venusActualPosition.sub(probeActualPosition).norm()), 2);
			venusForce = (probeActualPosition.sub(venusActualPosition));
			venusForce = venusForce.mul(venusForceMagnitude / venusForce.norm());
			
			//force of the moon on the probe
			moonForceMagnitude = (G * moonMass * probeMass) / Math.pow((moonActualPosition.sub(probeActualPosition).norm()), 2);
			moonForce = (probeActualPosition.sub(moonActualPosition));
			moonForce = moonForce.mul(moonForceMagnitude / moonForce.norm());
			
			//force of mars on the probe
			marsForceMagnitude = (G * marsMass * probeMass) / Math.pow((marsActualPosition.sub(probeActualPosition).norm()), 2);
			marsForce = (probeActualPosition.sub(marsActualPosition));
			marsForce = marsForce.mul(marsForceMagnitude / marsForce.norm());
			
			//force of jupiter on the probe
			jupiterForceMagnitude = (G * jupiterMass * probeMass) / Math.pow((jupiterActualPosition.sub(probeActualPosition).norm()), 2);
			jupiterForce = (probeActualPosition.sub(jupiterActualPosition));
			jupiterForce = jupiterForce.mul(jupiterForceMagnitude / jupiterForce.norm());
			
			//force of saturn on the probe
			saturnForceMagnitude = (G * saturnMass * probeMass) / Math.pow((saturnActualPosition.sub(probeActualPosition).norm()), 2);
			saturnForce = (probeActualPosition.sub(saturnActualPosition));
			saturnForce = saturnForce.mul(saturnForceMagnitude / saturnForce.norm());
			
			//force of titan on the probe
			titanForceMagnitude = (G * titanMass * probeMass) / Math.pow((titanActualPosition.sub(probeActualPosition).norm()), 2);
			titanForce = (probeActualPosition.sub(titanActualPosition));
			titanForce = titanForce.mul(titanForceMagnitude / titanForce.norm());
			
			//force of uranus on the probe
			uranusForceMagnitude = (G * uranusMass * probeMass) / Math.pow((uranusActualPosition.sub(probeActualPosition).norm()), 2);
			uranusForce = (probeActualPosition.sub(uranusActualPosition));
			uranusForce = uranusForce.mul(uranusForceMagnitude / uranusForce.norm());
			
			//force of neptune on the probe
			neptuneForceMagnitude = (G * neptuneMass * probeMass) / Math.pow((neptuneActualPosition.sub(probeActualPosition).norm()), 2);
			neptuneForce = (probeActualPosition.sub(neptuneActualPosition));
			neptuneForce = neptuneForce.mul(neptuneForceMagnitude / neptuneForce.norm());
			
			
			force = earthForce.add(mercuryForce).add(venusForce).add(moonForce).add(marsForce).add(jupiterForce).add(saturnForce).add(titanForce).add(uranusForce).add(neptuneForce);
			acce = force.mul(1 / probeMass);
			vitesse = acce.mul(h).add(vitesse);
			position = acce.mul(Math.pow(h, 2) / 2).add(vitesse.mul(h)).add(position);
			positions[i - 1] = position;

		}

		return positions;

	}
	}