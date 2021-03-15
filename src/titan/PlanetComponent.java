package titan;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

import titan.Planet;
	
	public class PlanetComponent extends JComponent {
			
			public void paintComponent (Graphics g) {
	        Graphics2D g2 = (Graphics2D) g;
	        Planet planet = new Planet(800, 600);
	        planet.draw(g2);
	    }
	}
