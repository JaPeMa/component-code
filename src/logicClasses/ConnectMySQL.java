package logicClasses;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import models.Query;
import api.SaveQuery;
import models.ConnectionData;
import models.QueryType;

public class ConnectMySQL implements Serializable{

	private ConnectionData connectionData;

	private Statement statement = null;
	private ResultSet resultSet = null;
	private transient PropertyChangeSupport propertyChangeListener = new PropertyChangeSupport(this);
	private Connection connection = null;
	
	public ConnectMySQL() {
		this.addPropertyChangeListener(new QueryLogManager());
	}
	
	public ConnectMySQL(ConnectionData connectionData) {
		this.connectionData = connectionData;
		this.addPropertyChangeListener(new QueryLogManager());
	}

	public ConnectionData getConnectionData() {
		return connectionData;
	}

	public void setConnectionData(ConnectionData connectionData) {
		this.connectionData = connectionData;
	}
	
	protected synchronized void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeListener.removePropertyChangeListener(l);
	}

	protected synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeListener.addPropertyChangeListener(l);
	}
	
	public List<List<String>> executeQuery(String query) {
		Date queryMoment = Calendar.getInstance().getTime();
		QueryType queryType = QueryType.valueOf(query.split(" ")[0].toUpperCase());
		switch (queryType) {
			case SELECT:
				return select(query, queryMoment);
				
			case INSERT:
				insert(query, queryMoment);
				break;
			
			case UPDATE:
				updateOrDelete(query, queryMoment, QueryType.UPDATE);
				break;
				
			case DELETE:
				updateOrDelete(query, queryMoment, QueryType.DELETE);
				break;
				
			case CALL: 
				callProcedure(query, queryMoment);
				break;
			default:
				break;
			}
		return null;
	}
	
	public List<List<String>> select(String query, Date date) {
		List<List<String>> result = new ArrayList<List<String>>();
		int numRows;
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			resultSet.last();
			numRows = resultSet.getRow();
			resultSet.beforeFirst();
			
			while (resultSet.next()) {
				List<String> row = new ArrayList<String>();
				
				for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
					row.add(resultSet.getString(resultSet.getMetaData().getColumnName(i)));
				}

				result.add(row);
			}
			
			resultSet.close();
			
			Query queryToSave = new Query(connectionData.getUser(), 
					QueryType.SELECT, query, date, numRows, connectionData.getDatabase());
			
			propertyChangeListener.firePropertyChange("Query", "", queryToSave);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void insert(String query, Date date) {
		try {
			connection.createStatement().executeUpdate(query);
			
			Query queryToSave = new Query(connectionData.getUser(), 
					QueryType.INSERT, query, date, 0, connectionData.getDatabase());
			
			propertyChangeListener.firePropertyChange("Query", "", queryToSave);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateOrDelete(String query, Date date, QueryType queryType) {
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
			
			Query queryToSave = new Query(connectionData.getUser(), 
					queryType, query, date, statement.getUpdateCount(),
					connectionData.getDatabase());
			
			propertyChangeListener.firePropertyChange("Query", "", queryToSave);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void callProcedure(String query, Date date) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			int numRows;
			try {
				numRows = resultSet.getRow();
			} catch (Exception e) {
				numRows = 0;
			}
			
			Query queryToSave = new Query(connectionData.getUser(), 
					QueryType.CALL, query, date, numRows,
					connectionData.getDatabase());
			
			propertyChangeListener.firePropertyChange("Query", "", queryToSave);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Query> searchByDatabaseAndUser(String database, String user) {
		return QueryLogManager.searchByDatabaseAndUser(database, user);
	}
	
	public List<Query> searchByDatabaseAndUserAndType(String database, String user, QueryType type) {
		return QueryLogManager.searchByDatabaseAndUserAndType(database, user, type);
	}
	
	public List<Query> searchByDatabaseAndType(String database, QueryType type) {
		return QueryLogManager.searchByDatabaseAndType(database, type);
	}
	
	public void openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://" + connectionData.getIp() +  
					"/" + connectionData.getDatabase() + connectionData.getTimezone(), 
					connectionData.getUser(), connectionData.getPassword());
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.err.println("Connection Error: " + e);
		}
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Error Closing Connection: " + e);
		}
	}
	
}
