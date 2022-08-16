package p;

public class AdminLog 
{
	int logID;
	int flightNum;
	int LogActivity;
	
	AdminLog(int logID1, int flightNum1, int LogActivity1)
	{
		logID = logID1;
		flightNum = flightNum1;
		LogActivity = LogActivity1;
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
	 * @return the flightNum
	 */
	public int getFlightNum() {
		return flightNum;
	}

	/**
	 * @param flightNum the flightNum to set
	 */
	public void setFlightNum(int flightNum) {
		this.flightNum = flightNum;
	}

	/**
	 * @return the logActivity
	 */
	public int getLogActivity() {
		return LogActivity;
	}

	/**
	 * @param logActivity the logActivity to set
	 */
	public void setLogActivity(int logActivity) {
		LogActivity = logActivity;
	}
	
	public void findUser()
	{
		
	}

}
