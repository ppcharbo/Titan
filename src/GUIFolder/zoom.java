package GUIFolder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class zoom extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int prevN = 0;
	private Dimension preferredSize = new Dimension(400, 400);
	private Rectangle2D[] rects = new Rectangle2D[50];

	/*public static void main(String[] args) {
		JFrame jf = new JFrame("test");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(800, 800);

		JPanel containerPanel = new JPanel(); // extra JPanel
		containerPanel.setLayout(new GridBagLayout());
		containerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 0, 1)));

		zoom zoomPanel = new zoom();
		containerPanel.add(zoomPanel);
		zoomPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jf.add(new JScrollPane(containerPanel));
		jf.setVisible(true);
	}*/

	public zoom() {
		// generate rectangles with pseudo-random coords
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new Rectangle2D.Double(Math.random() * .8, Math.random() * .8, Math.random() * .2, Math.random() * .2);
		}

		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				updatePreferredSize(e.getWheelRotation(), e.getPoint());
			}
		});
	}

	private void updatePreferredSize(int n, Point p) {

		if (n == 0) // ideally getWheelRotation() should never return 0.
			n = -1 * prevN; // but sometimes it returns 0 during changing of zoom
		// direction. so if we get 0 just reverse the direction.

		double d = (double) n * 1.08;
		d = (n > 0) ? 1 / d : -d;

		int w = (int) (getWidth() * d);
		int h = (int) (getHeight() * d);
		preferredSize.setSize(w, h);

		int offX = (int) (p.x * d) - p.x;
		int offY = (int) (p.y * d) - p.y;
		getParent().setLocation(getParent().getLocation().x - offX, getParent().getLocation().y - offY);
		//in the original code, zoomPanel is being shifted. here we are shifting containerPanel

		getParent().doLayout(); // do the layout for containerPanel
		getParent().getParent().doLayout(); // do the layout for jf (JFrame)

		prevN = n;
	}

	@Override
	public Dimension getPreferredSize() {
		return preferredSize;
	}

}