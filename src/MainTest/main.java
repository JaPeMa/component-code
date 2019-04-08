package MainTest;

import java.util.List;

import mySqlManager.MySQLConnect;

public class main {

	public static void main(String[] args) {
		
		MySQLConnect newConnect = new MySQLConnect("localhost:3306","component","root","");
		MySQLConnect newConnect2 = new MySQLConnect("localhost:3306","component2","root","");
		
		
		System.out.println("== DataBase: component - User: root =====");
		List<List> list = newConnect.executeQuery("Select * from firstTry");
		
		for (List<String> list2 : list) {
			String r = "";
			for (String string : list2) {
				r = r + " - " + string;
			}
			System.out.println(r);
		}
		
		newConnect.executeQuery("INSERT INTO firstTry (name, value) VALUES (1, 'Sean')");
		newConnect.executeQuery("INSERT INTO firstTry (name, value) VALUES (2, 'Jordi')");
		newConnect.executeQuery("INSERT INTO firstTry (name, value) VALUES (3, 'Jaime')");
		
		newConnect.executeQuery("update firstTry set value = 'Fran' where value = 'Jaime'");
		
		newConnect.executeQuery("delete from firstTry where value = 'Sean'");

		
		System.out.println("== DataBase: component2 - User: root =====");
		List<List> new1 = newConnect2.executeQuery("Select * from test");
		
		for (List<String> new2 : new1) {
			String r = "";
			for (String string : new2) {
				r = r + "- " + string;
			}
			System.out.println(r);
		}
		
		newConnect2.executeQuery("INSERT INTO test (id, name) VALUES (1, 'Lean')");
		newConnect2.executeQuery("INSERT INTO test (id, name) VALUES (2, 'Maria')");
		
		newConnect2.executeQuery("update test set name = 'Selena' where name = 'Maria'");
		
		
		System.out.println("== DataBase: component2 - User: root =====");
		
		System.out.println("======== Consulta de las querys =========");
		
		newConnect.searchDatabaseType("component2", "insert");
		System.out.println("==========================================");
		newConnect.searchDatabaseUser("component", "root");
		System.out.println("==========================================");
		newConnect.searchDatabaseUserType("component", "root", "insert");
	}

}
