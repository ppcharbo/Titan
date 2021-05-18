package titan.GUI;

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
import titan.GUI.ProbeSimulator;
import titan.GUI.Vector3d;
import titan.GUI.State;
import titan.StateInterface;

public class SystemPlanet extends JPanel {

	// TODO add the labels to the planets so we can identify them more easily :)
	// TODO plotting Titan and the earth's moon
	// TODO plot Rocket
	// -->
	// TODO change the coordinates according to the planet.java inside the
	// titan.impl

	private static final long serialVersionUID = 1L;
	private final boolean DEBUG = false;

	private ArrayList<PlanetGUI> allPlanets = new ArrayList<PlanetGUI>();
	private final GUIWelcome upperFrame;
	private Point imageCorner;
	private Point prevPt;
	private ImageIcon icon;
	private StateInterface[] solvedStates;

	public double size = 1;
	public static int delay = 25;
	public boolean stop = false;

	public SystemPlanet(GUIWelcome frame, double speed) {

		this.upperFrame = frame; // pass the GUIWelcome object because we need access to it.

		// Source of image:
		// https://www.pexels.com/photo/fine-tip-on-black-surface-3934623/
		// Edited by the group so that the image is all black for a background
		icon = new ImageIcon(this.getClass().getResource("background.jpg"));
		imageCorner = new Point(0, 0);

		// Create listeners
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();

		// add all the listeners
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseWheelListener(clickListener);
		
		
		solvedStates = simulateOneYear();
		
		
		// PlanetGUI(JPanel parento, String label, int r, int g, int b, double xCoordinate, double yCoordinate, int diameter)
		
		// TODO check values for the radius
		// TODO scale values
		allPlanets.add(new PlanetGUI(this, "SHIP", 255, 20, 147, ((State) solvedStates[0]).getPosition()[0], 10E6));
		allPlanets.add(new PlanetGUI(this, "SUN", 255, 140, 0, ((State) solvedStates[0]).getPosition()[1], 6.96E8));
		allPlanets.add(new PlanetGUI(this, "MOON", 192, 192, 192, ((State) solvedStates[0]).getPosition()[2], 1.74E-02));
		allPlanets.add(new PlanetGUI(this, "MERCURY", 128, 128, 128, ((State) solvedStates[0]).getPosition()[3], 2.4397E6));
		allPlanets.add(new PlanetGUI(this, "VENUS", 207, 153, 52, ((State) solvedStates[0]).getPosition()[4], 6.0518E6));
		allPlanets.add(new PlanetGUI(this, "EARTH", 0, 0, 255, ((State) solvedStates[0]).getPosition()[5], 6.37814E6));
		allPlanets.add(new PlanetGUI(this, "MARS", 255, 0, 0, ((State) solvedStates[0]).getPosition()[6], 3.3972E6));
		allPlanets.add(new PlanetGUI(this, "JUPITER", 255, 140, 0, ((State) solvedStates[0]).getPosition()[7], 7.1492E7));
		allPlanets.add(new PlanetGUI(this, "SATURN", 112, 128, 144, ((State) solvedStates[0]).getPosition()[8], 6.0268E7));
		allPlanets.add(new PlanetGUI(this, "URANUS", 196, 233, 238, ((State) solvedStates[0]).getPosition()[9], 2.5559E7));
		allPlanets.add(new PlanetGUI(this, "TITAN", 218, 165, 32, ((State) solvedStates[0]).getPosition()[10], 2575.5E3));
		allPlanets.add(new PlanetGUI(this, "NEPTUNE", 66, 98, 243, ((State) solvedStates[0]).getPosition()[11], 2.4746E7));
		
		/*
		allPlanets.add(new Planet(this, "mercury", 	128, 128, 128,		801, 381, 		8, 		3.89E-05, 2.98E-06,		9)); //done
		allPlanets.add(new Planet(this, "venus", 	207, 153, 52, 		706, 502, 		12, 	-1.73E-05, -3.07E-05, 	900)); //done
		allPlanets.add(new Planet(this, "earth", 	0, 0, 255, 			653, 421, 		11, 	5.43E-06, -2.93E-05, 	900)); //done
		allPlanets.add(new Planet(this, "mars", 	255, 0, 0, 			764, 232, 		7, 		2.48E-05, -1.82E-06, 	900)); //done
		allPlanets.add(new Planet(this, "jupiter", 	255, 140, 0, 		978, -306, 		20, 	1.26E-05, 3.62E-06, 	900)); //done
		allPlanets.add(new Planet(this, "saturn", 	112, 128, 144, 		1434, 1807, 	15, 	8.22E-06, 4.05E-06, 	900)); //done
		allPlanets.add(new Planet(this, "uranus", 	196, 233, 238, 		3196, 2193, 	15, 	-4.06E-06, 5.19E-06, 	900)); //done
		allPlanets.add(new Planet(this, "neptune", 	66, 98, 243, 		5183, -460, 	13, 	1.07E-06, 5.35E-06, 	900)); //done
		allPlanets.add(new Planet(this, "sun", 		255, 140, 0, 		800, 450,		30, 	-1.42E-08, -4.95E-09, 	1000)); //done
		*/
		
		
		/*
		 * //	Planet(double radius, double xPosition, double yPosition, String name)
		// listOfPlanets.add(new Planet(0, 6371000.0, 0, "SHIP"));
		// listOfPlanets.add(new Planet(6.96e8, -6.806783239281648e+08, 1.080005533878725e+09, "SUN"));
		// listOfPlanets.add(new Planet(3e8, -1.472343904597218e+11, -2.822578361503422e+10, "MOON"));
		// listOfPlanets.add(new Planet(2.4397e6, 6.047855986424127e+06, -6.801800047868888e+10, "MERCURY"));
		// listOfPlanets.add(new Planet(6.0518e6, -9.435345478592035e+10, 5.350359551033670e+10, "VENUS"));
		// listOfPlanets.add(new Planet(6.37814e6, -1.471922101663588e+11, -2.860995816266412e+10, "EARTH"));
		// listOfPlanets.add(new Planet(3.3972e6, -3.615638921529161e+10, -2.167633037046744e+11, "MARS"));
		// listOfPlanets.add(new Planet(7.1492e7, 1.781303138592153e+11, -7.551118436250277e+11, "JUPITER"));
		// listOfPlanets.add(new Planet(6.0268e7, 6.328646641500651e+11, -1.358172804527507e+12, "SATURN"));
		// listOfPlanets.add(new Planet(2.5559e7, 2.395195786685187e+12, 1.744450959214586e+12, "URANUS"));
		// listOfPlanets.add(new Planet(2575.5e3, 6.332873118527889e+11, -1.357175556995868e+12, "TITAN"));
		// listOfPlanets.add(new Planet(2.4746e7, 4.382692942729203e+12, -9.093501655486243e+11, "NEPTUNE"));
		 */
		



	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Repaint background
		icon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

		// Repaint planets
		for (PlanetGUI body : allPlanets) {
			body.draw(g, size, getWidth(), getHeight());
			if (body.label.equals("earth") && DEBUG) {
				System.out.println(body.getX());
				System.out.println(body.getY());
			}
		}
	}

