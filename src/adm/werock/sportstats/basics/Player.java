/**
 * This Class represents a Player.
 */
package adm.werock.sportstats.basics;

/**
 * @author SergiuDaniel
 *
 */
public class Player {

	private int licenseNumber;
	private String name;
	private String surname;
	private int teamId;

	/**
	 * @param licenseNumber
	 * @param name
	 * @param surname
	 * @param teamId
	 */
	public Player(int licenseNumber, String name, String surname, int teamId) {
		super();
		this.licenseNumber = licenseNumber;
		this.name = name;
		this.surname = surname;
		this.teamId = teamId;
	}

	public int getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(int licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

}
