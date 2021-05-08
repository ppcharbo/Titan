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

		// frame is 1600 by 900 default
		// sun needs to move from 600 to 1600/2 = 800, DELTA = 800-600 = 200 --> so
		// everything 200 to the right!
		// sun needs to move from 400 to 450/2 = 450, DELTA = 450-400 = 050 --> so
		// everything 050 to the right!
		allPlanets.add(new Planet(this, "", 128, 128, 128, 800, 500, 8, -4.7, 0, 9));
		allPlanets.add(new Planet(this, "", 207, 153, 52, 952, 450, 12, 0, 2.5, 900));
		allPlanets.add(new Planet(this, "", 0, 0, 255, 800, 200, 11, 1.8, 0, 900));
		allPlanets.add(new Planet(this, "", 255, 0, 0, 850, 0, 7, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "", 255, 140, 0, 800, -50, 20, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "", 112, 128, 144, 800, -75, 15, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "", 196, 233, 238, 800, -125, 15, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "", 66, 98, 243, 0, 650, 13, 0, -1.2, 900));
		allPlanets.add(new Planet(this, "sun", 255, 140, 0, 800, 450, 30, 0.1, 0, 1000));
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
