/**
 * This Class represents an Event that happens along the game.
 */
package adm.werock.sportstats.basics;

public class Event {
	
	private int id;
	private int actId;
	private int type;
	private String value;
	private int player;
	
	/**
	 * @param id
	 * @param actId
	 * @param type
	 * @param value
	 */
	public Event(int id, int actId, int type, String value) {
		super();
		this.id = id;
		this.actId = actId;
		this.type = type;
		this.value = value;
	}
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
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
