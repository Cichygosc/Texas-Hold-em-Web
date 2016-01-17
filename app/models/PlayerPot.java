package models;

public class PlayerPot {

	private int money;
	private int totalCashInRound;	//sum of bet, raise and call in this round
	private boolean isAllIn;
	private boolean isFold;
	
	public PlayerPot()
	{
		money = 0;
		newGame();
	}
	
	public void newRound()
	{
		totalCashInRound = 0;
	}
	
	public void newGame()
	{
		totalCashInRound = 0;
		isAllIn = false;
		isFold = false;
	}
	
	public void bet(int money) throws Exception
	{
		if (money > this.money)
			throw new Exception("Insufficient money");
		this.money -= money;
		this.totalCashInRound += money;
	}
	
	public void addMoney(int money)
	{
		this.money += money;
	}
	
	public void setMoney(int money)
	{
		this.money = money;
	}
	
	public void allIn() throws Exception
	{
		bet(money);
		isAllIn = true;
	}
	
	public void fold()
	{
		isFold = true;
	}
	
	public boolean isAllIn()
	{
		return isAllIn;
	}
	
	public boolean isFold()
	{
		return isFold;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	
	public int getTotalCashUsedInRound()
	{
		return totalCashInRound;
	}
	
}
