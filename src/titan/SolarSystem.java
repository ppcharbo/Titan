package titan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
//import titan.Planet;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SolarSystem extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<Planet> solarSystem=new ArrayList<>();
	
	public void paintComponent(Graphics g1) {
		Graphics2D g2 = (Graphics2D) g1;
		 
	
		for (Planet planet : solarSystem) {
			planet.draw(g2);	
		}
		
	}
	public void addPlanet(int x, int y, int width, int height, int r, int g, int b) {
		Planet planet = new Planet(x, y, width, height, r, g, b);
		solarSystem.add(planet);
		
	}
}
