/**
 * 
 */
package adm.werock.sportstats.basics;

/**
 * @author SergiuDaniel
 *
 */
public class League {
	
	private int leagueId;
	private String name;
	
	/**
	 * @param leagueId
	 * @param name
	 */
	public League(int leagueId, String name) {
		super();
		this.leagueId = leagueId;
		this.name = name;
	}

	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
