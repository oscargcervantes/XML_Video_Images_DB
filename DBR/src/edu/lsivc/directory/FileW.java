package edu.lsivc.directory;

import java.io.*;

public class FileW 
{
   /**
 * @uml.property  name="directory"
 */
   private String archivo;    
   /**
 * @uml.property  name="message"
 */
   private String message;
   
   public FileW(String path, String m)
   {
        archivo = path;
        message = m;
     }
    
    public void Write()
    {
	   try
	   {
	   	  //Create file 
		  FileWriter fstream = new FileWriter(archivo, true);
          BufferedWriter out = new BufferedWriter(fstream);
		  out.write(message);
		  out.newLine();
		  //Close the output stream
	      out.close();
	    }
	   catch (Exception e)
	   {//Catch exception if any
		 System.err.println("Error: " + e.getMessage());
		}		  
	 }

}	    
