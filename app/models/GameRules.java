package models;

public interface GameRules {
	BettingValues calculateBet(PokerRoom pokerRoom);
}
