package GUIFolder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SystemPlanet extends JPanel {
	ArrayList<Planet> celestialBodies = new ArrayList<Planet>();
	private Image img;

	final static int DELAY = 5;
	double size = 1;

	boolean stop = false;
	int clicked = -1;

	public SystemPlanet() {
		ImageIcon icon = new ImageIcon(this.getClass().getResource("background.jpg")); //https://www.pexels.com/photo/starry-sky-998641/

		img = icon.getImage();

 
		celestialBodies.add(new Planet(this,"",128, 128, 128, 600, 450, 8, -4.7, 0, 9));
		celestialBodies.add(new Planet(this,"",207, 153, 52, 752, 400, 12, 0, 2.5, 900));
		celestialBodies.add(new Planet(this,"",0, 0, 255, 600, 150, 11, 1.8, 0, 900));
		celestialBodies.add(new Planet(this,"",255, 0, 0, 650, -50, 7, 1.2, 0, 900));
		celestialBodies.add(new Planet(this,"",255, 140, 0, 600, -100, 20, 1.2, 0, 900));
		celestialBodies.add(new Planet(this,"",112, 128, 144, 600, -150, 15, 1.2, 0, 900));
		celestialBodies.add(new Planet(this,"",196, 233, 238, 600, -175, 15, 1.2, 0, 900));
		celestialBodies.add(new Planet(this,"",66, 98, 243, 0, 400, 13, 0, -1.2, 900));
		celestialBodies.add(new Planet(this,"sun",255, 140, 0, 600, 400, 30, .1, 0, 1000));
 
		//setBackground(Color.BLACK);

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
	//	System.out.println("width  " + width2 + " height  " + height2);

		g2.drawImage(img, 0, 0, width2, height2, this);

		for (Planet body : celestialBodies)
		//	body.draw(g, size);
			body.draw(g, size,getWidth(),getHeight());
	}

	private void gameLoop() {

		while (true) {
			if (!stop) {
				// original
				/*
				for (int i = 0; i < celestialBodies.length - 1; i++) {
					celestialBodies[i].update(celestialBodies[8].getXPosition(), celestialBodies[8].getYPosition(), celestialBodies[8].getMass());
				}
				*/
				// this is the SUN 
				//we must update relative to the sun position
				Planet sun = celestialBodies.get(celestialBodies.size() - 1);
				for (Planet planet : celestialBodies) {
					if(planet!=sun)
					planet.update(sun.getXPosition(), sun.getYPosition(), sun.getMass());
				}
			}
			repaint();

			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException ex) {
			}
		}

	}

}