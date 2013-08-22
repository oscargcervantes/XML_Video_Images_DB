package edu.lsivc.jdbc;

//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
//import java.sql.Statement;
 
public class DataBaseConnection 
{
 /**
 * @uml.property  name="classname"
 */
private String classname;
 /**
 * @uml.property  name="user"
 */
private String user;
  /**
 * @uml.property  name="password"
 */
private String password;
  /**
 * @uml.property  name="port"
 */
private String port;
  /**
 * @uml.property  name="dbname"
 */
private String dbname;
  
  public DataBaseConnection(String driver, String u, String p, String pt, String db) //Name of Database driver org.postgresql.Driver
  {
	 classname = driver;
	 user = u;
	 password = p;
	 port = pt;
	 dbname = db; 
   }
  
  public DataBaseConnection()
  {
	classname = "org.postgresql.Driver";
	user = "postgres";
	password = "postgres";
	port = "5432";
	dbname = "dirs";  
   }
  
  public void Initialize()
  {
	try 
    {
     Class.forName(classname);
     } 
    catch (ClassNotFoundException e) 
    {
      System.out.println("Please set your classpath to Where your PostgreSQL is located");
      e.printStackTrace();
      return;
      }
 
    System.out.println("Driver is loaded successfully");   
   }
  
  public Connection Connect()
  {
	Connection conn = null;
	
	  try 
      {
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:" + port + "/" + dbname ,user, password);
        if (conn != null)
        {
          System.out.println("Database Connected");
         }
        else
        {
          System.out.println("Failed to make connection");
          }
       } 
     
      catch (SQLException e) 
      {
        System.out.println("Database connection Failed");
        e.printStackTrace();
        return null;
        }
     return conn;             
   }
}
