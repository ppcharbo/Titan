package GUIFolder;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SystemPlanetMain extends JPanel {
	Model model;
	Planet[] celestialBodies = new Planet[9];

	final static int DELAY = 5;
	double size = 1;

	boolean stop = false;
	int clicked = -1;

	public SystemPlanetMain() {
		//source: https://www.pexels.com/photo/stars-1257860/
		//ImageIcon icon = new ImageIcon(this.getClass().getResource("backgroundtest-fullsize.jpg"));

		// extract the image out of it
		model = new Model();
		model.setPreferredSize(new Dimension(1200, 1200));
		add(model);


		celestialBodies[0] = new Planet(128,	128,	128,	600,	450,	 8,	   -4.7,	  0,	9);
		celestialBodies[1] = new Planet(207,	153,	 52,	752,	400,	12,		  0,	2.5, 	900);
		celestialBodies[2] = new Planet(0,		  0,	255,	600,	150,	11,		1.8,      0,	900);
		celestialBodies[3] = new Planet(255,	  0,	  0,	650,	-50,	 7,		1.2,	  0,	900);
		celestialBodies[4] = new Planet(255,	140,	  0,	600,   -100,    20,		1.2,	  0,	900);
		celestialBodies[5] = new Planet(112,	128,	144,	600,   -150,	15,		1.2,	  0,	900);
		celestialBodies[6] = new Planet(196,	233,	238,	600,   -175,	15,		1.2,	  0,	900);
		celestialBodies[7] = new Planet(66,		 98,	243,	0,		400,	13,		0,	  -1.2,		900);
		
		celestialBodies[8] = new Planet(255, 140, 0, 600, 400, 30, .1, 0, 1000);
		
		/* becomes
		 * 128, 128, 128, 600, 450, 8, -4.7, 0, 9 //Mecury
		 * 207, 153, 52, 752, 400, 12, 0, 2.5, 900 //Venus
		 * 0, 0, 255, 600, 150, 11, 1.8, 0, 900 //Earth
		 * 255, 0, 0, 650, -50, 7, 1.2, 0, 900 //Mars
		 * 255, 140, 0, 600, -100, 20, 1.2, 0, 900 //Jupiter
		 * 112, 128, 144, 600, -150, 15, 1.2, 0, 900 //Saturn
		 * 196, 233, 238, 600, -175, 15, 1.2, 0, 900 //Uranus
		 * 66, 98, 243, 600, -175, 13, -1.2, 0, 900 //Neptune
		 * 255, 140, 0, 600, 400, 30, .1, 0, 1000 //Sun
		 * */
		//Planet(int r, int g, int b, double xCoordinate, double yCoordinate, double diameter, double vx, double vy, double mass)
		//CelestialBody(double x, double y, double xVelocity, double yVelocity, int bodyMass, int bodyDiameter, Color bodyColor, double bodySpeed)
		/*
		celestialBodies[0] = new CelestialBody(600, 450, -4.7, 0, 9, 8, Color.GRAY, 1000); //Mercury
            celestialBodies[1] = new CelestialBody(752, 400, 0, 2.5, 900, 12, new Color(207,153,52), 1000); //Venus
            celestialBodies[2] = new CelestialBody(600, 150, 1.8, 0, 900, 11, Color.BLUE, 2000); //Earth
            celestialBodies[3] = new CelestialBody(650, -50, 1.2, 0, 900, 7, Color.RED, 2000); //Mars
            celestialBodies[4] = new CelestialBody(600, -100, 1.2, 0, 900, 20, new Color(255,140,0), 2000); //Jupiter
            celestialBodies[5] = new CelestialBody(600, -150, 1.2, 0, 900, 15, new Color(112,128,144), 2000); //Saturn
            celestialBodies[6] = new CelestialBody(600, -175, 1.2, 0, 900, 15, new Color(196,233,238), 2000); //Uranus
            celestialBodies[7] = new CelestialBody(0, 400, 0, -1.2, 900, 13, new Color(66, 98, 243), 2000);//Neptune
            
            celestialBodies[8] = new CelestialBody(600, 400, .1, 0, 1000, 30, Color.ORANGE, 0);//Sun
		*/



		/* OLD constructor with original values, but change of ObjectName
		celestialBodies[0] = new Planet(600, 450, -4.7, 0, 9, 8, Color.GRAY); //Mercury
		celestialBodies[1] = new Planet(752, 400, 0, 2.5, 900, 12, new Color(207,153,52)); //Venus
		celestialBodies[2] = new Planet(600, 150, 1.8, 0, 900, 11, Color.BLUE); //Earth
		celestialBodies[3] = new Planet(650, -50, 1.2, 0, 900, 7, Color.RED); //Mars
		celestialBodies[4] = new Planet(600, -100, 1.2, 0, 900, 20, new Color(255,140,0)); //Jupiter
		celestialBodies[5] = new Planet(600, -150, 1.2, 0, 900, 15, new Color(112,128,144)); //Saturn
		celestialBodies[6] = new Planet(600, -175, 1.2, 0, 900, 15, new Color(196,233,238)); //Uranus
		celestialBodies[7] = new Planet(0, 400, 0, -1.2, 900, 13, new Color(66, 98, 243));//Neptune
		celestialBodies[8] = new Planet(600, 400, .1, 0, 1000, 30, Color.ORANGE);//Sun
		*/



		setBackground(Color.BLACK);
		
		Thread thread = new Thread() {

			@Override
			public void run() {
				gameLoop();
			}
		};

		thread.start();
	}

	private void gameLoop() {

		while (true) {
			if (!stop) {
				for (int i = 0; i < celestialBodies.length - 1; i++) {
					celestialBodies[i].update(celestialBodies[8].getXPosition(), celestialBodies[8].getYPosition(), celestialBodies[8].getMass());
				}
			}
			repaint();

			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException ex) {
			}
		}
	}

	class Model extends JPanel implements KeyListener, MouseListener {
		public Model() {

			setFocusable(true);
			requestFocus();
			addKeyListener(this);
			addMouseListener(this);
		}

		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			//g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);

			for (Planet body : celestialBodies)
				body.draw(g, size);
		}

		public void keyTyped(KeyEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mouseClicked(MouseEvent e) {

		}

		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS)
				size += .1;

			if (e.getKeyCode() == KeyEvent.VK_MINUS && size > 0)
				size -= .1;

			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				stop = !stop;
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}

		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("The Unknown Space, the road to Titan");
				SystemPlanetMain sys = new SystemPlanetMain();
				frame.setContentPane(new SystemPlanetMain());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {

						sys.repaint();
					}
				});
				frame.setVisible(true);

			}
		});
	}
}