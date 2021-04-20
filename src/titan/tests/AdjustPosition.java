package titan.tests;

import titan.Vector3dInterface;
import titan.impl.Vector3d;

public class AdjustPosition {

	private float xPosition=0;
	private static AdjustPosition adjustPosition = null;

	public static AdjustPosition getInstance() {
		if (adjustPosition == null)
			adjustPosition = new AdjustPosition();
		return adjustPosition;
	}
	public float getxPosition() {
		return xPosition;
	}
	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}
	public float add(int step) {
		
		xPosition= xPosition+step;
		return xPosition;
	}
	public float sub(int step) {
		xPosition= xPosition-step;
		return xPosition;
	}
	public Vector3dInterface getVector() {
		 
		return new Vector3d(xPosition, 0, 0);
	}
	
}
