package adm.werock.sportstats;

public class ActPlayer {
	
	private int number;
	private String name;
	private String surname;
	private String licenseNumber;
	
	private int points;
	private int freeThrowsMade;
	private int freeThrowsMissed;
	private int fouls;
	
	public ActPlayer(int number, String name, String surname, String licenseNumber)
	{
		this.number = number;
		this.name = name;
		this.surname = surname;
		this.licenseNumber = licenseNumber;
		this.points = 0;
		this.freeThrowsMade = 0;
		this.freeThrowsMissed = 0;
		this.fouls = 0;
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
	
	//
	
	public int getNumber(){
		return number;
	}
	
	public String getLicenseNumber(){
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
