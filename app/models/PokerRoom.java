package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import models.msgs.Info;
import models.msgs.Money;
import models.msgs.Seat;
import models.msgs.Turn;

public class PokerRoom {

	private List<HumanPlayer> connectedPlayers;
	private int numOfConnectedPlayers;
	private GameStateBehavior gameState;
	private ActorRef gameController;
	private Dealer dealer;
	private Random random;

	private Turn currentOptions;
	private HumanPlayer currentPlayer;
	private int currentPlayerNumber;
	private int dealerPosition;
	private boolean isDealerTurn;

	private int playersFoldOrAllIn;
	private int raisedTimes;
	private boolean gameEnded;
	private boolean gameStarted;

	public PokerRoom(ActorRef gameController) {
		connectedPlayers = new ArrayList<HumanPlayer>();
		numOfConnectedPlayers = 0;
		this.gameController = gameController;
		dealer = new Dealer(this);
		isDealerTurn = false;
		currentPlayer = null;
		currentPlayerNumber = -1;
		dealerPosition = -1;
		raisedTimes = 0;
		playersFoldOrAllIn = 0;
		gameEnded = false;
		gameStarted = false;
		random = new Random();
	}

	public void startGame() {
		if (gameReadyToStart()) {
			gameController.tell(new Info("All players connected. Game starting in 2 seconds, have fun", "GAME"), null);
			Collections.sort(connectedPlayers);
			int startingMoney = GameSettings.getInstance().getStartingMoney();
			for (HumanPlayer player : connectedPlayers) {
				player.getPlayerPot().setMoney(startingMoney);
				gameController.tell(new Money(startingMoney, player.getName()), null);
			}
			final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.schedule(new Runnable() {
				
				@Override
				public void run() {
					newGame();
					
				}
			}, 2, TimeUnit.SECONDS);
		}
	}

	private void newGame() {
		System.out.println("Starting new game");
		for (HumanPlayer player : connectedPlayers) {
			player.getPlayerPot().newGame();
			player.getHand().newGame();
		}
		gameState = GameState.Preflop.getStateBehavior();
		raisedTimes = 0;
		playersFoldOrAllIn = 0;
		gameEnded = false;
		gameStarted = true;
		dealer.newGame();
		chooseDealer();
		findAvailableNextPlayer();
		dealer.throwCards();
		getBlinds();
		nextPlayerTurn();
	}

	private void stopGame() {
		if (!gameStarted)
			return;
		if (numOfConnectedPlayers == 1) {
			gameController.tell(new Info("You won! Waiting for players to start new game", "GAME"), null);
			gameStarted = false;
		}

	}

	public boolean gameReadyToStart() {
		if (GameSettings.getInstance().getNumOfPlayers() > connectedPlayers.size())
			return false;
		for (HumanPlayer player : connectedPlayers)
			if (player.getSeat() == -1)
				return false;
		return true;
	}

	public void getBlinds() {
		try {

			Table table = dealer.getTable();
			currentPlayer.getPlayerPot().bet(GameSettings.getInstance().getSmallBlind());
			dealer.addPot(GameSettings.getInstance().getSmallBlind());
			gameController.tell(new Info(currentPlayer.getName() + " small blind", "GAME"), null);
			gameController.tell(new Money(currentPlayer.getPlayerPot().getMoney(), currentPlayer.getName()), null);
			findAvailableNextPlayer();
			currentPlayer.getPlayerPot().bet(GameSettings.getInstance().getBigBlind());
			dealer.addPot(GameSettings.getInstance().getBigBlind());
			gameController.tell(new Info(currentPlayer.getName() + " big blind", "GAME"), null);
			gameController.tell(new Money(currentPlayer.getPlayerPot().getMoney(), currentPlayer.getName()), null);
			table.increaseRoundBet(GameSettings.getInstance().getBigBlind());
			table.setLastBet(GameSettings.getInstance().getBigBlind());
			table.setIsOpen(true);
		} catch (Exception e) {
			System.out.println("NO MONEY TO PLAY");
		}
	}

	public void setDealerPos(int pos) {
		dealerPosition = pos;
		currentPlayer = connectedPlayers.get(dealerPosition);
		currentPlayerNumber = dealerPosition;
	}

	public void findAvailableNextPlayer() {
		isDealerTurn = false;
		nextPlayer();
		if (currentPlayerNumber == dealerPosition)
			isDealerTurn = true;
		while (currentPlayer.getPlayerPot().isAllIn() || currentPlayer.getPlayerPot().isFold()) {
			nextPlayer();
			if (currentPlayerNumber == dealerPosition)
				isDealerTurn = true;
		}
	}

	private void nextPlayer() {
		if (currentPlayerNumber == connectedPlayers.size() - 1)
			currentPlayerNumber = 0;
		else
			currentPlayerNumber++;
		currentPlayer = connectedPlayers.get(currentPlayerNumber);
	}

