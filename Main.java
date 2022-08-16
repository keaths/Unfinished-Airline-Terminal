/**
 * This program serves as a mock-airline booking software, with a simulated
 * database (CSV/Excel) to retrieve user information and perform necessary functions.
 * 
 * @auther: Keath Sawdo
 */

package p;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Main 
{	
	public static int i = 0;
	public static int j = 0;
	public static int k = 0;
	public static int l = 0;
	public static int m = 0;
	public static int counter = 0;
	public static ArrayList<Integer> indexes = new ArrayList<>();
	
	
	public static void main(String[] args) throws IOException 
	{
		Scanner scan = new Scanner(System.in);
		int choice;
		
		
					//Creating file path and writer for subsequent editing
		
		File AccFile = new File ("AirLineCSVs/AccountDetails.csv");
		FileOutputStream PW = new FileOutputStream(AccFile, true);
		PrintStream Awriter = new PrintStream(PW);
		
		
					//Creating file path and writer for subsequent editing
		
		File FlightFile = new File ("AirLineCSVs/AvailableAirLines.csv");	
		FileOutputStream FW = new FileOutputStream(FlightFile, true);
		PrintStream Fwriter = new PrintStream(FW);
		
		
					//Creating file path and writer for subsequent editing
		
		File BookedFile = new File ("AirLineCSVs/BookedFlights.csv");	
		FileOutputStream BFW = new FileOutputStream(BookedFile, true);
		PrintStream BFwriter = new PrintStream(BFW);
		
					//Creating file path and writer for subsequent editing
		
		File LogFile = new File ("AirLineCSVs/AdminLogs.csv");	
		FileOutputStream LW = new FileOutputStream(LogFile, true);
		PrintStream Lwriter = new PrintStream(LW);
		
		
					//scanners used to iterate through csv files
		Scanner v = new Scanner (new File ("AirLineCSVs/AdminLogs.csv"));
		Scanner x = new Scanner (new File ("AirLineCSVs/AvailableAirLines.csv"));
		Scanner y = new Scanner (new File ("AirLineCSVs/BookedFlights.csv"));
		Scanner z = new Scanner (new File ("AirLineCSVs/AccountDetails.csv"));
		
		v.useDelimiter(",|\n");
		x.useDelimiter(",|\n");
		y.useDelimiter(",|\n");
		z.useDelimiter(",|\n");
		
		
					//creating hashtables and arraylists for storing csv information
		
		ArrayList<Login> AccLogins = new ArrayList<Login>();
		ArrayList<BookedFlight> Bflights = new ArrayList<BookedFlight>();
		ArrayList<AvailableFlight> Aflights = new ArrayList<AvailableFlight>();
		ArrayList<AdminLog> ALogs = new ArrayList<AdminLog>();
		
		
					//setting current login credentials to "null" for further assesment after scanning
		
		int currentID = 0;
		String currentUser = "";
		String currentPass = "";
		
		
					//scanning csv files to begin storing information into Logins hashtables
					//and array lists
				
		ArrayListFill(x, y, v, z, Aflights, null, null, null, true, null, null, null);
		ArrayListFill(x, y, v, z, null, Bflights, null, null, true, null, null, null);
		ArrayListFill(x, y, v, z,  null, null, ALogs, null, true, null, null, null);
		ArrayListFill(x, y, v, z,  null, null, null, AccLogins, true, null, null, null);
				
		
		System.out.print("1)login\n2)Create Account\n\nChoose: ");
		choice = scan.nextInt();
		boolean success = false;
		boolean administrator = false;
		
		
		/**
		 * This sequence is responsible for handling whether the user decides to
		 * login or create a new account, and sets the variables for the current
		 * user for subsequent searching and writing.
		 * 
		 */
		switch(choice)
		{
			case 1:
			{
				
						//declaring repeat boolean for failed login, as well as a repeat counter
						//to track failed attempts, booting user from program after 3 failed attempts
				
				boolean repeat = true;				
				int attempts = 3;
				
				while(repeat)
				{
					System.out.print("\n\nusername: " );
					String username = scan.next();					
					
					System.out.print("password: " );
					String password = scan.next();
					
					String r = loginProcess(AccLogins, currentID, username, password);	
					
					
							//checking the returned index of r for the * character, indicating an
							//admin account
					
					if(r.equals("-1"))
					{						
						attempts--;
						System.out.println("attempts left: " + attempts);
					}
										
							//setting the administrator boolean to true if the * character is detected,
							//allowing the user to bypass into the AdminService() function rather than
							//the RegularService
										
					if(r.contains("1") && r.contains("*"))
					{
						if(!r.contains("-"))
						{
							administrator = true;	
						}

						currentID = counter; 
						currentUser = username;
						currentPass = password;
						repeat = false;
						success = true;						
					}
					
							//boots user from program after the repeat counter is decremented
							//to 0.
					
					if(attempts == 0)
					{
						System.out.println("Maximum attempts exceeded. Exiting program.");
						success = false;
						break;
					}
				}
				break;
			}
					
			case 2:
			{
				boolean repeat = true;
				while(repeat)
				{
					
					//the account creation option offers the user to create either an admin or
					//user account.
					
					System.out.println("1)Admin\n2)Regular\n");
					System.out.print("Choose account type: ");
					String admin = "";
					
					int choice1 = scan.nextInt();
					if(choice1 == 1)
					{
						admin = "y";
					}
					else
					{
						admin = "n";
					}
					System.out.print("\n\nusername: " );
					String username = scan.next();
					
					System.out.print("\n\npassword: " );
					String password = scan.next();		
					int test = createAccProcess(AccLogins, Awriter, username, password, x, i, admin);
					if(test != -1)				
					{					
						currentID = test;
						currentUser = username;
						currentPass = password;
						repeat = false;
						success = true;
						if(admin.equals("y"))
						{
							administrator = true;
						}
						else
						{
							administrator = false;
						}
					}
				}
				break;
			}
		}
		
		
		//regular service activated if the administrator check is failed
		
		if(success && !administrator)
		{
			RegularService(Aflights, Bflights, AccLogins, BFwriter, Fwriter, currentID, currentUser, x, y, z, v);
		}
		
		//administrator service activated if the administrator check is passed
		
		if(success && administrator)
		{
			System.out.println("admin");
			AdminService(Aflights,AccLogins, currentUser,currentID,z, x, y, v,Fwriter,Lwriter, ALogs);
		}
		scan.close();
				
	}	 
	
	
	
	
	/**
	 * 
	 * This function is responsible for filling the array list containing the currently available flights.
	 * An array list was used in place of a hashtable due to the larger amount of criteria to which an
	 * available flight could be hashed with, making searching for it too particular (i.e., hashing with
	 * date & origin when user may search utilizing destination and price)
	 * 
	 * 
	 * @param x: scanner utilized for the AvailableAirlines.csv
	 * @param Aflights: array list storing all of the AvailableFlight objects
	 * @return
	 */
	public static ArrayList<AvailableFlight> ArrayListFill(Scanner x, Scanner y, Scanner v, Scanner z, ArrayList<AvailableFlight> Aflights,
														ArrayList<BookedFlight> Bflights, ArrayList<AdminLog> ALogs, ArrayList<Login>AccLogs,
														Boolean firstCheck, AvailableFlight newF, BookedFlight BF, AdminLog Alog)
	{
		if(Aflights != null)
		{
			if(firstCheck)
			{
				while(x.hasNext())
				{
					x.next();
					String origin = x.next();
					String destination = x.next();
					String date = x.next();
					String time = x.next();
					double price = x.nextDouble();
					int economySeats = x.nextInt();
					int firstClassSeats = x.nextInt();
					
					AvailableFlight AF = new AvailableFlight(j,origin,destination,date,time,price,
															economySeats,firstClassSeats);
					Aflights.add(AF);
					if(x.next().equals("\r")) 
					{
						
					}
					j++;
				}
			}
			else
			{
				j++;
				Aflights.add(newF);	
			}
			return Aflights;
		}
		if(Bflights != null)			
		{			
			if(firstCheck)
			{
				while(y.hasNext())
				{
					y.next();
					int Fid = y.nextInt();
					int userID = y.nextInt();
					String username = y.next();
					int classChoice = y.nextInt();
					
					BookedFlight BF1 = new BookedFlight(j, Fid, userID, username, classChoice);
					Bflights.add(BF1);
					if(y.next().equals("\r"))
					{
						
					}
					k++;
				}
			}
			else
			{
				k++;
				Bflights.add(BF);
			}
		}
		if(ALogs != null)
		{
			if(firstCheck)
			{
				while(v.hasNext())
				{
					int entry = v.nextInt();
					int flightNum = v.nextInt();
					int LogActivity = v.nextInt();					
					
					AdminLog AL1 = new AdminLog(entry, flightNum, LogActivity);
					ALogs.add(AL1);
					if(v.next().equals("\r"))
					{
						
					}
					l++;
				}
			}
			else
			{
				l++;
				ALogs.add(Alog);
			}
		}
		if(AccLogs != null)
		{
			if(firstCheck)
			{
				while(z.hasNext())
				{
					z.next();
					String user = z.next();
					String pass = z.next();
					String admin = z.next();
					admin.replace("\r", "");
					
					Login L1 = new Login(m, user, pass, admin);
					AccLogs.add(L1);
					if(!z.hasNext())
					{
						
					}
					m++;
				}
			}
			else
			{
				m++;
				//AccLogs.add(L1);
			}
		}
		
		return null;
	}
	
	
	/**
	 * 
	 * This function successfully edits the listings of avaialble flights after purchasing and refunding tickets.
	 * 
	 * @param choice
	 * @param classChoice
	 * @param Stuff
	 * @param Aflights
	 * @param Fwriter
	 * @throws FileNotFoundException
	 */
	public static void ArrayListEdit(int choice, int classChoice, ArrayList<AvailableFlight> Stuff, 
									ArrayList<AvailableFlight> Aflights, PrintStream Fwriter) 
											throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(new FileOutputStream("AirlineCSVs/AvailableAirlines.csv",false));
		for(int i = 0; i < Aflights.size(); i++)
		{
			pw.println(Aflights.get(i).getId() + "," + Aflights.get(i).getOrigin() + "," + Aflights.get(i).getDestination() + ","
					+ Aflights.get(i).getDate() + "," + Aflights.get(i).getTime() + "," + Aflights.get(i).getPrice() + "," + 
					+ Aflights.get(i).getEconomySeats() + "," + Aflights.get(i).getFirstClassSeats() + ",");
		}
		pw.close();
	}
		
	
	
	/**
	 * This function is responsible for handling the login process, and
	 * returning the Login ID of the user if found for subsequent searches
	 * 
	 * @param Logins: Hasharray of all listed login details
	 */
	public static String loginProcess(ArrayList<Login> Logins, int currentID, String username, String password)
	{
		for(int i = 0; i < Logins.size(); i++)
		{
			if(Logins.get(i).getUser().equals(username)&& Logins.get(i).getAdmin().equals("n\r"))
			{
				return "-1*";
			}
			if(Logins.get(i).getUser().equals(username) && Logins.get(i).getAdmin().equals("y\r"))
			{
				return "1*";
			}
			counter ++;
		}
		return "-1";
	}
	
	
	/**
	 * This funciton is responsible for handling the account creation process,
	 * as well as inserting it into the csv file and hasharray for subsequent
	 * searches and future logins.
	 * 
	 * @param Logins: Hasharray of all listed login details
	 * @throws IOException 
	 */
	public static int createAccProcess(ArrayList<Login> Logins, PrintStream PW, String username,
										String password, Scanner x, int i, String Admin) 
												throws IOException
	{	
		boolean found = false;
		for(int j = 0; j < Logins.size(); j++)
		{
			if(Logins.get(j).getUser().equals(username))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			if(!x.hasNext())
			{
				String newLogin = i + "," + username + "," + password + "," + Admin;
				PW.println(newLogin);
				PW.close();
			}
		}
		if(found)
		{
			System.out.println("Someone already has this information, please try something else\n");
			return -1;
		}
		return i;	
		
	}
	
	
	/**
	 * This function handles the brunt of the services of the airline services for non-admin users.
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void RegularService(ArrayList<AvailableFlight>Aflight, ArrayList<BookedFlight>Bflight, ArrayList<Login> AccLogs, 
										PrintStream BFwriter, PrintStream Fwriter, int currentID, String currentUser, Scanner x, 
										Scanner y, Scanner v, Scanner z) throws FileNotFoundException
	{
		int choice = 0;
		while(choice != 4)
		{
			Scanner scan = new Scanner (System.in);
			
			System.out.println("1)book flights\n2)view my booked flights\n3)view upcoming flights");
			System.out.print("\nEnter your choice: ");
			
			choice = scan.nextInt();
			System.out.println();
			switch(choice)
			{
				case 1:
				{
					bookFlight(Aflight, Bflight, BFwriter, Fwriter, currentID, currentUser, x ,y, v, z);
					break;
				}
				case 2:
				{					
					ArrayList <AvailableFlight> j = findBooked(currentID, Bflight, Aflight);
					DisplayList(j, true);
					break;
				}
				case 3:
				{
					System.out.println("3");
					break;
				}
				case 4:
				{
					System.out.println("bye!");
				}
			}
		}
		return;
	}
	
	
	/**
	 * this function is responsible for handling the services of administrators of the program.
	 * 
	 */
	public static void AdminService(ArrayList<AvailableFlight>Aflight, ArrayList<Login>AccLogins, String username, 
									int id, Scanner z, Scanner x, Scanner y, Scanner v, 
									PrintStream Fwriter, PrintStream Lwriter, ArrayList<AdminLog>Alog)
	{
		Scanner scan = new Scanner (System.in);
		int choice = -1;
		while(choice != 4)
		{
			System.out.println("\n1)add/remove flights\n2)view administrator logs\n3)admin stuff\n4)exit");
			System.out.print("\nEnter your choice: ");
			
			choice = scan.nextInt();
			
			switch(choice)
			{
				case 1:
				{
					addFlight(Aflight, username, id, x, y, v, z, Fwriter, Lwriter, Alog);
					break;
				}
				case 2:
				{
					viewLog(Alog, Aflight, AccLogins);
					break;
				}
				case 3:
				{
					System.out.println("3");
					break;
				}
			}
		}
		return;
	}
	
	
	/**
	 * This function is responsible for handling the processes involving a non-admin user booking
	 * a flight. Details regarding this purchase will be written into the booked flights csv file
	 * for subsequent writing and searching.
	 * @throws FileNotFoundException 
	 * 
	 */
	public static ArrayList<AvailableFlight> bookFlight(ArrayList<AvailableFlight> Aflights, ArrayList<BookedFlight>Bflight,
													PrintStream BFwriter, PrintStream Fwriter, int currentID, String currentUser, 
													Scanner x, Scanner y, Scanner v, Scanner z) throws FileNotFoundException
	{
		Scanner scan = new Scanner(System.in);
		ArrayList<AvailableFlight> FFlights = new ArrayList<>();
		System.out.println("1)Origin\n2)Destination\n3)Price\n4)Date");
		System.out.print("\nPlease enter your searching option: ");
		int choice = scan.nextInt();
		switch(choice)
		{
			case 1:
			{
				System.out.print("\nEnter origin: ");
				String origin = scan.next();
				searchFlight(choice, origin, Aflights, FFlights);
				DLBook(Aflights, FFlights, Bflight, BFwriter, Fwriter, currentID, currentUser, x, y, v, z);								
				break;
			}
			case 2:
			{
				System.out.print("\nEnter Destination: ");
				String destination = scan.next();
				searchFlight(choice, destination, Aflights, FFlights);
				DLBook(Aflights, FFlights, Bflight, BFwriter, Fwriter, currentID, currentUser, x, y, v, z);								
				break;
			}
			case 3:
			{
				System.out.print("\nEnter Price: ");
				String price = scan.next();
				searchFlight(choice, price, Aflights, FFlights);
				DLBook(Aflights, FFlights, Bflight, BFwriter, Fwriter, currentID, currentUser, x, y, v, z);								
				break;
			}
			case 4:
			{
				System.out.print("\nEnter Date: ");
				String date = scan.next();
				searchFlight(choice, date, Aflights, FFlights);
				DLBook(Aflights, FFlights, Bflight, BFwriter, Fwriter, currentID, currentUser, x, y, v, z);								
				break;
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * This function successfully searches throughout the arraylist for flights matching the user's
	 * criteria, and stores fitting flights into the FFlight arraylist.
	 * 
	 * @param criteria: 
	 * @param Aflights:
	 * @param FFlights:
	 * @return
	 */
	public static ArrayList<AvailableFlight> searchFlight(int choice, String criteria, ArrayList<AvailableFlight> Aflights, 
															ArrayList<AvailableFlight> FFlights)
	{
		switch(choice)
		{
			case 1:		
			{
				boolean FlightsFound = false;
				
				for(int i = 0; i < Aflights.size(); i++)
				{
					if(Aflights.get(i).origin.equals(criteria))
					{
						FFlights.add(Aflights.get(i));
						indexes.add(i);
						FlightsFound = true;
					}
				}
				if(FlightsFound)
				{
					return FFlights;
				}
				break;
			}
			
			case 2:
			{
				boolean FlightsFound = false;
				
				for(int i = 0; i < Aflights.size(); i++)
				{
					if(Aflights.get(i).destination.equals(criteria))
					{
						FFlights.add(Aflights.get(i));
						indexes.add(i);
						FlightsFound = true;
					}
				}
				if(FlightsFound)
				{
					return FFlights;
				}
			
			break;
			}
			
			case 3:
			{				
				boolean FlightsFound = false;
					
				for(int i = 0; i < Aflights.size(); i++)
				{
					if(Aflights.get(i).price <= Double.parseDouble(criteria))
					{
						FFlights.add(Aflights.get(i));
						indexes.add(i);
						FlightsFound = true;
					}
				}
				if(FlightsFound)
				{
					return FFlights;
				}
			break;
			}
			
			case 4:
			{					
				boolean FlightsFound = false;
						
				for(int i = 0; i < Aflights.size(); i++)
				{
					if(Aflights.get(i).date.equals(criteria))
					{
						FFlights.add(Aflights.get(i));
						indexes.add(i);
						FlightsFound = true;
					}
				}
				if(FlightsFound)
				{
					return FFlights;
				}					
			break;
			}
			
		}
		return FFlights;
	}
	
	
	/**
	 * 
	 * This function is responsible for handling the processes involving an admin adding a new flight
	 * To the AvailableAirlines.csv file for regular users to view and book flights with.
	 * 
	 */
	public static void addFlight(ArrayList<AvailableFlight>Aflight, String username, 
								int id, Scanner x, Scanner y, Scanner v, Scanner z, PrintStream Fwriter, 
								PrintStream Lwriter, ArrayList<AdminLog> AL)
	{
		Scanner scan = new Scanner (System.in);
		StringBuilder Awrite = new StringBuilder();
		StringBuilder Lwrite = new StringBuilder();
		
		System.out.print("Origin: ");
		String origin = scan.next();
		Awrite.append(origin + ",");
		
		System.out.print("Destination: ");
		String Destination = scan.next();
		Awrite.append(Destination + ",");
		
		System.out.print("Date (mm/dd/yyyy format): ");
		String Date = scan.next();
		Awrite.append(Date + ",");
		
		System.out.print("Time (HH:MM format): ");
		String Time = scan.next();
		Awrite.append(Time + ",");
		
		System.out.print("Price (##:## format): ");
		Double Price = scan.nextDouble();
		Awrite.append(Price + ",");
		
		System.out.print("Available Economy seats: ");
		int EconomySeats = scan.nextInt();
		Awrite.append(EconomySeats + ",");
		
		System.out.print("Available First Class seats: ");
		int FirstClassSeats = scan.nextInt();
		Awrite.append(FirstClassSeats + ",");
		
		AvailableFlight nF = new AvailableFlight(j,origin,Destination,Date,Time,Price,EconomySeats,FirstClassSeats);
		AdminLog Alog = new AdminLog(id,j,1);
		
		ArrayListFill(x, y, v, z, Aflight, null, null, null, false, nF, null, null);
		ArrayListFill(x, y, v, z, null, null, AL, null, false, null, null, Alog);
		
		int test = j - 1;
		Fwriter.println(test + "," + Awrite);
		Lwriter.println(id + "," + test  + "," + "1" + ",");
		System.out.println("\nFlight Added!");
	}	
	
	
	/**
	 * 
	 * This function displays all of the currently available flights witin the AvailableFlights array list
	 * and displays it's contents neatly to the user for further utilization.
	 * 
	 * @param Flights
	 * @throws FileNotFoundException 
	 */
	public static void DLBook(ArrayList<AvailableFlight> Aflights, ArrayList<AvailableFlight> Stuff, ArrayList<BookedFlight>Bflight,
					PrintStream BFwriter, PrintStream Fwriter, int currentID, String currentUser, Scanner x, Scanner y, Scanner v, Scanner z) throws FileNotFoundException
	{
		Scanner scan = new Scanner(System.in);
		System.out.println();
		
		DisplayList(Stuff, false);
		
		System.out.print("\nSelect which flight you'd like to book: ");
		int flightChoice = scan.nextInt();
		int AcflightChoice = indexes.get(flightChoice);
		indexes.clear();
		
		System.out.println("\n\n1)Economy\n2)First Class\n");
		System.out.print("Select class: ");
		int classChoice = scan.nextInt();
		
		BFwriter.println("0," + AcflightChoice + "," + currentID + "," + currentUser + "," + classChoice + ",");

		if(classChoice == 1)
		{
			int g = Stuff.get(flightChoice).getEconomySeats();
			g=g-1;
			Aflights.get(flightChoice).setEconomySeats(g);			
		}
		else
		{
			int g = Stuff.get(flightChoice).getFirstClassSeats();
			g=g-1;
			Aflights.get(flightChoice).setFirstClassSeats(g);	
		}
		BookedFlight BF = new BookedFlight(k,flightChoice,currentID,currentUser,classChoice);
		
		ArrayListEdit(flightChoice, classChoice, Stuff, Aflights, Fwriter);
		ArrayListFill(x, y, v, z, null, Bflight, null, null, false, null, BF, null);
		
		System.out.println("Flight booked, " + currentUser + "!\n");
	}
	
	
	/**
	 * 
	 * This function locates all the flights that have been booked by the current user
	 * 
	 * @param currID: current login ID
	 * @param BookedFlight: Array list of booked flights
	 * @param AFlights: Array list of avaialbe flights
	 * @return
	 */
	public static ArrayList<AvailableFlight> findBooked(int currID, ArrayList<BookedFlight>BookedFlight, ArrayList<AvailableFlight>AFlights)
	{
		ArrayList<AvailableFlight>userFlights = new ArrayList<>();
		for(int i = 0; i < BookedFlight.size(); i++)
		{
			if(BookedFlight.get(i).getUserID() == currID)
			{
				userFlights.add(AFlights.get(BookedFlight.get(i).getFlightID()));
			}
		}
		return userFlights;
	}
	
	
	/**
	 * 
	 * This function serves to display the contents of available flights in a neat manner
	 * 
	 * @param Stuff: Array list of available flights
	 */
	public static void DisplayList(ArrayList<AvailableFlight> Stuff, Boolean checkBooked)
	{
		if(!checkBooked)
		{
			System.out.println((String.format("%-6s%-14s%-15s%-16s%-14s%-16s%-15s%s"
					+ "\n--------------------------------------------------"
					+ "-------------------------------------------------------------"
					+ "", "ID","Origin","Destination","Date","Time","Price",
					"Economy Seats","FirstClassSeats")));
			for(int i = 0; i < Stuff.size(); i++)
			{
				System.out.println(String.format("%-5d%-15s%-15s%-15s%-15s%-17s%-17s%-15s",
						i,Stuff.get(i).getOrigin(),Stuff.get(i).getDestination(),Stuff.get(i).getDate(),
						Stuff.get(i).getTime(),Stuff.get(i).getPrice(),Stuff.get(i).getEconomySeats(),
						Stuff.get(i).getFirstClassSeats()));			
			}
			System.out.println("--------------------------------------------------"
								+ "-------------------------------------------------------------"
								+"\n");
		}
		else
		{
			System.out.println();
			System.out.println((String.format("%-14s%-15s%-16s%-14s%-16s"
					+ "\n------------------------------------------------"
					+ "------------------"
					+ "", "Origin","Destination","Date","Time","Price")));
			
			for(int i = 0; i < Stuff.size(); i++)
			{
				System.out.println(String.format("%-14s%-15s%-15s%-15s%-15s",
						Stuff.get(i).getOrigin(),Stuff.get(i).getDestination(),Stuff.get(i).getDate(),
						Stuff.get(i).getTime(),Stuff.get(i).getPrice()));		
			}
			System.out.println("------------------------------------------------"
								+"------------------"
								+"\n");
		}
	}
	
	public static void viewLog(ArrayList<AdminLog> Alog, ArrayList<AvailableFlight>Aflights, ArrayList<Login>AccLogins)
	{ 
		System.out.println("\n--------------------------------------");
		for(int i = 0; i < Alog.size(); i++)
		{
			System.out.println("- " + AccLogins.get(Alog.get(i).getLogID()).getUser() + " created flight entry " + Aflights.get(Alog.get(i).getFlightNum()).getId());
			System.out.println("--------------------------------------");
		}		
	}
	
}




