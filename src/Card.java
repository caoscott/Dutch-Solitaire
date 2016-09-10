public class Card {
	
	private int num;
	private char suite;
	
	public Card(String str) {
		num = Integer.parseInt(str.substring(0, str.length()-1));
		suite = str.charAt(str.length()-1);
	}
	
	public int getNum() {
		return num;
	}
	
	public char getSuite() {
		return suite;
	}
	
	@Override
	public String toString() {
		return ""+num+suite;
	}
	
}