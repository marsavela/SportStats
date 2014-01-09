/**
 * This Class represents a User that will use the service.
 */
package adm.werock.sportstats.basics;

/**
 * @author SergiuDaniel
 *
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

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

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
