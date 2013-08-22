package edu.lsivc.directory;

import java.io.IOException;

public class FolderPermissions
{
  /**
 * @uml.property  name="dir"
 */
private String Dir;
  /**
 * @uml.property  name="permissions"
 */
private String Permissions;
  
  public FolderPermissions(String Dir, String Permissions)
  {
	this.Dir = Dir;
	this.Permissions = Permissions;
   }
  public void Apply()
  {
	  try
	    {
	    	String command = "chmod -R " + Permissions + " " + Dir ;
			Process proc1 = Runtime.getRuntime().exec(command);
		 } 
	    catch (IOException e)
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }  
   }
 }