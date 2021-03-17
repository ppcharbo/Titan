package GUIFolder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SystemPlanet extends JPanel {
	Planet[] systemOfPlanets = new Planet[9];
	private Image img;

	public static int delay = 25;
	double size = 1;

	boolean stop = false;
	int clicked = -1;
	
	
	int width;
	int height;

	public SystemPlanet() {
		ImageIcon icon = new ImageIcon(this.getClass().getResource("background.jpg")); // https://www.pexels.com/photo/starry-sky-998641/

		img = icon.getImage();

		systemOfPlanets[0] = new Planet(128, 128, 128, 600, 450, 8, -4.7, 0, 9);
		systemOfPlanets[1] = new Planet(207, 153, 52, 752, 400, 12, 0, 2.5, 900);
		systemOfPlanets[2] = new Planet(0, 0, 255, 600, 150, 11, 1.8, 0, 900);
		systemOfPlanets[3] = new Planet(255, 0, 0, 650, -50, 7, 1.2, 0, 900);
		systemOfPlanets[4] = new Planet(255, 140, 0, 600, -100, 20, 1.2, 0, 900);
		systemOfPlanets[5] = new Planet(112, 128, 144, 600, -150, 15, 1.2, 0, 900);
		systemOfPlanets[6] = new Planet(196, 233, 238, 600, -175, 15, 1.2, 0, 900);
		systemOfPlanets[7] = new Planet(66, 98, 243, 0, 400, 13, 0, -1.2, 900);
		systemOfPlanets[8] = new Planet(255, 140, 0, 600, 400, 30, .1, 0, 1000);

		// setBackground(Color.BLACK);

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
		//System.out.println("width  " + width2 + " height  " + height2);

		g2.drawImage(img, 0, 0, width2, height2, this);

		for (Planet body : systemOfPlanets)
			body.draw(g, size);
	}

	private void gameLoop() {

		while (true) {
			if (!stop) {
				for (int i = 0; i < systemOfPlanets.length - 1; i++) {
					systemOfPlanets[i].update(systemOfPlanets[8].getXPosition(), systemOfPlanets[8].getYPosition(),
							systemOfPlanets[8].getMass());
				}
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
	PLEASE, DO NOT DELETE THIS, WORK IN PROGRESS!
	
	public void resetToMiddle() {
		int width2 = getWidth();
		int height2 = getHeight();
		//System.out.println("width  " + width2 + " height  " + height2);
		
		setWidth(width2);
		setHeight(height2);
		
		for(Planet body : systemOfPlanets) {
			body.setXPosition(body.getXPosition()+ getWidth()/2);
		}
		for(Planet body : systemOfPlanets) {
			body.setYPosition(body.getYPosition()+ getHeight()/2);
		}
		
	}
	
	public void setWidth(int w) {
		width = w;
	}
	public void setHeight(int h) {
		height = h;
	}
	
	*/

}