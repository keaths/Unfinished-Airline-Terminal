package p;

import java.util.ArrayList;

public class AvailableFlight 
{
	int id;
	String origin;
	String destination;
	String date;
	String time;
	double price;
	int economySeats;
	int firstClassSeats;
	
	AvailableFlight(int id1, String origin1,String destination1,String date1,String time1,
			double price1,int economySeats1,int firstClassSeats1)
	{
		id = id1;
		origin = origin1;
		destination = destination1;
		date = date1;
		time = time1;
		price = price1;
		economySeats = economySeats1;
		firstClassSeats = firstClassSeats1;
	}
	
	public String toString()
	{
		return String.format("%-15s%-15s%-15s%-15s%-15s%-15s%s", origin,destination,date,time,price,economySeats,firstClassSeats);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the economySeats
	 */
	public int getEconomySeats() {
		return economySeats;
	}

	/**
	 * @param economySeats the economySeats to set
	 */
	public void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}

	/**
	 * @return the firstClassSeats
	 */
	public int getFirstClassSeats() {
		return firstClassSeats;
	}

	/**
	 * @param firstClassSeats the firstClassSeats to set
	 */
	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}
	
	
}
