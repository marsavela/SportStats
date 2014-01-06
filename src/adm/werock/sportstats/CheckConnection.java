package adm.werock.sportstats;



public class CheckConnection {
	public boolean isWifi;
	public boolean is3g;
	
	public void CheckConnection(){
		isWifi = true;
		is3g = true;
	}
	public void setWifi(boolean wifi){
		//For WiFi Check
		isWifi = wifi;
	}
	public boolean getWifi(){
		return isWifi;
	}
	public void setThreeG(boolean threeG){
		//For 3G check
		is3g = threeG;
	}
	public boolean getThreeG(){
		return is3g;
	}
	
}
