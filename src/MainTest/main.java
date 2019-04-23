package MainTest;

import java.util.List;

import api.MySQLConnect;
import logicClasses.ConnectMySQL;
import models.ConnectionData;
import models.QueryType;

public class main {

	public static void main(String[] args) {

		ConnectMySQL databaseConnection = new ConnectMySQL(new ConnectionData("localhost:3306", "component2", "root", ""));
		databaseConnection.openConnection();
		databaseConnection.executeQuery("select * from test");
		databaseConnection.executeQuery("INSERT INTO test (id, name) VALUES (1, 'Sean')");
		databaseConnection.executeQuery("update test set name = 'Carlos' where name = 'Sean'");
		databaseConnection.executeQuery("delete from test where name = 'Carlos'");
		databaseConnection.executeQuery("call insertAdmin()");
		databaseConnection.searchByDatabaseAndUser("component2", "root");
		System.out.println("\n\n\n");
		databaseConnection.searchByDatabaseAndUserAndType("component2", "root", QueryType.SELECT);
		System.out.println("\n\n\n");
		databaseConnection.searchByDatabaseAndType("component2", QueryType.INSERT);
		databaseConnection.closeConnection();
	}

}
