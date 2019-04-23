package logicClasses;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import models.Query;
import models.QueryType;

public class QueryLogManager implements PropertyChangeListener {

	private static List<Query> queryList = new ArrayList<Query>();
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		queryList.add((Query) arg0.getNewValue());
	}
	
    public static List<Query> searchByDatabaseAndUser(String database, String user) {
    	List<Query> filteredQueryList = new ArrayList<Query>();
    	for (Query query : queryList) {
    		if (query.getDatabase().equals(database) && query.getUser().equals(user)) {
    			filteredQueryList.add(query);
    			System.out.println(query.getQuery() + " - " + query.getFormattedDate() + " - " + query.getTypeQuery());
    		}
    	}
    	return filteredQueryList;
    }
    
    public static List<Query> searchByDatabaseAndUserAndType(String database, String user, QueryType type) {
    	List<Query> filteredQueryList = new ArrayList<Query>();
    	for (Query query : queryList) {
    		if (query.getDatabase().equals(database) && query.getUser().equals(user) && query.getTypeQuery() == type) {
    			filteredQueryList.add(query);
    			System.out.println(query.getQuery() + " - " + query.getFormattedDate());
    		}
    	}
    	return filteredQueryList;
    }
    
    public static List<Query> searchByDatabaseAndType(String database, QueryType type) {
    	List<Query> filteredQueryList = new ArrayList<Query>();
    	for (Query query : queryList) {
    		if (query.getDatabase().equals(database) && query.getTypeQuery() == type) {
    			filteredQueryList.add(query);
    			System.out.println(query.getQuery() + " - " + query.getFormattedDate() + " - " + query.getUser());
    		}
    	}
    	return filteredQueryList;
    }

}
