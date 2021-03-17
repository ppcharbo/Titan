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
import javax.swing.JTextField;
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

		JLabel initialSpeedLabel = new JLabel("Please enter the intial speed: ");
		JTextField initialSpeedInput = new JTextField();
		initialSpeedInput.setSize(100, 100);
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(initialSpeedLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		initialSpeedInput.setColumns(5);
		panel.add(initialSpeedInput, gbc);

		welcomeFrame.add(panel);
		welcomeFrame.setVisible(true);
		SystemPlanet systemPlanet = new SystemPlanet();

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// new Object to open Frame with everything
				// e.g. new PlanetScreen();
				welcomeFrame.setContentPane(systemPlanet);
				welcomeFrame.setSize(800, 801);
				JToolBar toolbar = new JToolBar("Tools");
				
				JButton speedDecrease = new JButton("decrease");
				speedDecrease.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                //decrease the speed
		            }
		        });
				toolbar.add(speedDecrease);
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
