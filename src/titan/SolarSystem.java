package titan;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
//import titan.Planet;

public class SolarSystem extends JComponent {

	private Image img;
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<Planet> solarSystem=new ArrayList<>();
	public SolarSystem() {
		 
		ImageIcon icon = new ImageIcon(this.getClass().getResource("background.jpg"));

		        // extract the image out of it

          img = icon.getImage();

	}
	public void paintComponent(Graphics g1) {
		Graphics2D g2 = (Graphics2D) g1;
		
		g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		
		for (Planet planet : solarSystem) {
			planet.draw(g2);	
		}
	 
		
	}
	
	 
	public void addPlanet(int x, int y, int width, int height, int r, int g, int b) {
		Planet planet = new Planet(x, y, width, height, r, g, b);
		solarSystem.add(planet);
		
	}
}
