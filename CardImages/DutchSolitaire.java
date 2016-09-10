
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DutchSolitaire
{
	public static void main(String[] args) 
	{
		Frame f = new Frame();
		f.setVisible(true);
	}
}

class Frame extends JFrame
{
	public Frame()
	{
		setTitle("Dutch Solitaire");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);
	}
}

class Panel extends JPanel
{
	public Panel()
		
}