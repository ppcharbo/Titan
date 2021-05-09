package GUIFolder;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SystemPlanet extends JPanel {

	// TODO add the labels to the planets so we can identify them more easily :)
	// TODO plotting Titan and the earth's moon
	// TODO plot Rocket
	// -->
	// TODO change the coordinates according to the planet.java inside the
	// titan.impl

	private static final long serialVersionUID = 1L;
	private final boolean DEBUG = false;

	private ArrayList<Planet> allPlanets = new ArrayList<Planet>();
	private ArrayList<Rocket> greatRocket = new ArrayList<Rocket>();
	private final GUIWelcome upperFrame;
	private Point imageCorner;
	private Point prevPt;
	private ImageIcon icon;

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
		
		
		// Original plotting data:
		allPlanets.add(new Planet(this, "mercury", 128, 128, 128, 800, 500, 8, -4.7, 0, 9));
		allPlanets.add(new Planet(this, "venus", 207, 153, 52, 952, 450, 12, 0, 2.5, 900));
		allPlanets.add(new Planet(this, "earth", 0, 0, 255, 800, 200, 11, 1.8, 0, 900));
		allPlanets.add(new Planet(this, "mars", 255, 0, 0, 850, 0, 7, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "jupiter", 255, 140, 0, 800, -50, 20, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "saturn", 112, 128, 144, 800, -75, 15, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "uranus", 196, 233, 238, 800, -125, 15, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "neptune", 66, 98, 243, 0, 650, 13, 0, -1.2, 900));
		allPlanets.add(new Planet(this, "sun", 255, 140, 0, 800, 450, 30, 0.1, 0, 1000));
		greatRocket.add(new Rocket(this, speed, 20, 800, 200));
		
		
		/*
		//Modified position and velocity data:
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
		
		
		//Modified position, velocity and mass data:
		/*
		allPlanets.add(new Planet(this, "mercury", 	128, 128, 128,		801, 381, 		8, 		3.89E-05, 2.98E-06,		3.30E+00)); //done
		allPlanets.add(new Planet(this, "venus", 	207, 153, 52, 		706, 502, 		12, 	-1.73E-05, -3.07E-05, 	4.87E+01)); //done
		allPlanets.add(new Planet(this, "earth", 	0, 0, 255, 			653, 421, 		11, 	5.43E-06, -2.93E-05, 	5.97E+01)); //done
		allPlanets.add(new Planet(this, "mars", 	255, 0, 0, 			764, 232, 		7, 		2.48E-05, -1.82E-06, 	6.42E+01)); //done
		allPlanets.add(new Planet(this, "jupiter", 	255, 140, 0, 		978, -306, 		20, 	1.26E-05, 3.62E-06, 	1.90E+04)); //done
		allPlanets.add(new Planet(this, "saturn", 	112, 128, 144, 		1434, 1807, 	15, 	8.22E-06, 4.05E-06, 	5.68E+03)); //done
		allPlanets.add(new Planet(this, "uranus", 	196, 233, 238, 		3196, 2193, 	15, 	-4.06E-06, 5.19E-06, 	8.68E+02)); //done
		allPlanets.add(new Planet(this, "neptune", 	66, 98, 243, 		5183, -460, 	13, 	1.07E-06, 5.35E-06, 	1.02E+03)); //done
		allPlanets.add(new Planet(this, "sun", 		255, 140, 0, 		800, 450,		30, 	-1.42E-08, -4.95E-09, 	1.99E+07)); //done
		*/
		
		//Modified position data:
		/*
		allPlanets.add(new Planet(this, "mercury", 	128, 128, 128,		801, 381, 		8, 		-4.7, 0,	9)); //done
		allPlanets.add(new Planet(this, "venus", 	207, 153, 52, 		706, 502, 		12, 	0, 2.5, 	900)); //done
		allPlanets.add(new Planet(this, "earth", 	0, 0, 255, 			653, 421, 		11, 	1.8, 0, 	900)); //done
		allPlanets.add(new Planet(this, "mars", 	255, 0, 0, 			764, 232, 		7, 		1.2, 0, 	900)); //done
		allPlanets.add(new Planet(this, "jupiter", 	255, 140, 0, 		978, -306, 		20, 	1.2, 0, 	900)); //done
		allPlanets.add(new Planet(this, "saturn", 	112, 128, 144, 		1434, 1807, 	15, 	1.2, 0, 	900)); //done
		allPlanets.add(new Planet(this, "uranus", 	196, 233, 238, 		3196, 2193, 	15, 	1.2, 0, 	900)); //done
		allPlanets.add(new Planet(this, "neptune", 	66, 98, 243, 		5183, -460, 	13, 	0, -1.2, 	900)); //done
		allPlanets.add(new Planet(this, "sun", 		255, 140, 0, 		800, 450,		30, 	0.1, 0, 	1000)); //done
		*/
		
		greatRocket.add(new Rocket(this, speed, 20, 800, 200));
		


		Thread thread = new Thread() {

			@Override
			public void run() {
				gameLoop();
			}
		};

		thread.start();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Repaint background
		icon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

		// Repaint planets
		for (Planet body : allPlanets) {
			body.draw(g, size, getWidth(), getHeight());
			if(body.label.equals("earth") && DEBUG) {
				System.out.println(body.getX());
				System.out.println(body.getY());
			}
		}
		

		// Repaint rocket
		for (Rocket rocketo : greatRocket) {
			rocketo.draw(g, 5);
		}
	}

	private void gameLoop() {

		while (true) {
			if (!stop) {
				// this is the SUN
				// we must update relative to the sun position
				Planet sun = allPlanets.get(allPlanets.size() - 1);
				for (Planet planet : allPlanets) {
					if (planet != sun)
						planet.update(sun.getX(), sun.getY(), sun.getMass());
				}
				for (Rocket rocket : greatRocket)
					rocket.rocketMotion(sun.getX(), sun.getY());
			}
			repaint();

			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				//
			}
		}
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
			for (Planet planet : allPlanets) {
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
