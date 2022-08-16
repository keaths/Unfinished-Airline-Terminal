package p;

public class Login
{
	int logID;
	String user;
	String password;
	String admin;
	
	public Login(int logID, String user, String password, String admin) {
		super();
		this.logID = logID;
		this.user = user;
		this.password = password;
		this.admin = admin;
	}

	/**
	 * @return the logID
	 */
	public int getLogID() {
		return logID;
	}

	/**
	 * @param logID the logID to set
	 */
	public void setLogID(int logID) {
		this.logID = logID;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the admin
	 */
	public String getAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	
}
