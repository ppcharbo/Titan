package titan;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {

	public GUI() {
		JFrame welcomeFrame = new JFrame();
		welcomeFrame.setSize(800, 600);
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
		initialSpeedInput.setSize(100, 50);
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(initialSpeedLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		initialSpeedInput.setColumns(5);
		panel.add(initialSpeedInput, gbc);

		welcomeFrame.add(panel);
		welcomeFrame.setVisible(true);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PlanetScreen();
				welcomeFrame.dispose();
			}
		});
	}

}
