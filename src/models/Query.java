package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Query {
	
	private String user;
	private QueryType typeQuery;
	private String query;
	private Date date;
	private int numRows;
	private String database;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public Query(String user, QueryType typeQuery, String query, Date date, int numRows, String database) {

		this.user = user;
		this.typeQuery = typeQuery;
		this.query = query;
		this.date = date;
		this.numRows = numRows;
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public QueryType getTypeQuery() {
		return typeQuery;
	}

	public void setTypeQuery(QueryType typeQuery) {
		this.typeQuery = typeQuery;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Date getDate() {
		return date;
	}
	
	public String getFormattedDate() {
		return df.format(this.date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	@Override
	public String toString() {
		
		String newDate= df.format(this.date);
		
		return "Query [userName=" + user + ", typeQuery=" + typeQuery + ", query=" + query + ", date=" + newDate
				+ ", numRows=" + numRows + ", database=" + database + "]";
	}
	
	
	
}
