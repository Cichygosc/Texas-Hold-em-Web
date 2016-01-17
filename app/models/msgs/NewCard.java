package models.msgs;

public class NewCard {

	final int suit;
	final int number;
	final String name;

	public int getSuit() {
		return suit;
	}

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public NewCard(int number, int suit, String name) {
		this.number = number;
		this.suit = suit;
		this.name = name;

	}

}
