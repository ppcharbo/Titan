package titan.tests;

import titan.ProbeSimulatorInterface;
import titan.Vector3dInterface;
import titan.impl.ProbeSimulator;
import titan.impl.Vector3d;

import org.junit.Test;

//import com.sun.tools.javac.code.Attribute.Array;

import static org.junit.Assert.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.System;

public class ProbeSimulatorTestProvided {

    static final double ACCURACY = 1e5; // 1 meter (might need to tweak that)

    @Test
    public void testSearchPositionOneDayX() {
    	double x1 = -1.4218092965609787E11; // reference implementation
    	int step=100;
    	
    	  AdjustPosition adjustPosition = AdjustPosition.getInstance();
    	  int i=0;
    	while(true) {
    		i++;
        Vector3dInterface[] trajectory = simulateOneDayBis();
        double delta = x1-trajectory[1].getX();
        /*
		if (delta>0)
        	adjustPosition.add(step);
        else
        	adjustPosition.sub(step);
        	*/
		System.out.println("Delta "+trajectory[1].getX());
		if(i>20)break;
    	}
        

    }

    @Test
    public void testTrajectoryOneDayX() {
        Vector3dInterface[] trajectory = simulateOneDay();
        double x1 = -1.4218092965609787E11; // reference implementation
        assertEquals(x1, trajectory[1].getX(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    public void testTrajectoryOneDayY() {

        Vector3dInterface[] trajectory = simulateOneDay();
        double y1 = -3.3475191084301098E10; // reference implementation
        assertEquals(y1, trajectory[1].getY(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    public void testTrajectoryOneDayZ() {

        Vector3dInterface[] trajectory = simulateOneDay();
        double z1 = 8334994.892882561; // reference implementation
        assertEquals(z1, trajectory[1].getZ(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    public void testTrajectoryOneYearX() {

        Vector3dInterface[] trajectory = simulateOneYear();
        double x366 = -2.4951517995514418E13; // reference implementation
        assertEquals(x366, trajectory[366].getX(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    public void testTrajectoryOneYearY() {

        Vector3dInterface[] trajectory = simulateOneYear();
        double y366 = -1.794349344879982E12; // reference implementation
        assertEquals(y366, trajectory[366].getY(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    public void testTrajectoryOneYearZ() {

        Vector3dInterface[] trajectory = simulateOneYear();
        double z366 = 2.901591968932223E7; // reference implementation
        assertEquals(z366, trajectory[366].getZ(), ACCURACY); // delta +-ACCURACY

    }

    @Test
    public void testTrajectoryLength() {

        Vector3dInterface[] trajectory = simulateOneYear();
        try {
            FileWriter writer = new FileWriter("trajectory.csv");
            System.out.println("trajectory length: " + trajectory.length);
            String header = "day,x,y,z";
            System.out.println(header);
            writer.write(header + "\n");
            for(int i = 0; i < trajectory.length; i++) {
                String row = i + "," + trajectory[i].getX() + "," + trajectory[i].getY() + "," + trajectory[i].getZ();
                System.out.println(row);
                writer.write(row + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        assertEquals(367, trajectory.length);

    }
  
    public static Vector3dInterface[] simulateOneDay() {

      Vector3dInterface probe_relative_position = new Vector3d(6371e3,0,0);
      Vector3dInterface probe_relative_velocity = new Vector3d(52500.0,-27000.0,0); // 12.0 months
      double day = 24*60*60;
      ProbeSimulatorInterface simulator = new ProbeSimulator();
      Vector3dInterface[] trajectory = simulator.trajectory(probe_relative_position, probe_relative_velocity, day, day);
      return trajectory;

    }
   
    public static Vector3dInterface[] simulateOneYear() {

      Vector3dInterface probe_relative_position = new Vector3d(6371e3,0,0);
      Vector3dInterface probe_relative_velocity = new Vector3d(52500.0,-27000.0,0); // 12.0 months
      double day = 24*60*60;
      double year = 365.25*day;
      ProbeSimulatorInterface simulator = new ProbeSimulator();
      Vector3dInterface[] trajectory = simulator.trajectory(probe_relative_position, probe_relative_velocity, year, day);
      return trajectory;

    }
    
    public  Vector3dInterface[] simulateOneDayBis() {

        Vector3dInterface probe_relative_position1 = new Vector3d(6371e3,0,0);
        Vector3dInterface probe_relative_velocity1 = new Vector3d(52500.0,-27000.0,0); // 12.0 months
        double day1 = 24*60*60;
        ProbeSimulatorInterface simulator1 = new ProbeSimulator();
        Vector3dInterface[] trajectory1 = simulator1.trajectory(probe_relative_position1, probe_relative_velocity1, day1, day1);
        //System.out.println(Array.);
        return trajectory1;

      }
     

}
