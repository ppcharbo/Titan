package titan.impl;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import titan.StateInterface;

public class LandingFrame extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public LandingFrame(StateInterface landingState) {
		
		
		JFrame landingFrame = new JFrame();
		
		landingFrame.setSize(800, 800);
		landingFrame.setTitle("Journey to Titan - Landing");
		landingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LandingModule landMode = new LandingModule(this, landingState);
		landingFrame.add(landMode);
		landMode.repaint();
		
		landMode.startLanding();
		landingFrame.setVisible(true);	
	}
}
