package titan;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlanetScreen {
	
	public PlanetScreen() {
		JFrame planetFrame  = new JFrame();
		planetFrame.setSize(1600, 900);
		planetFrame.setTitle("Journey to Titan");
		planetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel panel = new JPanel(new GridBagLayout());
	    panel.setBackground(new Color(0, 0, 0));
	    
	    SunComponent component = new SunComponent();
	    
	    planetFrame.add(component);
	    planetFrame.setVisible(true);
	    
	}

	
    
    
}
