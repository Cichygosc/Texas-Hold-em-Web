package models;

public interface GameStateBehavior {
	public int getNumberOfCards();

	public int getCardsStartPos();

	public GameStateBehavior nextState();

	public GameState getState();
}
