package titan.impl;

//public class FuelCostProbe implements ProbeSimulatorInterface{
public class FuelCostProbe {

	private static final double MASS_EMPTY_CRAFT = 7.8e4;
	private static final double MASS_totalfuel=7.8e4; // assume.
	private static double Current_mass_fuel;

	//max thrust= 30 mN= 30000000 N=3e7 N
	private static double maxthrusty=3e7;//N

	private static double effective_exhaust_velocity=2e4;//m/s

	//Velocity
	private  Vector3d delta_velocity;
	private  Vector3d Post_velocity;
	private  Vector3d Current_velocity;
	private  Vector3d acceleration;

	private static double mass_flow_rate;
	private static double engineTime; //fuel burning time
	private static double fuelCost;
	private static double accleration_value; //the value of acceleration when engine turn on,  using accleration.norm()

	public FuelCostProbe(Vector3d current_velocity, Vector3d post_velocity) {//constructor
		this.Current_velocity=current_velocity;
		this.Post_velocity=post_velocity;
	}

	public static double massFlowrate() {

		mass_flow_rate=maxthrusty / effective_exhaust_velocity;

		return mass_flow_rate;
		//flowrate is also a constant 

	}

	public Vector3d changeVelocity(){

		//delta_velocity between engine on.
		delta_velocity=(Vector3d) Post_velocity.sub(Current_velocity);

		return delta_velocity;
	}

	
	 /**
	* first :calculate acceleration of the probe
	 * using M*a=Ve*mass flow rate
	 * M=Rocket mass
	 * Ve=exhuast velocity
	 * 
	 * a=Ve*mass flow rate/M
	 
	* Second: calculate engine time 
	  t=(V(t)-V(0))/a
	  t=delta(V)/a
	*/

	  public double calcTime(){

		accleration_value=effective_exhaust_velocity*massFlowrate()/MASS_EMPTY_CRAFT;

		engineTime=(changeVelocity().norm())/accleration_value;

		return engineTime;
	  }

	  /**
	   * calculate direction of accleration using Vector3d.
	   */

	  public Vector3d calAce(){

		  acceleration=(Vector3d)changeVelocity().mul(1/calcTime());

		  return acceleration;
	  }

	  public void Fuelcost(){
		  changeVelocity();
		  calcTime();

		  fuelCost=engineTime*mass_flow_rate;

		  Current_mass_fuel=MASS_totalfuel-fuelCost;

		  if(Current_mass_fuel<0){
			  System.out.println("NO FUEL LEFT,PLEASE TRY AGAIN");	
		  }

	  }

	  public double getFuelCost(){
		  return fuelCost;
	  }

	  
	  public static void main(String[] args){
		Vector3d v0=new Vector3d(1,1,1);
		Vector3d vt=new Vector3d(1000,1000,1000);

		FuelCostProbe f=new FuelCostProbe(v0,vt);
		f.Fuelcost();
		double fuelCost=f.getFuelCost();
		Vector3d accleration=f.calAce();

		System.out.println("Fuelcost: "+fuelCost+" kg");
		System.out.println("The accleration after thrust is :"+ accleration);
	}

}
