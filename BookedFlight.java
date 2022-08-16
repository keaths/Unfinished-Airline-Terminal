package p;

public class BookedFlight 
{
	int BFID;
	int flightID;
	int userID;
	String username;
	int classChoice;
	
	BookedFlight(int BFID1, int flightID1,int userID1,String username1,int classChoice1)
	{
		BFID = BFID1;
		flightID = flightID1;
		userID = userID1;
		username = username1;
		classChoice = classChoice1;
	}

	/**
	 * @return the bFID
	 */
	public int getBFID() {
		return BFID;
	}

	/**
	 * @param bFID the bFID to set
	 */
	public void setBFID(int bFID) {
		BFID = bFID;
	}

	/**
	 * @return the flightID
	 */
	public int getFlightID() {
		return flightID;
	}

	/**
	 * @param flightID the flightID to set
	 */
	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
