/**
 * This Class represents an Act of a registered game.
 */
package adm.werock.sportstats.basics;

import java.util.Date;

/**
 * @author SergiuDaniel
 *
 */
public class Act {

	private int id;
	private Date date;
	private String emailUser;
	private int teamHomeId;
	private int idTeamGuest;

	/**
	 * @param id
	 * @param idTeamHome
	 * @param idTeamGuest
	 * @param date
	 * @param emailUser
	 */
	public Act(int id, Date date, String emailUser, int idTeamHome, int idTeamGuest) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

}
