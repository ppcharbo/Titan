package titan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlanetScreen {
	
	public PlanetScreen() {
		JFrame planetFrame  = new JFrame();
		planetFrame.setSize(1600, 1600);
		planetFrame.setTitle("Journey to Titan");
		planetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel panel = new JPanel(); //new BorderLayout()
	    //panel.setBackground(new Color(0, 0, 0));
	    
	    //xLT, yLT, width, height, RGB code
	    PlanetComponent sun = new PlanetComponent(50, 450, 100, 100, 255, 165, 0);
	    PlanetComponent mercury = new PlanetComponent(150, 450, 50, 50, 169, 169, 169);
	    
	    //panel.add(sun);
		//panel.add(mercury);
	    sun.setVisible(true);
	    //push
	    mercury.setVisible(true);
	    planetFrame.add(sun);
	    planetFrame.add(mercury);
	    //panel.setVisible(true);
	    //planetFrame.add(panel);
	    
	    planetFrame.setVisible(true);
	    
	}

	
    
    
}
