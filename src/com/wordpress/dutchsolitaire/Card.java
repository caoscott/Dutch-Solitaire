package com.wordpress.dutchsolitaire;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Card {
	
	private int num;
	private char suite;
	private Image i;
	
	public Card(String str) {
		num = Integer.parseInt(str.substring(0, str.length()-1));
		suite = str.charAt(str.length()-1);
		loadImage(str);
	}
	
	private void loadImage(String str) {
		i = new ImageIcon("CardImages/"+str+".gif").getImage();
	}
	
	public int getNum() {
		return num;
	}
	
	public char getSuite() {
		return suite;
	}
	
	public Image getImage(){
		return i;
	}
	
}

