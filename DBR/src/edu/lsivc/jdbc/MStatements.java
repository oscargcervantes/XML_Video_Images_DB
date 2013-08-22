package edu.lsivc.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MStatements
{
  /**
 * @uml.property  name="conn"
 */
private Connection conn;
  
  public MStatements(Connection c)
  {
	conn = c;  
   }
  
  public void insert(String table, String fields, String values)
  {
	  
	try
    {
      String insert = "insert into " + table + " (" + fields + ") values (" + values + ");";
	  System.out.println(insert);
      Statement sql = conn.createStatement();
      sql.executeUpdate(insert);
      //conn.commit();
      sql.close();
     }  
    catch (SQLException e) 
    {
      System.out.println("Database connection Failed");
      e.printStackTrace();
     }  
   }
  
  public void delete()
  {
	//Pendiente  
   }
  
  public void update()
  {
	//Pendiente  
   }
 }