package edu.lsivc.directory;

import java.io.*;

/**
 * @author      Oscar Cervantes <oscargcervantes@yahoo.com.mx>
 * @version     1.6                                       
 * @since       2011.0622                                 
 */

public class CreateDirectory 
{
  /**
 * Variable dir.
 * @param dir    Used to store the path where Directories are going to be created.
 * @uml.property  name="dir"
 */
  private String dir;
  	
  /**
   * Constructor of Class.
   * @param path Path where Directory or Sub Directory is going to be created.
   */
  public CreateDirectory(String path)
  {
	dir = path;  
   }
  
  /**
   * Creates a Directory.
   * @return true if the directory was created.
   */
  public boolean CreateDir()
  {
	File file = new File(dir);
	file.setWritable(true, false);
	file.setReadable(true, false);
	file.setExecutable(true, false);
	boolean created = file.mkdir();
	return created;  
   }
  
  /**
   * Creates Sub Directory.
   * @return true if the directory was created.
   */
  public boolean CreateSubDir()
  {
	File file = new File(dir);
	file.setWritable(true, false);
	file.setReadable(true, false);
	file.setExecutable(true, false);
	boolean created = file.mkdirs();
	return created;  
   } 
  
  /**
   * Checks if Directory or Sub Directory already exists.
   * @return true if the directory exists.
   */
  public boolean ExistDir()
  {
	boolean exist = new File(dir).exists();
	return exist;  
   }
     
 }
