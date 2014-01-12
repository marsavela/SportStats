package adm.werock.sportstats;

//Event types
//-------------------
//AS - Assign Starter
//AC - Assign Captain
//AN - Assign number
//P - Points
//F - Foul
//FTI - Free Throw In
//FTO - Free Throw Out
//-------------------

public class ActEvent {
	
	private boolean bHome;
	private int playerPosition;
	private String type;
	private int value;
	private int minute;
	
	public ActEvent(boolean bHome, int position, String type, int value, int minute){
		this.bHome = bHome;
		this.playerPosition = position;
		this.type = type;
		this.value = value;
		this.minute = minute;
	}
	
	// TOSTRING
	@Override
	public String toString(){
		
		if(type.equals("P")){
			return new String("scores "+value+" points.");
		}
		
		if(type.equals("F")){
			return new String("commits "+value+" foul.");
		}
		
		if(type.equals("FTI")){
			return new String("scores "+value+" free thow.");
		}
		
		if(type.equals("FTO")){
			return new String("misses "+value+" free throw.");
		}
		
		return new String("event description");
	}
	
	// METHODS
	
	public boolean isHomePlayer(){
		return bHome;
	}
	
	public int getPlayerPosition(){
		return playerPosition;
	}
	
	public String getType(){
		return type;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public int getMinute(){
		return minute;
	}

}
