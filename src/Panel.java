import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Panel extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6191735977413691567L;
	private Button[][] buttons;
	private Card[][] cards;
	//private int[][] clicks;
	private Stack<Integer> rClicks,  cClicks, keyCodes;
	public static final Card[] ACES = {new Card("14c"), new Card("14d"), new Card("14h"), new Card("14s")};
	
	public Panel() {
		setLayout(new GridLayout(4, 14, 5, 10));
		initCards();
	}
	
	private void initCards() {
		char k = ' ';
		buttons = new Button[4][14];
		//clicks = new int[2][2];
		rClicks = new Stack<Integer>();
		cClicks = new Stack<Integer>();
		cards = new Card[buttons.length][buttons[0].length-1];
		List<String> all = new ArrayList<String>(buttons.length*(buttons[0].length-1));
		for(int r = 0; r < buttons.length; r++) {
			for(int c = 0; c < buttons[r].length-1; c++)
				buttons[r][c] = new Button(r, c);
			buttons[r][buttons[r].length-1] = new Button(-1, -1) {
				/**
				 * 
				 */
				private static final long serialVersionUID = -60246898566055085L;

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("*");
					//clearClicks();
				}
				
			};
		}
		for(int r = 0; r < buttons.length; r++) {
			switch(r) {
			case 0: k = 'c'; break;
			case 1: k = 'd'; break;
			case 2: k = 'h'; break;
			case 3: k = 's'; break;
			}
			for(int c = 0; c < buttons[0].length; c++)
				if(c != 12)
					all.add(c+2+""+k);
			
			buttons[r][buttons[r].length-1].setIcon(new ImageIcon("CardImages/"+ACES[r]+".gif"));
		}
		for(int r = 0; r < cards.length; r++)
			for(int c = 0; c < cards[r].length; c++) 
				cards[r][c] = new Card(all.remove((int)(Math.random()*all.size())));
		display();
		addButtons();
		//clearClicks();
	}
	
	public void addTextArea(JTextArea typingArea) {
		for(Button[] array: buttons)
			for(Button b: array)
				b.add(typingArea);
		addButtons();
	}
	
	private void addButtons() {
		for(Button[] array: buttons)
			for(Button b: array)
				add(b);
	}
	
	/*private void swap() {
		if(canSwap()) {
			Card temp = cards[clicks[0][0]][clicks[0][1]];
			cards[clicks[0][0]][clicks[0][1]] = cards[clicks[1][0]][clicks[1][1]];
			cards[clicks[1][0]][clicks[1][1]] = temp;
			clearClicks();
		}
	}
	
	private void clearClicks() {
		clicks[0][0] = clicks[0][1] = clicks[1][0] = clicks[1][1] = -1;
	}
	
	private void halfClearClicks() {
		clicks[0] = clicks[1];
		clicks[1][0] = clicks[1][1] = -1;
	}*/
	
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
		if(canSwap(bR, bC, r, c)) {
			Card temp = cards[bR][bC];
			cards[bR][bC] = cards[r][c];
			cards[r][c] = temp;
		}
	}
	
	/*private boolean canSwap() {
		if(clicks[0][0] == -1 || clicks[1][0] == -1) {
			clearClicks();
			return false;
		}
		//System.out.println("1");
		Card a = cards[clicks[0][0]][clicks[0][1]], b = cards[clicks[1][0]][clicks[1][1]];
		if(a.getNum() != 15 && b.getNum() != 15) {
			halfClearClicks();
			return false;
		}
		//System.out.println("2");
		int aRow = clicks[0][0], aCol = clicks[0][1];
		if(b.getNum() == 15) {
			Card temp = a;
			a = b;
			b = temp;
			aRow = clicks[1][0];
			aCol = clicks[1][1];
		}
		Card c = (aCol-1 >= 0) ? cards[aRow][aCol-1] : ACES[aRow]; 
		Card d = (aCol+1 < cards[0].length) ? cards[aRow][aCol+1] : ACES[aRow];
		if(b.getSuite() != c.getSuite() && b.getSuite() != d.getSuite()) {
			clearClicks();
			return false;
		}
		//System.out.println("3");
		if((b.getNum()-c.getNum() == 1 || d.getNum()-b.getNum() == 1)) {
			//System.out.println("4");
			return true;
		}
		clearClicks();
		return false;
	}*/
	
	private void display() {
		for(int r = 0; r < buttons.length; r++)
			for(int c = 0; c < buttons[r].length-1; c++)
				buttons[r][c].setIcon(new ImageIcon("CardImages/"+cards[r][c]+".gif"));
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
				if((cards[r][c].getNum() != c+2 || cards[r][c].getSuite() != k) && cards[r][c].getNum() != 15) //NOT FINISHED
					return false;
		}
		return true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_Z) {
			if(keyCodes.isEmpty())
				System.out.println("empty");
			else
				System.out.println(keyCodes.pop());
		}
		else {
			keyCodes.push(keyCode);
			System.out.println(keyCodes);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	private class Button extends JButton implements ActionListener {

		private int r, c;
		
		private Button(int a, int b) {
			r = a;
			c = b;
			addActionListener(this);
		}
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -60246898566055085L;

		@Override
		public void actionPerformed(ActionEvent e) {
			/*if(clicks[0][0] == -1) {
				clicks[0][0] = r;
				clicks[0][1] = c;
			}
			else {
				clicks[1][0] = r;
				clicks[1][1] = c;
			}
			
			if(clicks[0][0] != -1 && clicks[1][0] != -1) {
				swap();
				display();
			}*/
			
			if(cards[r][c].getNum() == 15) {
				if(!rClicks.isEmpty() && !cClicks.isEmpty())
					swap(r, c, rClicks.pop(), cClicks.pop());
			}
			else {
				rClicks.push(r);
				cClicks.push(c);
			}
			display();
		}	
		

	}
	
}
