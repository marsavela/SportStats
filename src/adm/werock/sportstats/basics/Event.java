package adm.werock.sportstats.basics;

/**
 * @author Sergiu Daniel Marsavela This Class represents an Event that happens
 *         along the game.
 */

// Event types
// -------------------
// A - Assign number
// S - Score
// F - Foul
// FI - Free Throw In
// FO - Free Throw Out
// AS - Assign Starter
// AC - Assign Captain
// AN - Assign Number
// MCH - Main Coach Home Team
// SCH - Secondary Coach Home Team
// MCG - Main Coach Guest Team
// SCG - Secondary Coach Guest Team
// TW - Town
// MR - Main Referee
// SR - Secondary Referee
// -------------------

public class Event {

	private int id;
	private int actId;
	private int minute;
	private String type;
	private String value;
	private int player;

	/**
	 * @param id
	 * @param actId
	 * @param minute
	 * @param type
	 * @param value
	 * @param player
	 */
	public Event(int id, int actId, int minute, String type, String value,
			int player) {
		super();
		this.id = id;
		this.actId = actId;
		this.minute = minute;
		this.type = type;
		this.value = value;
		this.player = player;
	}

	// Setters and Getters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

}
