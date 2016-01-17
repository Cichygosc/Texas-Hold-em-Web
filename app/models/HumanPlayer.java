package models;

public class HumanPlayer implements Comparable<HumanPlayer> {

	private int seat;
	private Hand hand;
	private String name;
	private PlayerPot pot;
	private boolean isHost;
	
	public HumanPlayer(String name, boolean isHost) {
		this.name = name;
		this.isHost = isHost;
		hand = new Hand();
		pot = new PlayerPot();
		seat = -1;
	}
	

	public String getName()
	{
		return name;
	}
	
	public Hand getHand()
	{
		return hand;
	}
	
	public int getSeat()
	{
		return seat;
	}
	
	public boolean isHost()
	{
		return isHost;
	}
	
	public PlayerPot getPlayerPot()
	{
		return pot;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void takeASeat(int seat)
	{
		this.seat = seat;
	}
	
	public void setIsHost(boolean isHost)
	{
		this.isHost = isHost;
	}
	
	public int compareTo(HumanPlayer p)
	{
		return this.seat > p.getSeat() ? 1 : this.seat < p.getSeat() ? -1 : 0;
	}
}
