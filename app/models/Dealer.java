package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import models.msgs.Info;
import models.msgs.NewCard;
import models.msgs.Winners;

public class Dealer {

	private Table table;
	private CardDeck deck;
	private PokerRoom pokerRoom;
	
	public Dealer(PokerRoom pokerRoom) {
		this.pokerRoom = pokerRoom;
		table = new Table();
		deck = new CardDeck();
		deck.shuffleCards();
	}

	public void newGame() {
		table.newGame();
		deck.shuffleCards();
	}

	public void throwCards() {
		for (HumanPlayer player : pokerRoom.getPlayers()) {
			for (int i = 0; i < 2; ++i) {
				Card card = deck.getCard();
				player.getHand().addCardToHand(card);
				pokerRoom.getGameController().tell(new NewCard(card.getNumber(), card.getSuit(), player.getName()), null);
			}
		}
	}
	
	public void showMiddleCards(int amount, int pos)
	{
		for (int i = 0; i < amount; ++i, ++pos)
		{
			Card card = deck.getCard();
			for (HumanPlayer player : pokerRoom.getPlayers())
				player.getHand().addCardOnBoard(card);
			pokerRoom.getGameController().tell(new Info("New board card " + card.toString(), "GAME"), null);
			pokerRoom.getGameController().tell(new NewCard(card.getNumber(), card.getSuit(), "BOARD"), null);
		}
	}

	public void showdown() {

		pokerRoom.getGameController().tell(new Info("Checking cards...", "GAME"), null);
		for (HumanPlayer player: pokerRoom.getPlayers())
		{
			pokerRoom.getGameController().tell(new Info(player.getName() + " cards: ", "GAME"), null);
			for (int i = 0; i < 2; ++i)
				pokerRoom.getGameController().tell(new Info(player.getHand().getCardFromHand(i).toString(),  "GAME"), null);
		}
		lookForWinner();
	}
	
	private void lookForWinner()
	{
		table.optimalizeSidePots();
		String winnersMessage = "Winners: ";
		HashSet<HumanPlayer> allWinners = new HashSet<HumanPlayer>();
		for (int i = 0; i < table.getTablePots().size(); ++i)
		{
			TablePot pot = table.getTablePots().get(i);
			List<HumanPlayer> winners = new ArrayList<HumanPlayer>();
			BestHand winnerHand = null;
			for (HumanPlayer player: pot.getPlayers())
			{
				if (player.getPlayerPot().isFold())
					continue;
				if (winners.isEmpty())
				{
					winners.add(player);
					winnerHand = player.getHand().getBestHand();
					continue;
				}
				BestHand comHand = player.getHand().getBestHand();
				if (winnerHand.getRank() < comHand.getRank())
				{
					winners.clear();
					winners.add(player);
					winnerHand = comHand;
					continue;
				}
				else if (winnerHand.getRank() > comHand.getRank())
				{
					continue;
				}
				int handCompare = winnerHand.compareHighCards(comHand);
				if (handCompare == 1)
				{
					continue;
				}
				else if (handCompare == -1)
				{
					winners.clear();
					winners.add(player);
					winnerHand = comHand;
				}
				else if (handCompare == 0)
				{
					winners.add(player);
				}
			}
			int moneyWin = pot.getPot() / winners.size();
			for (HumanPlayer player: winners)
			{
				if (!allWinners.contains(player))
				{
					winnersMessage += player.getName() + " with " + winnerHand + " ";
					allWinners.add(player);
				}
				player.getPlayerPot().addMoney(moneyWin);
			}
		}
		pokerRoom.getGameController().tell(new Winners(winnersMessage, "GAME"), null);
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(new Runnable() {
			
			@Override
			public void run() {
				pokerRoom.restartGame();
				
			}
		}, 2, TimeUnit.SECONDS);
	}
	
	public void setCurrentBet(int bet)
	{
		if (table.getIsOpen())
			pokerRoom.increaseRaiseTimes();
		table.setLastBet(bet);
		table.increaseRoundBet(bet);
		table.setIsOpen(true);
		addPot(bet);
	}
	
	public void addPot(int pot)
	{
		table.addPot(pot);
		table.checkSidePots(pot, pokerRoom.getCurrentPlayer());
	}

	public Table getTable() {
		return table;
	}
	
	////////////////////////////////////////////////////
	//////////// METHODS USED ONLY IN TESTS/////////////
	////////////////////////////////////////////////////
	
	public void setCurrentBet(int bet, HumanPlayer player)
	{
		table.setLastBet(bet);
		table.increaseRoundBet(bet);
		table.setIsOpen(true);
		addPot(bet, player);
	}
	
	public void addPot(int pot, HumanPlayer player)
	{
		table.addPot(pot);
		table.checkSidePots(pot, player);
	}
	
}
