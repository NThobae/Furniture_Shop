package classes;

/**
 * The Admin class of the ProgEx project. It contains getter/setter methods for Admin objects.
 * 
 * @version 1.0
 */
public class Admin {

	// Vars
	private int id;
	private String login;
	private String password;

	// Setters/Getters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Constructor
	public Admin(int id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}

	// Methods?
	// Blub
}
