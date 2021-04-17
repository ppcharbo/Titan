package GUIFolder;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class GUIWelcome {

	public static void main(String[] args) {
		new GUIWelcome();
	}

	public GUIWelcome() {

		JFrame welcomeFrame = new JFrame();
		welcomeFrame.setSize(800, 800);
		welcomeFrame.setTitle("Journey to Titan");
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(176, 196, 222));

		GridBagConstraints gbc = new GridBagConstraints();

		//source of imgIcon: https://tenor.com/view/cosmic-stairway-space-bright-lights-espacio-gif-11884832
		Icon imgIcon = new ImageIcon(this.getClass().getResource("cosmic.gif"));
		JLabel label = new JLabel(imgIcon);
		label.setSize(480, 270);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(label, gbc);

		JLabel emptyLabel = new JLabel("                              ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(emptyLabel, gbc);

		JButton startButton = new JButton("Start your journey");
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(startButton, gbc);
		

		//JLabel initialSpeedLabel = new JLabel("Please enter the intial speed: ");
		//JTextField initialSpeedInput = new JTextField();
		
		/*int speed = -1;
		while(speed == -1 && initialSpeedInput.getText().equals(null)) {
			speed = (int) Integer.parseInt(initialSpeedInput.getText());
			System.out.println("I am here?");
		}
		*/
		
        String input = JOptionPane.showInputDialog("Please, enter your speed.");
        double speed = Double.parseDouble(input);
		
		//int speed = (int) (initialSpeedInput.getText());
		//initialSpeedInput.setSize(100, 100);
		//gbc.gridx = 0;
		//gbc.gridy = 3;
		//panel.add(initialSpeedLabel, gbc);

		//gbc.gridx = 0;
		//gbc.gridy = 4;
		//initialSpeedInput.setColumns(5);
		//panel.add(initialSpeedInput, gbc);

		welcomeFrame.add(panel);
		welcomeFrame.setVisible(true);
		SystemPlanet systemPlanet = new SystemPlanet(speed);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// new Object to open Frame with everything
				// e.g. new PlanetScreen();
				welcomeFrame.setContentPane(systemPlanet);
				//welcomeFrame.add(new JScrollPane(systemPlanet));
				welcomeFrame.setSize(1600, 900);
				
				JToolBar toolbar = new JToolBar("Tools");

				// Buttons in toolbar
				JButton speedDecrease = new JButton("decrease");
				speedDecrease.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SystemPlanet.delay = SystemPlanet.delay + 5;
					}
				});
				JButton speedIncrease = new JButton("increase");
				speedIncrease.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (SystemPlanet.delay - 10 <= 0) {
							// systemPlanet.delay is very small
							SystemPlanet.delay = 1;
						} else {
							SystemPlanet.delay = SystemPlanet.delay - 10;
						}

					}
				});

				JButton pauseStart = new JButton("pause/start");
				pauseStart.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						systemPlanet.stop = !(systemPlanet.stop);
					}
				});

				JButton zoomOut = new JButton("zoom out");
				zoomOut.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						systemPlanet.size = systemPlanet.size - 0.1;
						// systemPlanet.repaint();
					}
				});

				JButton zoomIn = new JButton("zoom in");
				zoomIn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						systemPlanet.size = systemPlanet.size + 0.1;
						// systemPlanet.repaint();
					}
				});

				// buttons to toolbar to frame
				toolbar.add(speedDecrease);
				toolbar.add(speedIncrease);
				toolbar.add(pauseStart);
				toolbar.add(zoomOut);
				toolbar.add(zoomIn);
				welcomeFrame.add(toolbar);

			}
		});

		welcomeFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				systemPlanet.repaint();
				//systemPlanet.resetToMiddle();
				
				// DO NOT DELETE THIS, WORK IN PROGRESS!
				// Planet sun = systemPlanet.allPlanets.get(systemPlanet.allPlanets.size() - 1);
				// sun.setX((int) (welcomeFrame.getWidth() * 0.5));
				// sun.setY((int) (welcomeFrame.getHeight() * 0.5));
				// systemPlanet.repaint();

				// Planet sun = systemPlanet.allPlanets.get(systemPlanet.allPlanets.size() - 1);
				// for (Planet planet : systemPlanet.allPlanets) {
				// if(planet != sun)
				// planet.update(sun.getX(), sun.getY(), sun.getMass());
				// }
				//systemPlanet.repaint();

				// systemPlanet.resetToMiddle();
			}
		});
	}
}
