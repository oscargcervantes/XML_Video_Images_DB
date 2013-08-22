package edu.lsivc.directory;

import java.io.*;

public class Index_Elements extends CreateIndexElements
{
  
  /**
 * @uml.property  name="pathindex"
 */
private String pathindex;
  /**
 * @uml.property  name="dir"
 */
private String dir;
  /**
 * @uml.property  name="pathelms"
 */
private String pathelms;
/**
 * @uml.property  name="pathdbinit"
 */
private String pathdbinit;
/**
 * @uml.property  name="allpaths"
 */
private String allpaths;
/**
 * @uml.property  name="pathdb"
 */
private String pathdb;
  
  public Index_Elements (String dbdir, String indxfilename, String elmsfilename, String dbinitfilename, String alldirs, String dir)
  {
	super(dir);
	this.dir = dir;
	pathdb = dbdir;
	pathindex = this.dir + indxfilename;
    pathelms = this.dir + elmsfilename;
    pathdbinit = this.dir + dbinitfilename;
    allpaths = this.dir + alldirs;
    this.SaveAllDirs(allpaths, pathdb, pathindex, pathelms, pathdbinit);
   }
  
  public String GetIndex()
  { 
   try
   {
     String Number = null;
     FileInputStream fstream = new FileInputStream(pathindex);
     DataInputStream in = new DataInputStream(fstream);
     BufferedReader br = new BufferedReader(new InputStreamReader(in));
     String strLine;
  
     while ((strLine = br.readLine()) != null)   
     {
       Number = strLine;
      }
     in.close();
     return Number;
    }
    catch (Exception e)
    {
     System.err.println("Error: " + e.getMessage());
     return "Error";
     }
  }
  
  public boolean GetDBInit()
  { 
   try
   {
     String Init = null;
     FileInputStream fstream = new FileInputStream(pathdbinit);
     DataInputStream in = new DataInputStream(fstream);
     BufferedReader br = new BufferedReader(new InputStreamReader(in));
     String strLine;
  
     while ((strLine = br.readLine()) != null)   
     {
       Init = strLine;
      }
     in.close();
     if(Init.contentEquals("true"))
     { 	 
       return true;
      }
     else
     {
       return false; 
      } 	 
    }
    catch (Exception e)
    {
     System.err.println("Error: " + e.getMessage());
     return false;
     }
  }
  
  public boolean SetIndex(String Number)
  {
	try
	{
     String read;
     read = this.GetIndex();
     if(Integer.valueOf(Number) == (Integer.valueOf(read) + 1))
     {	 
	   FileWriter fstream = new FileWriter(pathindex);
       BufferedWriter out = new BufferedWriter(fstream);
       out.write(Number);
       out.close();
       return true;
      }
     else
     {
       return false;	 
      }
     }
    catch (Exception e)
    {
     System.err.println("Error: " + e.getMessage());
     return false;
     } 
   }
 
  public String[] GetElements()
  { 
   try
   {
     int count = 0;
     FileInputStream fstream = new FileInputStream(pathelms);
     DataInputStream in = new DataInputStream(fstream);
     BufferedReader br = new BufferedReader(new InputStreamReader(in));
     String strLine;
     
     while ((strLine = br.readLine()) != null)   
     {
       count++;
      }
     
     System.gc();
     
     String[] Elms = new String[count];
     FileInputStream fs = new FileInputStream(pathelms);
     DataInputStream i = new DataInputStream(fs);
     BufferedReader buffer = new BufferedReader(new InputStreamReader(i));
     count = 0;
     
     while ((strLine = buffer.readLine()) != null)   
     {
       Elms[count] = strLine;
       count++;
      }
     
     in.close();
     i.close();
     return Elms;
    }
    catch (Exception e)
    {
     System.err.println("Error: " + e.getMessage());
     return null;
     }
  }
 
  public boolean InsertElement(String Element)
  {
	try
	{
	 FileWriter fstream = new FileWriter(pathelms,true);
	 BufferedWriter out = new BufferedWriter(fstream);
	 out.newLine();
	 out.write(Element);
	 out.close();
     return true;
	 }
    catch (Exception e)
    {
     System.err.println("Error: " + e.getMessage());
     return false;
     } 
   }
  
  private void SaveAllDirs(String dirs, String dbdir, String index, String elms, String dbinit)
  {
	  File file = new File(dirs);
	  file.delete();
	  try
		{
		 FileWriter fstream = new FileWriter(dirs,true);
		 BufferedWriter out = new BufferedWriter(fstream);
		 out.write(dbdir);
		 out.newLine();
		 out.write(index);
		 out.newLine();
		 out.write(elms);
		 out.newLine();
		 out.write(dbinit);
		 out.close();
		 }
	    catch (Exception e)
	    {
	     System.err.println("Error: " + e.getMessage());
	     }   
   }
}
