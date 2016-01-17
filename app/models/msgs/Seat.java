package models.msgs;

public class Seat {
	final int seat;
	final String playerName;
	
	public Seat(int seat, String playerName)
	{
		this.seat = seat;
		this.playerName = playerName;
	}
	
	public int getSeat()
	{
		return seat;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
}
