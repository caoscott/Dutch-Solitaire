package com.wordpress.dutchsolitaire;

import javax.swing.JFrame;

class Frame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3368408933344510730L;
	private Panel p;
	
	public Frame() {
		init();
	}
	
	private void init() {
		p = new Panel();
		getContentPane().add(p);
		setSize(1074, 445);
		setTitle("Dutch Solitaire");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}