package adm.werock.sportstats;

import adm.werock.sportstats.basics.Player;

public class ActPlayer {
	
	private int number;
	private String name;
	private String surname;
	private int licenseNumber;
	
	private boolean bStarter;
	private boolean bCaptain;
	
	private int points;
	private int freeThrowsMade;
	private int freeThrowsMissed;
	private int fouls;
	
	public ActPlayer(int number, String name, String surname, int licenseNumber)
	{
		this.number = number;
		this.name = name;
		this.surname = surname;
		this.licenseNumber = licenseNumber;
		
		this.bStarter = false;
		this.bCaptain = false;
		
		this.points = 0;
		this.freeThrowsMade = 0;
		this.freeThrowsMissed = 0;
		this.fouls = 0;
	}
	
	public ActPlayer(Player player)
	{
		this.name = player.getName();
		this.surname = player.getSurname();
		this.licenseNumber = player.getLicenseNumber();
		this.number = 0;
		
		resetStats();
	}
	
	public String toString(){
		//return new String(number + " - " + name.charAt(0)+". "+surname);
		//return surname;
		return new String(name.charAt(0)+". "+surname);
	}
	
	public void resetStats(){
		points = 0;
		freeThrowsMade = 0;
		freeThrowsMissed = 0;
		fouls = 0;
	}
	
	// Setters
	
	public void setAsStarter()
	{
		bStarter = true;
	}
	
	public void setAsCaptain()
	{
		bCaptain = true;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}
	
	// Getters
	
	public boolean isStarter(){
		return bStarter;
	}
	
	public boolean isCaptain(){
		return bCaptain;
	}
	
	public int getNumber(){
		return number;
	}
	
	public int getLicenseNumber(){
		return licenseNumber;
	}
	
	public String getName(){
		return name;
	}
	
	// Performance actions
	
	public int getPoints(){
		return points;
	}
	
	public int getFreeThrowsMade(){
		return freeThrowsMade;
	}
	
	public int getFreeThrowsMissed(){
		return freeThrowsMissed;
	}
	
	public int getFreeThrowsTotal(){
		return freeThrowsMade+freeThrowsMissed;
	}
	
	public int getFouls(){
		return fouls;
	}
	
	//
	
	public void addPoints(int pts){
		points += pts;
	}
	
	public void substractPoints(int pts){
		points -= pts;
	}
	
	public void addFreeThrowsMade(int fts){
		freeThrowsMade += fts;
	}
	
	public void substractFreeThrowsMade(int fts){
		freeThrowsMade -= fts;
	}
	
	public void addFreeThrowsMissed(int fts){
		freeThrowsMissed += fts;
	}
	
	public void substractFreeThrowsMissed(int fts){
		freeThrowsMissed -= fts;
	}
	
	public void addFouls(int f){
		fouls += f;
	}
	
	public void substractFouls(int f){
		fouls -= f;
	}
}
