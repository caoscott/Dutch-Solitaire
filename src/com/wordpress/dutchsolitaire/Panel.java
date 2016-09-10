package com.wordpress.dutchsolitaire;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Panel extends JPanel implements KeyListener, MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6191735977413691567L;
	private Card[][] cards;
	private Stack<Integer> clicks, keyCodes, backClicks, forwardClicks;
	public static final Card[] ACES = {new Card("14c"), new Card("14d"), 
		new Card("14h"), new Card("14s")};
	private JTextArea typingArea;
	
	public Panel() {
		initListeners();
		initCards();
	}
	
	private void initListeners() {
		clicks = new Stack<Integer>();
		backClicks = new Stack<Integer>();
		forwardClicks = new Stack<Integer>();
		keyCodes = new Stack<Integer>();
		typingArea = new JTextArea();
		typingArea.addKeyListener(this);
		add(typingArea);
		addMouseListener(this);
	}
	
	private void initCards() {
		char k = ' ';
		cards = new Card[4][13];
		List<String> all = new ArrayList<String>(cards.length*cards[0].length);
		for(int r = 0; r < cards.length; r++) {
			switch(r) {
			case 0: k = 'c'; break;
			case 1: k = 'd'; break;
			case 2: k = 'h'; break;
			case 3: k = 's'; break;
			}
			for(int c = 0; c < cards[0].length+1; c++)
				if(c != 12)
					all.add(c+2+""+k);
		}
		for(int r = 0; r < cards.length; r++)
			for(int c = 0; c < cards[r].length; c++) 
				cards[r][c] = new Card(all.remove((int)(Math.random()*all.size())));
	}
	
	private void drawCards(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(int r = 0; r < cards.length; r++)
        	for(int c = 0; c < cards[0].length; c++)
        		g2d.drawImage(cards[r][c].getImage(), 5+75*c, 100*r+5, null);
    }
	
	private void drawAces(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for(int r = 0; r < ACES.length; r++)
			g2d.drawImage(ACES[r].getImage(), cards[0].length*75+5, 100*r+5, null);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCards(g);
        drawAces(g);
    }
	
	private boolean canSwap(int bR, int bC, int r, int c) {
		Card card = cards[r][c];
		if(bC == 0) {
			if(card.getNum() == ACES[bR].getNum()-12 && card.getSuite() == ACES[bR].getSuite())
				return true;
		}
		else {
			Card d = cards[bR][bC-1];
			if(card.getNum() == d.getNum()+1 && card.getSuite() == d.getSuite())
				return true;
		}
		if(bC == cards[0].length-1){
			if(card.getNum() == ACES[bR].getNum()-1 && card.getSuite() == ACES[bR].getSuite())
				return true;
		}
		else {
			Card d = cards[bR][bC+1];
			if(card.getNum() == d.getNum()-1 && card.getSuite() == d.getSuite())
				return true;
		}
		return false;
	}
	
	private void swap(int bR, int bC, int r, int c) {
		Card temp = cards[bR][bC];
		cards[bR][bC] = cards[r][c];
		cards[r][c] = temp;
		repaint();
	}
	
	private void controlZ() {
		if(!backClicks.isEmpty()) {
			int a = backClicks.pop(), b = backClicks.pop(), 
				c = backClicks.pop(), d = backClicks.pop();
			forwardClicks.push(d);
			forwardClicks.push(c);
			forwardClicks.push(b);
			forwardClicks.push(a);
			swap(a, b, c, d);
		}
	}
	
	private void controlY() {
		if(!forwardClicks.isEmpty()) {
			int a = forwardClicks.pop(), b = forwardClicks.pop(), 
				c = forwardClicks.pop(), d = forwardClicks.pop();
			backClicks.push(d);
			backClicks.push(c);
			backClicks.push(b);
			backClicks.push(a);
			swap(a, b, c, d);
		}
	}
	
	private void f2() {
		initCards();
		repaint();
	}
	
	private boolean won() {
		char k = ' ';
		for(int r = 0; r < cards.length; r++) {
			switch(r) {
			case 0: k = 'c'; break;
			case 1: k = 'd'; break;
			case 2: k = 'h'; break;
			case 3: k = 's'; break;
			}
			for(int c = 0; c < cards[0].length; c++)
				if((cards[r][c].getNum() != c+2 || cards[r][c].getSuite() != k) && 
						cards[r][c].getNum() != 15) //NOT FINISHED
					return false;
		}
		return true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(!keyCodes.isEmpty() && keyCodes.peek() == KeyEvent.VK_CONTROL) {
			keyCodes.pop();
			switch(keyCode) {
			case KeyEvent.VK_Z: controlZ(); break;
			case KeyEvent.VK_Y: controlY(); break;
			}
		}
		else if(!keyCodes.isEmpty() && keyCodes.peek() == KeyEvent.VK_F2) {
			System.out.println("F2");
			keyCodes.pop();
			f2();
		}
		else {
			keyCodes.push(keyCode);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int r = (e.getY()-5)/100, c = (e.getX()-5)/75;
		if(r >= cards.length || c >= cards[r].length)
			return;
		if(cards[r][c].getNum() == 15) {
			if(!clicks.isEmpty()){
				int a = clicks.pop(), b = clicks.pop();
				if(canSwap(r, c, a, b)) {
					backClicks.push(b);
					backClicks.push(a);
					backClicks.push(c);
					backClicks.push(r);
					forwardClicks.clear();
					swap(r, c, a, b);
				}
			}
		}
		else {
			clicks.push(c);
			clicks.push(r);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
