package adm.werock.sportstats.basics;

/**
 * @author Sergiu Daniel Marsavela This Class represents a User that will use
 *         the service.
 */
public class User {

	private String email;
	private String name;
	private String password;

	/**
	 * @param email
	 * @param name
	 * @param password
	 */
	public User(String email, String name, String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
	}

	/**
	 * @param email
	 * @param password
	 */
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	// Setters and Getters

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
