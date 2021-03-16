package titan;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PlanetScreen {

	public final int WIDTH = 1600;
	public final int HEIGHT = 900;
	
	
	public PlanetScreen() {
		JFrame planetFrame = new JFrame();
		
		planetFrame.setSize(WIDTH, HEIGHT);
		planetFrame.setTitle("Journey to Titan");
		planetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		
		// works but does not display planets
		//JLabel background = new JLabel(new ImageIcon(this.getClass().getResource("background.jpg"))); //source: https://www.pexels.com/photo/stars-1257860/
		//background.setBounds(0, 0, WIDTH, HEIGHT);
		//planetFrame.add(background);
		
		
		SolarSystem solarSystem = new SolarSystem();
		

		//planetFrame.getContentPane().add(new JLabel(new ImageIcon("background.jpg")));
		//planetFrame.getContentPane().add(solarSystem);

		
		solarSystem.addPlanet(50, 450, 100, 100, 255, 165, 0);
		solarSystem.addPlanet(150, 450, 50, 50, 169, 169, 169);
		
	
	//	planetFrame.getContentPane().add(new JPanelWithBackGround("background.jpg");

	
		solarSystem.addPlanet(50, 450, 100, 100, 255, 165, 0);
		solarSystem.addPlanet(150, 450, 50, 50, 169, 169, 169);
		//solarSystem. (new JLabel(new ImageIcon("background.jpg")));
		planetFrame.getContentPane().add(solarSystem);

		planetFrame.setVisible(true);
		planetFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				 
				solarSystem.repaint();
			}
		});
	}

}

	
