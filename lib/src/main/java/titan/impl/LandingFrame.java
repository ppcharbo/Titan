package titan.impl;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import titan.StateInterface;

public class LandingFrame extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public LandingFrame(StateInterface landingState) {
		
		
		JFrame landingFrame = new JFrame();
		
		landingFrame.setSize(800, 800);
		landingFrame.setTitle("Journey to Titan - Landing");
		landingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LandingModule landMode = new LandingModule(this, landingState);


		
		// Toolbar for more functionality
		JToolBar toolbar = new JToolBar("Tools");

		// Buttons in toolbar
		JButton speedDecrease = new JButton("decrease");
		speedDecrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LandingModule.delay = LandingModule.delay + 5;
			}
		});
		JButton speedIncrease = new JButton("increase");
		speedIncrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (LandingModule.delay - 10 <= 0) {
					LandingModule.delay = 1; // systemPlanet.delay is very small
				}
				else {
					LandingModule.delay = LandingModule.delay - 10;
				}
			}
		});

		JButton pauseStart = new JButton("pause/start");
		pauseStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				landMode.stop = !(landMode.stop);
			}
		});

		JButton resetToInitialState = new JButton("reset");
		resetToInitialState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				landMode.currentState = 0;
				landMode.stop = true;
			}
		});

		// add to toolbar and add toolbar to frame
		toolbar.add(speedDecrease);
		toolbar.add(speedIncrease);
		toolbar.add(pauseStart);
		toolbar.add(resetToInitialState);
		landMode.add(toolbar);
		
		
		
		
		landingFrame.add(landMode);
		landMode.repaint();
		
		landMode.startLanding();
		landingFrame.setVisible(true);
	}
}
