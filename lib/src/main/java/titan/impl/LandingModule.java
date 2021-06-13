package titan.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import titan.StateInterface;
import titan.Vector3dInterface;

public class LandingModule extends JPanel {

	private static final long serialVersionUID = 1L;
	private Point imageCorner;
	private ImageIcon icon;
	private StateInterface[] landingStatesPerTime;
	private static int delay = 25;

	public double size = 1;
	public boolean stop = false;
	public int currentState = 0;

	
	//private OpenLoopController landingModule = new OpenLoopController();
	private OpenLoopController openController;

	public LandingModule(LandingFrame landing, StateInterface landingState) {

		// Source of image:
		// https://www.pexels.com/photo/fine-tip-on-black-surface-3934623/
		// Edited by the group so that the image is all black for a background
		icon = new ImageIcon(this.getClass().getResource("background.jpg"));
		imageCorner = new Point(0, 0);

		openController = new OpenLoopController();
		openController.openLoopLandingSimulatorWRTU(landingState);

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Repaint background
		icon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

		openController.draw(g, size, 10, 10);

		g.setColor(new Color(218, 165, 32));
		g.fillRect(0, 700, 800, 100); // x, y, width, height
	}
	
	private void landingLoop() {
		
		landingStatesPerTime = openController.returnLandingStates();
		
		while (true) {
			if (!stop) {
				// update positions
				openController.update(((State) landingStatesPerTime[currentState]).getPosition()[0]);
				if (currentState == landingStatesPerTime.length - 1) {
					currentState = 0;
				}
				System.out.println(currentState);
			}
			currentState++;
			
			repaint();

			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				//
			}
		}
	}
	
	public void startLanding() {
		Thread thread = new Thread() {

			@Override
			public void run() {
				landingLoop();
			}
		};
		thread.start();
		
		
	}
}
