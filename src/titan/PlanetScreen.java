package titan;

import javax.swing.JFrame;

public class PlanetScreen {

	public PlanetScreen() {
		JFrame planetFrame = new JFrame();
		planetFrame.setSize(1600, 1600);
		planetFrame.setTitle("Journey to Titan");
		planetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SolarSystem solarSystem = new SolarSystem();
		solarSystem.addPlanet(50, 450, 100, 100, 255, 165, 0);
		solarSystem.addPlanet(150, 450, 50, 50, 169, 169, 169);
		planetFrame.getContentPane().add(solarSystem);
		planetFrame.setVisible(true);

	}

}
