package adm.werock.sportstats;

public class ActProv {
	
	//TODO Hay que aclarar esto. Problemas al leer la informacion del servidor.
	//Se lee toda la informacion o se lee solo lo necesario?
	private String teamHome;
	private String teamGuest;
	private int pointsHome;
	private int pointsGuest;
	private String date;
	
	public ActProv(String teamHome, String teamGuest, int pointsHome,
			int pointsGuest, String date) {
		super();
		this.teamHome = teamHome;
		this.teamGuest = teamGuest;
		this.pointsHome = pointsHome;
		this.pointsGuest = pointsGuest;
		this.date = date;
	}
	public String getTeamHome() {
		return teamHome;
	}
	public void setTeamHome(String teamHome) {
		this.teamHome = teamHome;
	}
	public String getTeamGuest() {
		return teamGuest;
	}
	public void setTeamGuest(String teamGuest) {
		this.teamGuest = teamGuest;
	}
	public int getPointsHome() {
		return pointsHome;
	}
	public void setPointsHome(int pointsLocal) {
		this.pointsHome = pointsLocal;
	}
	public int getPointsGuest() {
		return pointsGuest;
	}
	public void setPointsGuest(int pointsGuest) {
		this.pointsGuest = pointsGuest;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	

}
