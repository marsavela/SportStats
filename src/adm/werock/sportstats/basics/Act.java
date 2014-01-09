/**
 * This Class represents an Act of a registered game.
 */
package adm.werock.sportstats.basics;

import java.sql.Timestamp;

/**
 * @author SergiuDaniel
 *
 */
public class Act {
	
	private int id;
	private int teamHomeId;
	private int idTeamGuest;
	private Timestamp date;
	private String emailUser;
	
	/**
	 * @param id
	 * @param idTeamHome
	 * @param idTeamGuest
	 * @param date
	 * @param emailUser
	 */
	public Act(int id, int idTeamHome, int idTeamGuest, Timestamp date,
			String emailUser) {
		super();
		this.id = id;
		this.teamHomeId = idTeamHome;
		this.idTeamGuest = idTeamGuest;
		this.date = date;
		this.emailUser = emailUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdTeamHome() {
		return teamHomeId;
	}

	public void setIdTeamHome(int idTeamHome) {
		this.teamHomeId = idTeamHome;
	}

	public int getIdTeamGuest() {
		return idTeamGuest;
	}

	public void setIdTeamGuest(int idTeamGuest) {
		this.idTeamGuest = idTeamGuest;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

}
