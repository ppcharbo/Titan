package titan;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
//import titan.Planet;

public class PlanetComponent extends JComponent {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public class PlanetComponent extends JComponent {
			
			public void paintComponent (Graphics g) {
	        Graphics2D g2 = (Graphics2D) g;
	        Planet planet = new Planet(200, 300);
	        planet.draw(g2);
	    }
	}
}
