package GUI;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DragPanel dragMe = new DragPanel();

	public MyFrame() {

		this.add(dragMe);
		this.setTitle("Drag & Drop demo");
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}
}