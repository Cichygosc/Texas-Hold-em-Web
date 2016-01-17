package models.msgs;

public class Turn {
	final int call;
	final int raise;
	final int maxRaise;
	final int pot;
	final int playerMoney;
	final String buttons;
	final String name;

	public Turn(int call, int raise, int maxRaise, int pot, int playerMoney, String buttons, String name) {
		this.call = call;
		this.raise = raise;
		this.maxRaise = maxRaise;
		this.buttons = buttons;
		this.name = name;
		this.pot = pot;
		this.playerMoney = playerMoney;
	}

	public int getCall() {
		return call;
	}

	public int getRaise() {
		return raise;
	}

	public int getMaxRaise() {
		return maxRaise;
	}

	public int getPot() {
		return pot;
	}

	public int getPlayerMoney() {
		return playerMoney;
	}

	public String getButtons() {
		return buttons;
	}

	public String getName() {
		return name;
	}
}
