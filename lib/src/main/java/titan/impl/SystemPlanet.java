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

import titan.ProbeSimulatorInterface;
import titan.StateInterface;
import titan.Vector3dInterface;
/**
 * SystemPlanet will simulate the solarsystem for a trajectory (step and t_final)
 * @author Group 12
 */
public class SystemPlanet extends JPanel {

	private static final long serialVersionUID = 1L;
	private final static boolean DEBUG = false;

	private ArrayList<PlanetGUI> allPlanets = new ArrayList<PlanetGUI>(); //list of planets
	private final GUIWelcome upperFrame;
	private Point imageCorner;
	private Point prevPt;
	private ImageIcon icon;
	private StateInterface[] solvedStates;
	private int globalState = -1; //define the state where the minimum fly-by is
	private int globalStateJupiter = -1; //define the state where the minimum fly-by is for Jupiter
	private final boolean AUTO_CLOSE = true; 

	// we need the following to be public, because we need access to it in
	// the GUIWelcome class
	public double size = 1;
	public static int delay = 25;
	public boolean stop = false;
	public int currentState = 0; //starting from state = 0
	
	public static boolean euler = false;
	public static boolean rk4 = false;
	public static boolean verlet = false;
	
	public static boolean openController = false;
	public static boolean feedbackController = false;

	/**
	 * @param frame: we need the GUIWelcome class because if we scroll in and out
	 */
	public SystemPlanet(GUIWelcome frame) {

		this.upperFrame = frame;

		// Source of image:
		// https://www.pexels.com/photo/fine-tip-on-black-surface-3934623/
		// Edited by the group so that the image is all black for a background
		icon = new ImageIcon(this.getClass().getResource("background.jpg"));
		imageCorner = new Point(0, 0);

		// Create listeners
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();

		// Add all the listeners for advanced functionality
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseWheelListener(clickListener);

		// Simulates the states, so we can plot them
		solvedStates = simulateOneYear(); 
		
		// Calculating the minimal distance we get to Titan
		int titan = 10; //Titan is the 10th of the list
		System.out.println("Fly-by Titan");
		System.out.println(distanceToPlanet(titan));
		System.out.println();
		
		// Calculating the minimal distance we get to Jupuiter
		System.out.println("Fly by Jupiter");
		int jupiter = 7; //Titan is the 7th of the list
		System.out.println(distanceToPlanet(jupiter));

		// PlanetGUI(JPanel parent, String label, int r, int g, int b, Vector3d position, double d)
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
	
	public double distanceToPlanet(int planet) {
		Vector3d[] shipPositions = new Vector3d[solvedStates.length];
		Vector3d[] planetPositions = new Vector3d[solvedStates.length];

		for (int i = 0; i < solvedStates.length; i++) {
			shipPositions[i] = ((State) solvedStates[i]).getPosition()[0];
			planetPositions[i] = ((State) solvedStates[i]).getPosition()[planet];
		}

		double closestDistance = Double.MAX_VALUE;

		// loop over every state and calculate the minimal distance
		for (int j = 0; j < shipPositions.length; j++) {
			if (shipPositions[j].dist(planetPositions[j]) < closestDistance) {
				closestDistance = shipPositions[j].dist(planetPositions[j]);
				if(planet == 10) {
					globalState = j;
				}
				if(planet == 7) {
					globalStateJupiter = j;
				}
			}
		}

		if(DEBUG) {
			System.out.println("To check:");
			System.out.println(((State) solvedStates[globalStateJupiter]).getPosition()[0].dist(((State) solvedStates[globalStateJupiter]).getPosition()[7]));
		}
		
		return closestDistance;
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Repaint background
		icon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

		// Repaint planets
		for (PlanetGUI body : allPlanets) {
			body.draw(g, size, getWidth(), getHeight());
		}
	}

	private void gameLoop() {

		while (true) {
			if (!stop) {
				// update positions
				for (int a = 0; a < 12; a++) {
					allPlanets.get(a).update(((State) solvedStates[currentState]).getPosition()[a]);
				}
				if(DEBUG) {
					System.out.println("Ship's velocity:");
					System.out.println(((State) solvedStates[currentState]).getVelocity()[0].norm());
				}

				// Reset the state if we reach the end
				if (currentState == solvedStates.length - 1) {
					currentState = 0;
				}

				// Do some changes to the ship so that we can see
				// where it is near the end of the trajectory
				if (currentState > (solvedStates.length-20) && currentState < (solvedStates.length-1)) {
					allPlanets.get(0).setColor(255, 255, 255);
					allPlanets.get(0).setDiameter(10);
				}
				// also make sure to reset the color!
				else {
					allPlanets.get(0).setColor(0, 255, 0);
				}
				if(currentState == globalState)
				{
					new LandingFrame(globalState);
					if(AUTO_CLOSE) {
						upperFrame.welcomeFrame.dispose();
					}
					break;
				}
				currentState += 1;
			}
			

			repaint();

			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				//
			}
			if (DEBUG)
				System.out.println("Simulating state: " + currentState);

		}
	}

