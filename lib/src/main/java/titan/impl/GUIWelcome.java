package titan.impl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * GUIWelcome class opens a frame with a start button to launch the ship
 * @author Group 12
 */
public class GUIWelcome {

	//public because we need access to it later
	public JButton zoomOut;
	public JButton zoomIn;
	public JFrame welcomeFrame;

	public static void main(String[] args) {
		new GUIWelcome();
	}

	public GUIWelcome() {

		welcomeFrame = new JFrame();
		welcomeFrame.setSize(800, 800);
		welcomeFrame.setTitle("Journey to Titan");
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(176, 196, 222));

		GridBagConstraints gbc = new GridBagConstraints();

		// source of imgIcon:
		// https://tenor.com/view/cosmic-stairway-space-bright-lights-espacio-gif-11884832
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
		
		JLabel chooseLabel = new JLabel("Please, also choose the solver which you would like to try.");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(chooseLabel, gbc);

		JLabel emptyLabel2 = new JLabel("                              ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(emptyLabel2, gbc);

		JComboBox<String> controller = new JComboBox<String>();
		gbc.gridx = 0;
		gbc.gridy = 5;
		controller.addItem("Open-loop controller");
		controller.addItem("Feedback controller");
		panel.add(controller, gbc);
		
		JComboBox<String> solver = new JComboBox<String>();
		gbc.gridx = 1;
		gbc.gridy = 5;
		solver.addItem("Euler");
		solver.addItem("RK4");
		solver.addItem("Verlet");
		panel.add(solver, gbc);
		
		String controllerChoice = controller.getSelectedItem().toString();
		
		String solverChoice = solver.getSelectedItem().toString();
		//System.out.println(solverChoice);
		
		SystemPlanet.setSolver(solverChoice);
		SystemPlanet.setController(controllerChoice);
		
		JLabel emptyLabel3 = new JLabel("                              ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		panel.add(emptyLabel3, gbc);

		JButton startButton = new JButton("Start your journey");
		gbc.gridx = 0;
		gbc.gridy = 7;
		panel.add(startButton, gbc);

		// Previous implementation where then user determines the initial velocity
		// String input = JOptionPane.showInputDialog("Please, enter your speed.");
		// double speed = Double.parseDouble(input);

		welcomeFrame.add(panel);
		welcomeFrame.setVisible(true);
		SystemPlanet systemPlanet = new SystemPlanet(this);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				welcomeFrame.setContentPane(systemPlanet);
				welcomeFrame.setSize(1600, 900);
				systemPlanet.startMe();

				// Toolbar for more functionality
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
							SystemPlanet.delay = 1; // systemPlanet.delay is very small
						}
						else {
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

				// add to toolbar and add toolbar to frame
				toolbar.add(speedDecrease);
				toolbar.add(speedIncrease);
				toolbar.add(pauseStart);
				toolbar.add(zoomOut);
				toolbar.add(zoomIn);
				toolbar.add(resetToInitialState);
				welcomeFrame.add(toolbar);
			}
		});
	}
}