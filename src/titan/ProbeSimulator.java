package titan;

public class ProbeSimulator implements ProbeSimulatorInterface {

	@Override
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
		
		Vector3dInterface[] positions = new Vector3dInterface[ts.length];
		
		for(int i = 0; i < ts.length; i++) {
			positions[i] = p0.addMul(ts[i], v0);
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
	@Override
	public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
		
		Vector3dInterface[] positions = new Vector3dInterface[(int) ((tf/h)+1)];
		
		for(int i = 0; i <= tf; i++) {
			positions[i] = p0.addMul(h, v0);
		}
		
		return positions;
	}

}
