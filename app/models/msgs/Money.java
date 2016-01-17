package models.msgs;

public class Money {
	final int money;
	final String name;
	
	public Money(int money, String name)
	{
		this.money = money;
		this.name = name;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public String getName()
	{
		return name;
	}
}
