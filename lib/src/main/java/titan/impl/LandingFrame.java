package titan.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import titan.StateInterface;
import titan.Vector3dInterface;


/**
 * Class to display the frame in for the landing
 * @author Group 12
 */
public class LandingFrame extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public LandingFrame(int state) {
		
		StateInterface[] simulations = simulateTime();
		StateInterface landingState = simulations[state];
		JFrame landingFrame = new JFrame();
		
		landingFrame.setSize(1600, 900);
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
	
	/**
	 * Method from the SystemPlanet class
	 * @return states: array of StateInterfaces describing the states to go to Titan from Earth
	 */
	public static StateInterface[] simulateTime() {
		Vector3dInterface probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752); // row 367
		Vector3dInterface probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); // row 133 with speed 130E3
		
		double stepGUI2 = 60 * 60; // 1 hour
		double finalGUI2 = 2*365.25 * 24 * 60 * 60; // 2 years
		
		ProbeSimulatorEuler simulator = new ProbeSimulatorEuler();
		StateInterface[] states = simulator.trajectoryGUI(probe_pos, probe_vel, finalGUI2, stepGUI2);
		
		return states;
	}
}
