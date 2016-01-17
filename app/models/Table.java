package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Table {

	private HashSet<Integer> takenSeats;
	private List<TablePot> sidePots;
	private int pot; // amount of money to win
	private int roundBet; // sum of all players bets/raises
	private int lastBet; // amount of last bet/raise
	private boolean isOpen; // was bet used before in this round

	public Table() {
		takenSeats = new HashSet<Integer>();
		sidePots = new ArrayList<TablePot>();
		isOpen = false;
		pot = 0;
		roundBet = 0;
		lastBet = 0;
	}

	public void addPot(int pot) {
		this.pot += pot;
	}

	public void newGame() {
		pot = 0;
		sidePots.clear();
		newRound();
	}

	public void newRound() {
		roundBet = 0;
		lastBet = 0;
		isOpen = false;
	}

	public void optimalizeSidePots() {
		for (int i = sidePots.size() - 1; i > 0; --i) {
			if (cmp(sidePots.get(i).getPlayers(), sidePots.get(i - 1).getPlayers())) {
				sidePots.get(i - 1).setBet(sidePots.get(i).getBet() + sidePots.get(i - 1).getBet());
				sidePots.get(i - 1).setPot(sidePots.get(i).getPot() + sidePots.get(i - 1).getPot());
				sidePots.remove(i);
			}
		}
	}

	public void checkSidePots(int bet, HumanPlayer player) {
		if (sidePots.size() == 0)
			createSidePot(bet, player);
		else {
			for (int i = 0; i < sidePots.size(); ++i) {
				if (sidePots.get(i).getBet() == bet && !sidePots.get(i).contains(player)) {
					sidePots.get(i).increasePot();
					sidePots.get(i).addPlayer(player);
					bet = 0;
					break;
				} else if (sidePots.get(i).getBet() < bet && !sidePots.get(i).contains(player)) {
					bet -= sidePots.get(i).getBet();
					sidePots.get(i).addPlayer(player);
					sidePots.get(i).increasePot();
				} else if (sidePots.get(i).getBet() > bet && !sidePots.get(i).contains(player)) {
					List<HumanPlayer> players = sidePots.get(i).getPlayers();
					int newBet = sidePots.get(i).getBet() - bet;
					createSidePot(newBet, players);
					sidePots.get(i).decreasePotByValue(players.size() * newBet);
					sidePots.get(i).setBet(bet);
					sidePots.get(i).increasePot();
					sidePots.get(i).addPlayer(player);
					bet = 0;
					break;
				}
			}
			if (bet > 0)
				createSidePot(bet, player);
		}
	}

	private void createSidePot(int bet, List<HumanPlayer> players) {
		TablePot newPot = new TablePot();
		newPot.setBet(bet);
		for (HumanPlayer player : players) {
			newPot.addPlayer(player);
			newPot.increasePot();
		}
		sidePots.add(newPot);
		Collections.sort(sidePots);
	}

	private void createSidePot(int bet, HumanPlayer player) {
		TablePot newPot = new TablePot(bet);
		newPot.addPlayer(player);
		sidePots.add(newPot);
		Collections.sort(sidePots);
	}

	private static boolean cmp(List<HumanPlayer> l1, List<HumanPlayer> l2) {
		List<HumanPlayer> temp = new ArrayList<HumanPlayer>(l1);
		for (HumanPlayer player : l2)
			if (!temp.remove(player))
				return false;
		return temp.isEmpty();
	}

	public int getPot() {
		return pot;
	}

	public int getFirstFreeSeat() {
		for (int i = 0; i < 8; ++i)
			if (!takenSeats.contains(i))
				return i;
		return -1;
	}

	public void addTakenSeat(int seat) {
		takenSeats.add(seat);
	}

	public void removeTakenSeat(int seat) {
		System.out.println("removed seat " + seat);
		takenSeats.remove(seat);

	}

	public void setLastBet(int bet) {
		this.lastBet = bet;
	}

	public void increaseRoundBet(int amount) {
		this.roundBet += amount;
	}

	public int getLastBet() {
		return lastBet;
	}

	public int getRoundBet() {
		return roundBet;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public List<TablePot> getTablePots() {
		return sidePots;
	}

}
