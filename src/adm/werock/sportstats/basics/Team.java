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
	
	/**
	 * @param teamId
	 * @param name
	 */
	public Team(int teamId, String name) {
		super();
		this.teamId = teamId;
		this.name = name;
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
	
}
