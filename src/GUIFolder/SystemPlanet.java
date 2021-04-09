package GUIFolder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SystemPlanet extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Planet> allPlanets = new ArrayList<Planet>();
	ArrayList<Rocket> greatRocket = new ArrayList<Rocket>();
	private Image img;

	public static int delay = 25;
	double size = 1;

	boolean stop = false;
	int clicked = -1;

	int width;
	int height;

	public SystemPlanet(int speed) {
		// Source of image: https://www.pexels.com/photo/starry-sky-998641/
		ImageIcon icon = new ImageIcon(this.getClass().getResource("background.jpg"));
		img = icon.getImage();
		

		// frame is 1600 by 900 default
		// sun needs to move from 600 to 1600/2 = 800, DELTA = 800-600 = 200 --> so
		// everything 200 to the right!
		// sun needs to move from 400 to 450/2 = 450, DELTA = 450-400 = 050 --> so
		// everything 050 to the right!
		allPlanets.add(new Planet(this, "", 128, 128, 128, 800, 500, 8, -4.7, 0, 9));
		allPlanets.add(new Planet(this, "", 207, 153, 52, 952, 450, 12, 0, 2.5, 900));
		allPlanets.add(new Planet(this, "", 0, 0, 255, 800, 200, 11, 1.8, 0, 900)); //earth
		allPlanets.add(new Planet(this, "", 255, 0, 0, 850, 0, 7, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "", 255, 140, 0, 800, -50, 20, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "", 112, 128, 144, 800, -75, 15, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "", 196, 233, 238, 800, -125, 15, 1.2, 0, 900));
		allPlanets.add(new Planet(this, "", 66, 98, 243, 0, 650, 13, 0, -1.2, 900));
		//allPlanets.add(new Planet(this, "", 40, 137, 234, 800, 200, 5, 1.8, 0, 900)); //earth imposter
		allPlanets.add(new Planet(this, "sun", 255, 140, 0, 800, 450, 30, .1, 0, 1000));
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

		Graphics2D g2 = (Graphics2D) g;
		int width2 = getWidth();
		int height2 = getHeight();
		// System.out.println("width " + width2 + " height " + height2);

		g2.drawImage(img, 0, 0, width2, height2, this);

		for (Planet body : allPlanets)
			// body.draw(g, size);
			body.draw(g, size, getWidth(), getHeight());
		for(Rocket rocketo : greatRocket) {
			rocketo.drawRocket(g, 5);
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
				for(Rocket rocket : greatRocket)
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

	/*
	 * PLEASE, DO NOT DELETE THIS, WORK IN PROGRESS!
	 * 
	 * public void resetToMiddle() { int width2 = getWidth(); int height2 =
	 * getHeight(); //System.out.println("width  " + width2 + " height  " +
	 * height2);
	 * 
	 * setWidth(width2); setHeight(height2);
	 * 
	 * for(Planet body : systemOfPlanets) { body.setXPosition(body.getXPosition()+
	 * getWidth()/2); } for(Planet body : systemOfPlanets) {
	 * body.setYPosition(body.getYPosition()+ getHeight()/2); }
	 * 
	 * }
	 * 
	 * public void setWidth(int w) { width = w; } public void setHeight(int h) {
	 * height = h; }
	 * 
	 */

}
