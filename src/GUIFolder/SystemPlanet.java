package GUIFolder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SystemPlanet extends JPanel {
	Model model;
	Planet[] celestialBodies = new Planet[9];
	private Image img;

	final static int DELAY = 5;
	double size = 1;

	boolean stop = false;
	int clicked = -1;

	public SystemPlanet() {
		ImageIcon icon = new ImageIcon(this.getClass().getResource("background.jpg")); //https://www.pexels.com/photo/starry-sky-998641/

		img = icon.getImage();
		// extract the image out of it
		model = new Model();
		model.setPreferredSize(new Dimension(1200, 1200));
		add(model);

		celestialBodies[0] = new Planet(128, 128, 128, 600, 450, 8, -4.7, 0, 9);
		celestialBodies[1] = new Planet(207, 153, 52, 752, 400, 12, 0, 2.5, 900);
		celestialBodies[2] = new Planet(0, 0, 255, 600, 150, 11, 1.8, 0, 900);
		celestialBodies[3] = new Planet(255, 0, 0, 650, -50, 7, 1.2, 0, 900);
		celestialBodies[4] = new Planet(255, 140, 0, 600, -100, 20, 1.2, 0, 900);
		celestialBodies[5] = new Planet(112, 128, 144, 600, -150, 15, 1.2, 0, 900);
		celestialBodies[6] = new Planet(196, 233, 238, 600, -175, 15, 1.2, 0, 900);
		celestialBodies[7] = new Planet(66, 98, 243, 0, 400, 13, 0, -1.2, 900);

		celestialBodies[8] = new Planet(255, 140, 0, 600, 400, 30, .1, 0, 1000);

		//setBackground(Color.BLACK);

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
			g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);

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

}