/**
 * This Class represents a Team.
 */
package adm.werock.sportstats.basics;

/**
 * @author SergiuDaniel
 *
 */
public class Team {
	
	private int teamId;
	private String name;
	private int leagueId;
	
	/**
	 * @param teamId
	 * @param name
	 * @param leagueId
	 */
	public Team(int teamId, String name, int leagueId) {
		super();
		this.teamId = teamId;
		this.name = name;
		this.leagueId = leagueId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	
}
