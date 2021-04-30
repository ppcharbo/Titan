package GUI2;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DragPanel dragPanel = new DragPanel();

	MyFrame() {

		this.add(dragPanel);
		this.setTitle("Drag & Drop demo");
		this.setSize(1850, 1020);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}
}