import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JTextArea;

class Frame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3368408933344510730L;
	private Panel p;
	private JTextArea typingArea;
	
	public Frame() {
		typingArea = new JTextArea();
		p = new Panel();
		typingArea.addKeyListener(p);
		add(typingArea);
		p.addTextArea(typingArea);
		getContentPane().add(p);
		setSize(1280, 600);
		setTitle("Dutch Solitaire");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}