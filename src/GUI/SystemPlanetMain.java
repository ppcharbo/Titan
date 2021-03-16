package GUI;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import example.CelestialBody;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

public class SystemPlanetMain extends JPanel {
	Model model;
	Planet[] Planet = new Planet[9];

	final static int DELAY = 500;
	double size = 1;
	BufferedImage[] imgs = new BufferedImage[9];

	boolean stop = false;
	int clicked = -1;
	private Image img;

	public SystemPlanetMain() {
		//source: https://www.pexels.com/photo/stars-1257860/
		ImageIcon icon = new ImageIcon(this.getClass().getResource("backgroundtest-fullsize.jpg"));

		// extract the image out of it
		img = icon.getImage();
		model = new Model();
		model.setPreferredSize(new Dimension(1200, 1200));
		add(model);

		Planet[0] = new Planet(128, 128, 128, 600, 450, 8, -4.7, 0, 9);
		Planet[1] = new Planet(207, 153, 52, 752, 400, 12, 0, 2.5, 900);
		Planet[2] = new Planet(0, 0, 255, 600, 150, 11, 1.8, 0, 900);
		Planet[3] = new Planet(255, 0, 0, 650, -50, 7, 1.2, 0, 900);
		Planet[4] = new Planet(255, 140, 0, 600, -100, 20, 1.2, 0, 900);
		Planet[5] = new Planet(112, 128, 144, 600, -150, 15, 1.2, 0, 900);
		Planet[6] = new Planet(196, 233, 238, 600, -175, 15, 1.2, 0, 900);
		Planet[7] = new Planet(66, 98, 243, 600, -175, 13, -1.2, 0, 900);
		Planet[8] = new Planet(255, 140, 0, 600, 400, 30, .1, 0, 1000);
		
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
		 * 
		 * Planet(int r, int g, int b, double xCoordinate, double yCoordinate, double diameter, double vx, double vy, double mass)
		 * CelestialBody(double x, double y, double xVelocity, double yVelocity, int bodyMass, int bodyDiameter, Color bodyColor, double bodySpeed)
		 *  celestialBodies[0] = new CelestialBody(600, 450, -4.7, 0, 9, 8, Color.GRAY, 1000); //Mercury
            celestialBodies[1] = new CelestialBody(752, 400, 0, 2.5, 900, 12, new Color(207,153,52), 1000); //Venus
            celestialBodies[2] = new CelestialBody(600, 150, 1.8, 0, 900, 11, Color.BLUE, 2000); //Earth
            celestialBodies[3] = new CelestialBody(650, -50, 1.2, 0, 900, 7, Color.RED, 2000); //Mars
            celestialBodies[4] = new CelestialBody(600, -100, 1.2, 0, 900, 20, new Color(255,140,0), 2000); //Jupiter
            celestialBodies[5] = new CelestialBody(600, -150, 1.2, 0, 900, 15, new Color(112,128,144), 2000); //Saturn
            celestialBodies[6] = new CelestialBody(600, -175, 1.2, 0, 900, 15, new Color(196,233,238), 2000); //Uranus
            celestialBodies[7] = new CelestialBody(0, 400, 0, -1.2, 900, 13, new Color(66, 98, 243), 2000);//Neptune
            
            celestialBodies[8] = new CelestialBody(600, 400, .1, 0, 1000, 30, Color.ORANGE, 0);//Sun
		 */

		setBackground(Color.BLACK);
		
		/*
		imgs[0] = getImage(""); // stackOverflow
		imgs[1] = getImage("");
		imgs[2] = getImage("");
		imgs[3] = getImage("");
		imgs[4] = getImage("");
		imgs[5] = getImage("");
		imgs[6] = getImage("");
		imgs[7] = getImage("");
		imgs[8] = getImage("");
		*/
		
		Thread thread = new Thread() {

			@Override
			public void run() {
				gameLoop();
			}
		};

		thread.start();
	}

	public static BufferedImage getImage(String ref) { // loading the image
		BufferedImage bimg = null;
		try {

			bimg = ImageIO.read(new File(ref));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bimg;
	}

	private void gameLoop() {

		while (true) {
			if (!stop) {
				for (int i = 0; i < Planet.length - 1; i++) {
					Planet[i].updatePlanet(Planet[8].getX(), Planet[8].getY(), Planet[8].getMass());
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

			for (Planet body : Planet)
				body.drawPlanet(g, size);

			for (int i = 0; i < Planet.length; i++) {
				if (Planet[i].isVisible())
					Planet[i].dispDesc(g, size);
			}
			/*
			 * if (clicked > -1) { g.drawImage(imgs[clicked],0,0,200,200,Color.WHITE,null);
			 * g.setFont(new Font("Arial", Font.PLAIN, 20)); g.setColor(Color.WHITE);
			 * for(int i = 0; i < description[clicked].length; i++) {
			 * g.drawString(description[clicked][i], 0, 210+i*30); } }
			 */

			Planet[0].dispDesc(g, size);
			Planet[1].dispDesc(g, size);
			Planet[2].dispDesc(g, size);
			Planet[3].dispDesc(g, size);
			Planet[4].dispDesc(g, size);
			Planet[5].dispDesc(g, size);
			Planet[6].dispDesc(g, size);
			Planet[7].dispDesc(g, size);
			Planet[8].dispDesc(g, size);

		}

		public void keyTyped(KeyEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {
			for (int i = 0; i < Planet.length; i++)
				if (Planet[i].collision(e.getX(), e.getY(), size)) {

					Planet[i].visibalityChange(!Planet[i].isVisible());
					if (Planet[i].isVisible()) {
						clicked = i;
					} else {
						clicked = -1;
					}
				}
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
