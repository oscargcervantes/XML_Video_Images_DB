package edu.lsivc.directory;

import java.io.FileFilter;
import java.io.File;

public class Filter implements FileFilter
{ 
 /**
 * @uml.property  name="extension"
 */
private String extension;
 
 public Filter(String extension)
 {
  	this.extension = extension;
  }
 
 public boolean accept(File file)
 {	 
   if(file.getName().toLowerCase().endsWith(this.extension))
   {
	 return true; 
	}
   return false; 
  }
}