	private void gameLoop() {
		int i = 0;
		while (true) { //&& i < solvedStates.length
			if (!stop) {
				// this is the SUN
				// we must update relative to the sun position
				for (int a = 0; a < 12; a++) {
					allPlanets.get(a).update(((State) solvedStates[i]).getPosition()[a]);
				}
				
				if(i == solvedStates.length-1) {
					i = 0;
				}
				
				i++;
			}

			repaint();

			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				//
			}
			System.out.println(i);

		}
	}
	public static StateInterface[] simulateOneYear() {

		Vector3dInterface probe_relative_position = new Vector3d(6371e3, 0, 0);
		Vector3dInterface probe_relative_velocity = new Vector3d(52500.0, -27000.0, 0); // 12.0 months
		double day = 24 * 60 * 60;
		double year = 365.25 * day;
		ProbeSimulator simulator = new ProbeSimulator();
		StateInterface[] states = simulator.trajectoryGUI(probe_relative_position, probe_relative_velocity, year, day);
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

			Point currentPt = e.getPoint(); // Obtain current clicked point

			// Get the current x and y position
			final double currentX = currentPt.getX();
			final double currentY = currentPt.getY();

			// Get the old x and y position (we got prevPt from the ClickListener class)
			final double oldX = prevPt.getX();
			final double oldY = prevPt.getY();

			// Move the planets
			for (PlanetGUI planet : allPlanets) {
				planet.translate((double) currentX - oldX, (double) currentY - oldY);
				if (DEBUG) {
					System.out.println(currentX);
					System.out.println(planet.getX());
					System.out.println(planet.label);
				}
			}

			prevPt = currentPt; // Reset points
			repaint();
		}
	}

}
