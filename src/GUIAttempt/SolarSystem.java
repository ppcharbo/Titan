package GUIAttempt;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class SolarSystem extends JFrame{
	public double speed;
	public static int delay = 25;
	public boolean stop = false;
	public ArrayList<Planet> allPlanets = new ArrayList<Planet>();
	public ArrayList<Rocket> greatRocket = new ArrayList<Rocket>();
	double size = 1;
	
    public static int prevN = 0;
    public Dimension preferredSize = new Dimension(1600, 900);
	
	public SolarSystem(double speed) {
		this.speed = speed;
		JFrame solarSystemFrame = new JFrame();
		solarSystemFrame.setSize(1600, 900);
		solarSystemFrame.setTitle("Journey to Titan");
		solarSystemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel planetPanel = new JPanel();
		
		
		
		JToolBar toolbar = new JToolBar("Tools");
		
		// Buttons in toolbar
		JButton speedDecrease = new JButton("decrease");
		speedDecrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delay = delay + 5;
			}
		});
		JButton speedIncrease = new JButton("increase");
		speedIncrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (delay - 10 <= 0) {
					// systemPlanet.delay is very small
					delay = 1;
				} else {
					delay = delay - 10;
				}

			}
		});
		JButton pauseStart = new JButton("pause/start");
		pauseStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop = !(stop);
			}
		});
		
		
		// buttons to toolbar to frame
		toolbar.add(speedDecrease);
		toolbar.add(speedIncrease);
		toolbar.add(pauseStart);
		toolbar.setVisible(true);
		planetPanel.add(toolbar);
		solarSystemFrame.add(toolbar);
		
		
		
		// if the system is resized, then we need to repaint the system as well!
		solarSystemFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				solarSystemFrame.repaint();
			}
		});
		
		
		//if we want to zoom in the System, then we need to repaint the system as well!
		planetPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                updatePreferredSize(e.getWheelRotation(), e.getPoint());
            }
        });
		
		
		

		
		allPlanets.add(new Planet(planetPanel, "", 128, 128, 128, 800, 500, 8, -4.7, 0, 9));
		allPlanets.add(new Planet(planetPanel, "", 207, 153, 52, 952, 450, 12, 0, 2.5, 900));
		allPlanets.add(new Planet(planetPanel, "", 0, 0, 255, 800, 200, 11, 1.8, 0, 900));
		allPlanets.add(new Planet(planetPanel, "", 255, 0, 0, 850, 0, 7, 1.2, 0, 900));
		allPlanets.add(new Planet(planetPanel, "", 255, 140, 0, 800, -50, 20, 1.2, 0, 900));
		allPlanets.add(new Planet(planetPanel, "", 112, 128, 144, 800, -75, 15, 1.2, 0, 900));
		allPlanets.add(new Planet(planetPanel, "", 196, 233, 238, 800, -125, 15, 1.2, 0, 900));
		allPlanets.add(new Planet(planetPanel, "", 66, 98, 243, 0, 650, 13, 0, -1.2, 900));
		allPlanets.add(new Planet(planetPanel, "sun", 255, 140, 0, 800, 450, 30, .1, 0, 1000));
		greatRocket.add(new Rocket(planetPanel, speed, 20, 800, 200));

		//Use a thread for updating the planets
		Thread thread = new Thread() {

			@Override
			public void run() {
				gameLoop();
			}
		};

		thread.start();
		
		solarSystemFrame.add(planetPanel);
		solarSystemFrame.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {

		//previously: super.paintComponent(g)
		//super.paintComponents(g);

		for (Planet body : allPlanets) {
			body.draw(g, size, getWidth(), getHeight());
		}
		for (Rocket rocketo : greatRocket) {
			rocketo.draw(g, 5);
		}
	}
	
	public void gameLoop() {

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
	
	public void updatePreferredSize(int n, Point p) {

        if(n == 0)              // ideally getWheelRotation() should never return 0.
            n = -1 * prevN;     // but sometimes it returns 0 during changing of zoom
        // direction. so if we get 0 just reverse the direction.

        double d = (double) n * 1.08;
        d = (n > 0) ? 1 / d : -d;

        int w = (int) (getWidth() * d);
        int h = (int) (getHeight() * d);
        preferredSize.setSize(w, h);

        int offX = (int)(p.x * d) - p.x;
        int offY = (int)(p.y * d) - p.y;
        setLocation(getLocation().x-offX,getLocation().y-offY);
        //in the original code, zoomPanel is being shifted. here we are shifting containerPanel

        doLayout();             // do the layout for containerPanel
        //doLayout(); // do the layout for jf (JFrame)

        prevN = n;
    }
	
    @Override
    public Dimension getPreferredSize() {
        return preferredSize;
    }

}
