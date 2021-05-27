//Hoi Hoi
package titan.impl;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import titan.StateInterface;
import titan.Vector3dInterface;

public class SystemPlanet extends JPanel {

	private static final long serialVersionUID = 1L;
	private final boolean DEBUG = false;

	private ArrayList<PlanetGUI> allPlanets = new ArrayList<PlanetGUI>();
	private final GUIWelcome upperFrame;
	private Point imageCorner;
	private Point prevPt;
	private ImageIcon icon;
	private StateInterface[] solvedStates;

	public double size = 1; // public for zooming function
	public static int delay = 25; // public for decreasing/increasing speed
	public boolean stop = false; // public for pausing the simulation
	public int currentState = 0; // public for resetting the states

	public SystemPlanet(GUIWelcome frame) {

		this.upperFrame = frame; // pass the GUIWelcome object because we need access to it.

		// source of image:
		// https://www.pexels.com/photo/fine-tip-on-black-surface-3934623/
		// edited by the group so that the image is all black for a background
		icon = new ImageIcon(this.getClass().getResource("background.jpg"));
		imageCorner = new Point(0, 0);

		// create listeners
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();

		// add all the listeners for advanced functionality
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseWheelListener(clickListener);

		solvedStates = simulateOneYear(); // simulates the states so we can plot them
		doesItComeClose(); // prints least fly-by

		// PlanetGUI(JPanel parent, String label, int r, int g, int b, Vector3d position, double diameter)
		allPlanets.add(new PlanetGUI(this, "SHIP", 0, 255, 0, ((State) solvedStates[0]).getPosition()[0], 10));
		allPlanets.add(new PlanetGUI(this, "SUN", 255, 140, 0, ((State) solvedStates[0]).getPosition()[1], 50));
		allPlanets.add(new PlanetGUI(this, "MOON", 192, 192, 192, ((State) solvedStates[0]).getPosition()[2], 10));
		allPlanets.add(new PlanetGUI(this, "MERCURY", 128, 128, 128, ((State) solvedStates[0]).getPosition()[3], 10));
		allPlanets.add(new PlanetGUI(this, "VENUS", 207, 153, 52, ((State) solvedStates[0]).getPosition()[4], 20));
		allPlanets.add(new PlanetGUI(this, "EARTH", 0, 0, 255, ((State) solvedStates[0]).getPosition()[5], 20));
		allPlanets.add(new PlanetGUI(this, "MARS", 255, 0, 0, ((State) solvedStates[0]).getPosition()[6], 15));
		allPlanets.add(new PlanetGUI(this, "JUPITER", 255, 140, 0, ((State) solvedStates[0]).getPosition()[7], 45));
		allPlanets.add(new PlanetGUI(this, "SATURN", 112, 128, 144, ((State) solvedStates[0]).getPosition()[8], 42));
		allPlanets.add(new PlanetGUI(this, "URANUS", 196, 233, 238, ((State) solvedStates[0]).getPosition()[9], 50));
		allPlanets.add(new PlanetGUI(this, "TITAN", 218, 165, 32, ((State) solvedStates[0]).getPosition()[10], 10));
		allPlanets.add(new PlanetGUI(this, "NEPTUNE", 66, 98, 243, ((State) solvedStates[0]).getPosition()[11], 38));
	}

	private void doesItComeClose() {
		Vector3d[] shipPositions = new Vector3d[solvedStates.length];
		Vector3d[] titanPositions = new Vector3d[solvedStates.length];

		// save all the positions of the ship and titan
		for (int i = 0; i < solvedStates.length; i++) {
			shipPositions[i] = ((State) solvedStates[i]).getPosition()[0];
			titanPositions[i] = ((State) solvedStates[i]).getPosition()[10];
		}

		// make a loop to see if the titanPositions.dist(titanPositions) <= 300
		double max = Double.MAX_VALUE;
		for (Vector3dInterface shipVector : shipPositions) {
			for (Vector3dInterface titanVector : titanPositions) {
				if (Math.abs(shipVector.dist(titanVector)) <= 3E5) {
					System.out.println("We fully made it to Titan!");
				}
				if (Math.abs(shipVector.dist(titanVector)) < max) {
					max = Math.abs(shipVector.dist(titanVector));
				}
			}
		}
		System.out.println("Least fly-by");
		System.out.println(max);
	}

	public void paintComponent(Graphics g) {

		// repaint background
		icon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

		// repaint planets
		for (PlanetGUI body : allPlanets) {
			body.draw(g, size, getWidth(), getHeight());
		}
	}

