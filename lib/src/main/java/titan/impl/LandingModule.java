package titan.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import titan.StateInterface;
import titan.Vector3dInterface;


public class LandingModule extends JPanel{
	
	private static final long serialVersionUID = 1L;
	//private final GUIWelcome landFrame;
	private Point imageCorner;
	private ImageIcon icon;
	
	public double size = 100;
	
	private OpenLoopController landingstuff = new OpenLoopController();
	
	
	public LandingModule(LandingFrame landing) {
		
				// Source of image:
				// https://www.pexels.com/photo/fine-tip-on-black-surface-3934623/
				// Edited by the group so that the image is all black for a background
				icon = new ImageIcon(this.getClass().getResource("background.jpg"));
				imageCorner = new Point(0, 0);
			
			
			}

			public void paintComponent(Graphics g) {

				super.paintComponent(g);

				// Repaint background
				icon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

				
					landingstuff.draw(g, size, 120, 100);
					landingstuff.draw(g, size, 120, 100);
					
					g.setColor(new Color(218, 165, 32));
					//         x,  y, width, height
					g.fillRect(0, 700, 800, 100);
					//g.fillRect(0, 0, 800, 100);
					
			}

			

		
		
	
}