	public void nextPlayerTurn() {
		if (gameEnded)
			while (nextRound())
				;
		if (isDealerTurn && checkEndOfRound())
			if (!nextRound())
				return;
		findAvailableNextPlayer();
		int call = dealer.getTable().getRoundBet() - currentPlayer.getPlayerPot().getTotalCashUsedInRound();
		BettingValues values = GameSettings.getInstance().getRules().calculateBet(this);
		int raise = values.getRaise();
		int maxRaise = values.getMaxRaise();
		String buttons = "";
		if (currentPlayer.getPlayerPot().getMoney() - call < 0)
			buttons += "AllIn Fold";
		else {
			if (raise < maxRaise) {
				if (dealer.getTable().getIsOpen())
					buttons += "Raise ";
				else
					buttons += "Bet ";
			}
			if (call == 0)
				buttons += "Check ";
			if (call > 0)
				buttons += "Call Fold ";
		}
		int pot = dealer.getTable().getPot();
		int money = currentPlayer.getPlayerPot().getMoney();
		currentOptions = new Turn(call, raise, maxRaise, pot, money, buttons, currentPlayer.getName());
		gameController.tell(currentOptions, null);
	}

	public void chooseDealer() {
		if (dealerPosition == -1)
			dealerPosition = random.nextInt(connectedPlayers.size());
		else {
			if (dealerPosition == connectedPlayers.size() - 1)
				dealerPosition = 0;
			else
				dealerPosition++;
		}
		currentPlayerNumber = dealerPosition;
		currentPlayer = connectedPlayers.get(currentPlayerNumber);
	}

	public boolean nextRound() {
		for (HumanPlayer player : connectedPlayers) {
			player.getPlayerPot().newRound();
		}
		dealer.getTable().newRound();
		gameState = gameState.nextState();
		int numOfCards = gameState.getNumberOfCards();
		if (numOfCards == 0) {
			dealer.showdown();
			return false;
		}
		int pos = gameState.getCardsStartPos();
		dealer.showMiddleCards(numOfCards, pos);
		return true;
	}

	public boolean checkEndOfRound() {
		int i = 0;
		for (i = 0; i < connectedPlayers.size(); ++i) {
			System.out.println("DEALER: " + dealer.getTable().getRoundBet());
			if (connectedPlayers.get(i).getPlayerPot().isAllIn() || connectedPlayers.get(i).getPlayerPot().isFold())
				continue;
			if (dealer.getTable().getRoundBet() - connectedPlayers.get(i).getPlayerPot().getTotalCashUsedInRound() != 0)
				return false;
		}
		return true;
	}

	public void restartGame() {
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("RUNNING NEWW GAME");
				newGame();
			}
		}, 10, TimeUnit.SECONDS);
	}

	public void addPlayer(String name) {
		HumanPlayer player = new HumanPlayer(name, false);
		int seat = dealer.getTable().getFirstFreeSeat();
		player.takeASeat(seat);
		gameController.tell(new Seat(seat, player.getName()), null);
		dealer.getTable().addTakenSeat(seat);
		connectedPlayers.add(player);
		numOfConnectedPlayers++;
	}

	public void removePlayer(String name) {
		for (HumanPlayer player : connectedPlayers)
			if (player.getName().equals(name)) {
				connectedPlayers.remove(player);
				dealer.getTable().removeTakenSeat(player.getSeat());
				numOfConnectedPlayers--;
				break;
			}
		stopGame();
	}

	public void playerFoldOrAllIn() {
		playersFoldOrAllIn++;
		if (connectedPlayers.size() == playersFoldOrAllIn + 1)
			gameEnded = true;
	}

	public int getRaisedTimes() {
		return raisedTimes;
	}

	public void increaseRaiseTimes() {
		raisedTimes++;
	}

	public int getNumOfConnectedPlayers() {
		return numOfConnectedPlayers;
	}

	public int getDealerPos() {
		return dealerPosition;
	}

	public int getCurrentPlayerNum() {
		return currentPlayerNumber;
	}

	public boolean getGameStarted() {
		return gameStarted;
	}

	public boolean isPlayerTurn(String name) {
		return currentPlayer.getName().equals(name) ? true : false;
	}

	public HumanPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public Turn getCurrentOptions() {
		return currentOptions;
	}

	public HashMap<Integer, String> getTakenSeats() {
		HashMap<Integer, String> seats = new HashMap<Integer, String>();
		for (HumanPlayer player : connectedPlayers)
			if (player.getSeat() != -1)
				seats.put(player.getSeat(), player.getName());
		return seats;
	}

	public List<HumanPlayer> getPlayers() {
		return connectedPlayers;
	}

	public ActorRef getGameController() {
		return gameController;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public Table getTable() {
		return dealer.getTable();
	}

}