	private void gameLoop() {

		while (true) {
			if (!stop) {
				
				// Reset the state if we reach the end
				if (currentState == solvedStates.length - 1) {
					currentState = 0;
				}
				
				// update planets to the next state
				for (int a = 0; a < 12; a++) {
					allPlanets.get(a).update(((State) solvedStates[currentState]).getPosition()[a]);
				}
				
				if(DEBUG) {
					// get ship's speed
					System.out.println(((State) solvedStates[currentState]).getVelocity()[0].norm());
				}

				// Do some changes to the ship so that we can see where it is near the end of
				// the trajectory
				
				if (currentState > (solvedStates.length-20) && currentState < (solvedStates.length-1)) {
					allPlanets.get(0).setColor(255, 255, 255);
					allPlanets.get(0).setDiameter(10);
					if (DEBUG) {
						System.out.println("X: " + allPlanets.get(0).getX());
						System.out.println("Y: " + allPlanets.get(0).getY());
						System.out.println("X_titan: " + allPlanets.get(10).getX());
						System.out.println("Y_titan: " + allPlanets.get(10).getY());
					}
				}
				currentState += 1;
			}

			repaint();
			if (DEBUG) {
				System.out.println("Simulating state: " + currentState);
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				//
			}


		}
	}

	private static StateInterface[] simulateOneYear() {
		// 350: 4.115732989682610E6, -4.863119819665272E6, 0.021179641570518E6
		// 300: 3.993756368515144E6, -4.963775939752052E6, 0.021897234604973E6
		// 278: 3.926620508447322e6, -5.017051486490201e6, 0.022062741157029e6
		// 200: 3.740731413563386e6, -5.157141498910596e6, 0.021472116668884e6
		// 150: 3.609867510498535E6, -5.249581360565903E6, 0.019826634766418E6

		/*
		 * 1.8966840399361339E9
		 * Vector3dInterface probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); row 133 speed 130E3
		 * Vector3dInterface probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752); row 367
		*/
		Vector3dInterface probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718);
		Vector3dInterface probe_pos = new Vector3d(4154116.78496650, -4830374.71365795, 20853.3573652752);

		// original step by examiners
		//double day = 24 * 60 * 60;
		//double year = 365.25 * day;
		
		// GUI2
		//double stepGUI2 = 60 * 60; // 1 hour
		//double finalGUI2 = 2*365.25 * 24 * 60 * 60; // 2 years
		
		// smaller step size
		// GUI3:
		double hour = 60 * 60;
		double year = 4.1 * 24 * hour;
		ProbeSimulatorEuler simulator = new ProbeSimulatorEuler();
		StateInterface[] states = simulator.trajectoryGUI(probe_pos, probe_vel, year, hour);

		return states;
	}

	public void startMe() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				gameLoop();
			}
		};
		thread.start();
	}

	private class ClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			// Obtain the point for the drag function
			prevPt = e.getPoint();
		}
		public void mouseWheelMoved(MouseWheelEvent e) {
			// Zoom in using the button
			if (e.getWheelRotation() < 0) {
				upperFrame.zoomIn.doClick();
			}
			// Zoom out using the button
			if (e.getWheelRotation() > 0) {
				upperFrame.zoomOut.doClick();
			}
		}
	}

	private class DragListener extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			Point currentPt = e.getPoint(); // obtain current clicked point

			// get the current x and y position
			final double currentX = currentPt.getX();
			final double currentY = currentPt.getY();

			// get the old x and y position (we got prevPt from the ClickListener class)
			final double oldX = prevPt.getX();
			final double oldY = prevPt.getY();

			// move the planets
			if (stop == true) {
				// if the GUI is paused, then executed this
				// this basically updates the drawing for the every planet by the difference in
				// mouse position
				for (PlanetGUI planet : allPlanets) {
					planet.translate((double) currentX - oldX, (double) currentY - oldY);
					if (DEBUG) {
						System.out.println("CurrentX: " + currentX);
						System.out.println("Coordinate of the planet: " + planet.getX());
						System.out.println("Name of the planet: " + planet.label);
					}
				}
			}
			else {
				// else the GUI is running (so planets are moving), then do this
				// calculate translation vector (difference in mouse position)
				// because everything is scaled by 1E9, we also move the translation by that
				Vector3d translate = new Vector3d((currentX - oldX) * 1E9, (currentY - oldY) * 1E9, 0);

				// then update all states by this vector
				for (int a = 0; a < solvedStates.length; a++) {

					// get current positions of the planet and create a new array of the translated
					// planets which is empty
					Vector3d[] positionsForEveryState = ((State) solvedStates[a]).getPosition();
					Vector3d[] positionsNew = new Vector3d[positionsForEveryState.length];

					for (int b = 0; b < positionsForEveryState.length; b++) {
						// set the translated place to the current place plus the translation
						positionsNew[b] = (Vector3d) positionsForEveryState[b].add(translate);
					}
				}
			}
			prevPt = currentPt; // Reset points
			repaint();
		}
	}
}
