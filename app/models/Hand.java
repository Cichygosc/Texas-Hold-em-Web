package models;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private List<Card> cardsInHand;
	private List<Card> cardsOnBoard;

	public Hand() {
		cardsInHand = new ArrayList<Card>();
		cardsOnBoard = new ArrayList<Card>();
		newGame();
	}

	public void newGame() {
		cardsInHand.clear();
		cardsOnBoard.clear();
	}

	public int numberOfCards() {
		return cardsInHand.size();
	}

	public void addCardToHand(Card c) {
		cardsInHand.add(c);
	}

	public void addCardOnBoard(Card c) {
		cardsOnBoard.add(c);
	}

	public BestHand getBestHand() {
		List<Card> allCards = new ArrayList<Card>(cardsInHand);
		allCards.addAll(cardsOnBoard);
		return CardEvaluator.getInstance().checkCards(allCards);
	}

	public Card getCardFromHand(int index) {
		return (index < cardsInHand.size() ? cardsInHand.get(index) : null);
	}

	public List<Card> getBoardCards() {
		return cardsOnBoard;
	}

}
