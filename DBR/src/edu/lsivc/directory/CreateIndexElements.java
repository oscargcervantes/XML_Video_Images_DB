package edu.lsivc.directory;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class CreateIndexElements
{
 /**
 * @uml.property  name="genericElements" multiplicity="(0 -1)" dimension="1"
 */
final private String[] GenericElements = {"ID","Height","Birth_Date",
	            "Sex","Weight","Skin_Color","Hair_Color"};
 
 /**
 * @uml.property  name="dir"
 */
private String dir;
 /**
 * @uml.property  name="i"
 */
private int i;
  
  public CreateIndexElements(String dir)
  {
	this.dir = dir;
   }
  
  public void Create()
  {
	  Directory directory = new Directory(this.dir);
	  directory.Delete(".Elements.elms");
	  directory.Delete(".Index.index");	
	  directory.Delete(".DBInitialized.db");
	  
	  for(i=0;i<GenericElements.length;i++)
	  {	  
		FileW personaElements = new FileW(this.dir + ".Elements.elms", GenericElements[i]);
        personaElements.Write();
	   }
      
	  FileW personaIndex = new FileW(this.dir + ".Index.index","0");
	  personaIndex.Write();  
	  FileW dbinit = new FileW(this.dir + ".DBInitialized.db", "true");
	  dbinit.Write();
   }
 }