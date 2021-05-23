package titan.impl;

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
import javax.swing.JToolBar;

public class GUIWelcome {

	public JButton zoomOut;
	public JButton zoomIn;
	
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
		
		JLabel waitingLabel = new JLabel("Please, wait till a few seconds so we can calculate the trajectory.");
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(waitingLabel, gbc);
		
		JLabel emptyLabel2 = new JLabel("                              ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(emptyLabel2, gbc);

		JButton startButton = new JButton("Start your journey");
		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(startButton, gbc);
		
		// TODO properly document the input speed
		// Obtain initial speed
        //String input = JOptionPane.showInputDialog("Please, enter your speed.");
		double speed = 10; //VARIABLE NOT USED
        //double speed = Double.parseDouble(input);
		

		welcomeFrame.add(panel);
		welcomeFrame.setVisible(true);
		SystemPlanet systemPlanet = new SystemPlanet(this, speed);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// new Object to open Frame with everything
				// e.g. new PlanetScreen();
				welcomeFrame.setContentPane(systemPlanet);
				//welcomeFrame.add(new JScrollPane(systemPlanet));
				welcomeFrame.setSize(1600, 900);
				systemPlanet.startMe();
				
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

				zoomOut = new JButton("zoom out");
				zoomOut.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						systemPlanet.size = systemPlanet.size - 0.1;
					}
				});

				zoomIn = new JButton("zoom in");
				zoomIn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						systemPlanet.size = systemPlanet.size + 0.1;
					}
				});
				
				JButton resetToInitialState = new JButton("reset");
				resetToInitialState.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						systemPlanet.currentState = 0;
						systemPlanet.stop = true;
					}
				});

				// buttons to toolbar to frame
				toolbar.add(speedDecrease);
				toolbar.add(speedIncrease);
				toolbar.add(pauseStart);
				toolbar.add(zoomOut);
				toolbar.add(zoomIn);
				toolbar.add(resetToInitialState);
				welcomeFrame.add(toolbar);

			}
		});

		welcomeFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				systemPlanet.repaint();
			}
		});
	}
}
