package exec;

import java.awt.EventQueue;
import java.io.*;
import java.util.*;

import util.DerbyConnection;

import java.sql.*;

/**
 * Executes all SQL statements in a file. Call this program as <br>
 * java -classpath driverPath:. ExecSQL commandFile
 * 
 * @version 1.30 2004-08-05
 * @author Cay Horstmann
 */
class ExecSQL {
	public static void main(String args[]) {
		
		String startDB="java -jar d://java//jdk1.8.0_31//db//lib//derbyrun.jar server start";
		String shutdownDB="java -jar d://java//jdk1.8.0_31//db//lib//derbyrun.jar server shutdown";

		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					Runtime.getRuntime().exec(startDB);
					runExecSQL(args);
					Runtime.getRuntime().exec(shutdownDB);
				}catch (SQLException e){
					for(Throwable t:e) t.printStackTrace();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void runExecSQL(String[] args) throws SQLException, IOException{
		
		Scanner in;
		if(args.length==0) in=new Scanner(System.in);
		else in=new Scanner(new File(args[0]));
		DerbyConnection dCon=new DerbyConnection();
		try(Connection conn=dCon.getConnection()){
			Statement stat=conn.createStatement();
			while(true){
				if(args.length==0) System.out.println("Please enter a SQL command or 'exit' to exit:");
				if(!in.hasNextLine()) return;
				String line=in.nextLine().trim();
				if(line.equalsIgnoreCase("exit")||line.equalsIgnoreCase("exit;")) return;
				if(line.endsWith(";")){
					line=line.substring(0, line.length()-1);
				}
				boolean hasResultSet=stat.execute(line);
				if(hasResultSet){
					showResultSet(stat);
				}
				else {
					int updateCount=stat.getUpdateCount();
					System.out.println(updateCount+" rows updated");
				}
				System.out.println();
			}
		}
	}
	
	private static void showResultSet(Statement stat) throws SQLException{
		ResultSet rs=stat.getResultSet();
		ResultSetMetaData rsmd=rs.getMetaData();
		int j=rsmd.getColumnCount();
		for(int i=1;i<=j;i++){
			if(i>1) System.out.print(",\t");
			System.out.print(rsmd.getColumnLabel(i));			
		}
		System.out.println();
		while(rs.next()){
			for(int i=1;i<=j;i++){
				if(i>1)System.out.print(",\t");
				System.out.print(rs.getString(i));
			}
			System.out.println();
		}
		rs.close();
	}
}
