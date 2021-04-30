package GUIFolder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SystemPlanet extends JPanel {

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
	Point imageCorner;
	Point prevPt;
	ImageIcon icon;

	private static int prevN = 0;
	private Dimension preferredSize = new Dimension(400, 400);

	public SystemPlanet(double speed) {
		
		
		// TODO
		// Source of image: 
		// setBackground(new Color(0, 0, 0));

		icon = new ImageIcon(this.getClass().getResource("InkedBlackBackground_LI.jpg"));
		img = icon.getImage();


		imageCorner = new Point(0, 0);
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();

		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);

		// mousWheely
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

		//Graphics2D g2 = (Graphics2D) g;
		//System.out.println("width " + getWidth() + " height " + getHeight());

		//g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);

		super.paintComponent(g);
		icon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

		for (Planet body : allPlanets) {
			body.draw(g, size, getWidth(), getHeight());
		}
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

	/*
	 * // PLEASE, DO NOT DELETE THIS, WORK IN PROGRESS! public void resetToMiddle()
	 * { int width2 = getWidth(); int height2 = getHeight();
	 * System.out.println("width " + width2 + " height " + height2);
	 * 
	 * for (Planet body : allPlanets) { body.setX((int) (body.getX() + (width -
	 * getWidth() ))); body.setY((int) (body.getY() + (height - getHeight() ))); }
	 * repaint();
	 * 
	 * } public void setHeight() { height = getHeight(); } public void setWidth() {
	 * width = getWidth(); }
	 */

	private class ClickListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			prevPt = e.getPoint();
		}
		public void mouseWheelMoved(MouseWheelEvent e) {
			
			updatePreferredSize(e.getWheelRotation(), e.getPoint());
		}

	}

	private class DragListener extends MouseMotionAdapter {
		
		public void mouseDragged(MouseEvent e) {

			Point currentPt = e.getPoint();

			imageCorner.translate(

					(int) (currentPt.getX() - prevPt.getX()), (int) (currentPt.getY() - prevPt.getY()));
			prevPt = currentPt;
			repaint();
		}

	}

	public void updatePreferredSize(int n, Point p) {

		if (n == 0) // ideally getWheelRotation() should never return 0.
			n = -1 * prevN; // but sometimes it returns 0 during changing of zoom
		// direction. so if we get 0 just reverse the direction.

		double d = (double) n * 1.08;
		d = (n > 0) ? 1 / d : -d;

		int w = (int) (getWidth() * d);
		int h = (int) (getHeight() * d);
		this.preferredSize.setSize(w, h);

		int offX = (int) (p.x * d) - p.x;
		int offY = (int) (p.y * d) - p.y;
		this.getParent().setLocation(getParent().getLocation().x - offX, getParent().getLocation().y - offY);
		// in the original code, zoomPanel is being shifted. here we are shifting
		// containerPanel

		this.getParent().doLayout(); // do the layout for containerPanel
		this.getParent().getParent().doLayout(); // do the layout for jf (JFrame)

		prevN = n;
		
		repaint();
		
	}

	public Dimension getPreferredSize() {
		return preferredSize;
	}

}
