package ssa;
import java.sql.*;

public class SqlDB {

	public Connection con = null;
	
	public int executeSqlUpdate(String sql) throws SQLException 
	{
		Statement stmt = con.createStatement();
		try 
		{	return stmt.executeUpdate(sql);	} 
		catch(SQLException ex) { throw ex; }
	}
	
	public ResultSet executeSqlQuery(String sql) throws SQLException 
	{
		Statement stmt = con.createStatement();
		try { return stmt.executeQuery(sql);	} 
		catch(SQLException ex) { throw ex; }
	}
	
	public SqlDB(String url, String usr, String pwd) throws SQLException 
	{		con = DriverManager.getConnection(url, usr, pwd);	}
	
	public void close() throws SQLException 
	{	if(con != null) { con.close(); con = null; }	}
	
}