	public static StateInterface[] simulateOneYear() {
		
		/*
		 * 
		 * Fly-by Titan
		 * 5.036348713187294E10
		 * 
		 * Fly by Jupiter
		 * 3.818103778134106E10
		 * 4.115732989682610E6, -4.863119819665272E6, 0.021179641570518E6
		 */
		Vector3dInterface probe_pos = new Vector3d(4.115732989682610E6, -4.863119819665272E6, 0.021179641570518E6); // row 367
		Vector3dInterface probe_vel = new Vector3d(72684.6410404669, -107781.235228466, 385.083685268718); // row 133 with speed 130E3
	
		// GUI
		double stepGUI2 = 60 * 60; // 1 hour
		double finalGUI2 = 2*365.25 * 24 * 60 * 60; // 2 years
		
		// GUI2: smaller step size and final time ENGINE ON
		//double hour = 60 * 60;
		//double fourDays = 4.05 * 24 * hour;
		
		ProbeSimulatorRungeKutta simulator = new ProbeSimulatorRungeKutta();
		StateInterface[] states = simulator.trajectoryGUI(probe_pos, probe_vel, finalGUI2, stepGUI2);
		
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
			prevPt = e.getPoint(); // Obtain the point for the drag function
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
			Point currentPt = e.getPoint(); // Obtain current clicked point

			// Get the current x and y position
			final double currentX = currentPt.getX();
			final double currentY = currentPt.getY();

			// Get the old x and y position (we got prevPt from the ClickListener class)
			final double oldX = prevPt.getX();
			final double oldY = prevPt.getY();

			// Move the planets
			if (stop == true) {
				// If the GUI is paused, then executed this
				// This basically updates the drawing for the every planet by the difference in
				// mouse position
				for (PlanetGUI planet : allPlanets) {
					planet.translate((double) currentX - oldX, (double) currentY - oldY);
					if (DEBUG) {
						System.out.println("CurrentX: " + currentX);
						System.out.println("Coordinate of the planet: " + planet.getX());
						System.out.println("Name of the planet: " + planet.getLabel());
					}
				}
			}
			else {
				// else the GUI is running (so planets are moving), then do this
				// Calculate translation vector (difference in mouse position)
				// Because everything is scaled by 1E9, we also move the translation by that
				Vector3d translate = new Vector3d((currentX - oldX) * 1E9, (currentY - oldY) * 1E9, 0);

				// Then update all states by this vector
				for (int a = 0; a < solvedStates.length; a++) {

					// Get current positions of the planet and create a new array of the translated
					// planets which is empty
					Vector3d[] positionsForEveryState = ((State) solvedStates[a]).getPosition();
					Vector3d[] positionsNew = new Vector3d[positionsForEveryState.length];

					for (int b = 0; b < positionsForEveryState.length; b++) {
						// Set the translated place to the current place plus the translation
						positionsNew[b] = (Vector3d) positionsForEveryState[b].add(translate);
					}

					if (DEBUG) {
						System.out.println("State before translating: " + ((State) solvedStates[0]).getPosition()[0].getX());
					}
					((State) solvedStates[a]).setPosition(positionsNew); // Sets new positions from translation
					if (DEBUG) {
						System.out.println("State after translating: " + ((State) solvedStates[0]).getPosition()[0].getX());
					}
				}
			}

			prevPt = currentPt; // Reset points
			repaint();
		}
	}

}
