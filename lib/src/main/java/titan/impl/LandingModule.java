package titan.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import titan.StateInterface;
import titan.Vector3dInterface;
/*
 *  This class is responsible for displaying the probe on the landing frame and it also updates its coordinates.
 *  @author Group 12 
 */
public class LandingModule extends JPanel {

	private static final long serialVersionUID = 1L;
	private Point imageCorner;
	private ImageIcon icon;
	private StateInterface[] landingStatesPerTime;
	public static int delay = 25;

	public double size = 1;
	public static boolean stop = false;
	public int currentState = 0;

	private OpenLoopControllerNew openController;
	private ClosedLoopController feedbackController;

	public LandingModule(LandingFrame landing, StateInterface landingState) {

		// Source of image:
		// https://www.pexels.com/photo/fine-tip-on-black-surface-3934623/
		// Edited by the group so that the image is all black for a background
		icon = new ImageIcon(this.getClass().getResource("background.jpg"));
		imageCorner = new Point(0, 0);
		
		feedbackController = new ClosedLoopController(landingState);
		openController = new OpenLoopControllerNew(landingState);
		
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Repaint background
		icon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

		openController.draw(g, size, 10, 10);

		g.setColor(new Color(218, 165, 32));
		//g.drawLine(1025, 0, 1025, getHeight()); //open: around 1300 // feedback around 1025
		g.drawLine(720, 0, 720, getHeight()); //with new states: //open: around 1300 // feedback around 705
		g.fillRect(0, getHeight()-100, getWidth(), 100); // x, y, width, height
		
		g.setColor(new Color(255, 0, 0));
		g.fillRect(700, getHeight()-100-10, 40, 10);
	}
	
	private void landingLoop() {
		
		landingStatesPerTime = openController.getLandingStates();
		
		while (true) {
			if (!stop) {
				// update positions
				openController.update(((State) landingStatesPerTime[currentState]).getPosition()[0]);
				if (currentState == landingStatesPerTime.length - 1) {
					currentState = 0;
				}
			displayDistances((State)landingStatesPerTime[currentState]);	
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
	
	public void displayDistances(State array) {
			 System.out.println("Current distance " + array.getPosition()[0].dist(array.getPosition()[1]));
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
