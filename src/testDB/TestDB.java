package testDB;

import java.awt.EventQueue;

import java.io.IOException;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import util.DerbyConnection;



public class TestDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					runTest();
				}catch(SQLException e){
					for(Throwable t:e) t.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void runTest() throws SQLException,IOException{
		DerbyConnection dCon=new DerbyConnection();
		try (Connection conn=dCon.getConnection()){
			Statement stat=conn.createStatement();
			stat.executeUpdate("CREATE TABLE Greetings (Message VARCHAR(20))");
			stat.executeUpdate("INSERT INTO Greetings (Message) VALUES ('ÊÀ½ç£¬ÄãºÃ£¡')");
			ResultSet rs=stat.executeQuery("SELECT Message FROM Greetings");			
			while(rs.next()) System.out.println(rs.getString(1));
			rs.close();
			stat.executeUpdate("DROP TABLE Greetings");
		}
	}
		
}
