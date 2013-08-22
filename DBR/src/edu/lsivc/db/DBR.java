package edu.lsivc.db;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import edu.lsivc.directory.Directory;
import edu.lsivc.directory.FileW;
import edu.lsivc.directory.Index_Elements;

public class DBR
{
	public DBR()
	{	
      		
	 }
	
	public String GetDBDir() //Gets DB main path
    {
    	try
        {		
    	 FileInputStream fstream = new FileInputStream(System.getProperty("user.home") + File.separator + ".dbr");
         DataInputStream in = new DataInputStream(fstream);
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         String strLine;
         String dbdir = null;
    	 
         while ((strLine = br.readLine()) != null)   
    	 {
    	    dbdir = strLine;
    	  }
         in.close();
         return dbdir;
         }
        catch(Exception e)
        {
      	 System.out.println(e.getMessage());
      	 return null;
         }	
    }
	
	public String GetSettingsDir(String dbdir) //Use after GetDBDir method, gets DB Settings path
	{
	  String settingsdir = null;	
	  settingsdir = dbdir + ".Settings" + File.separator;
      //System.out.println(settingsdir);
	  return settingsdir;
	}
	
	public String LoadIndex(String settingsdir) //Get the number of records in DB
    {
    	try
        {
         FileInputStream fstream = new FileInputStream(settingsdir + ".Index.index");
         DataInputStream in = new DataInputStream(fstream);
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         String strLine;
         String PersonaIndex = null;
    
         while ((strLine = br.readLine()) != null)   
         {
           PersonaIndex = strLine;
          }
         fstream.close();
         br.close();
         in.close();
         return PersonaIndex;
         }
    	catch(Exception e)
    	{
          System.out.println("Error Settings dir not found");
          return null;
    	 }	
    }
	
	public String[] GetXMLDirs(String xmlfiledir) //Gets the list of XML files for a specific Person, includes DBGR and DBFR
    {  // */DBR/DBGR/P1/*
	   // */DBR/DBFR/P1/*	
     try
     {
       int count = 0;
       FileInputStream fstream = new FileInputStream(xmlfiledir + ".filteredfiles.txt");
       DataInputStream in = new DataInputStream(fstream);
       BufferedReader br = new BufferedReader(new InputStreamReader(in));
       String strLine;
       
       while ((strLine = br.readLine()) != null)   
       {
         count++;
        }
       
       System.gc();
       
       String[] Elms = new String[count];
       FileInputStream fs = new FileInputStream(xmlfiledir + ".filteredfiles.txt");
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
	
	public void Initialize(String dbdir) //Initializes DB directories
    {
  	   //int i;
  	   
  	   final String settingsdir = dbdir + ".Settings" + File.separator;
  	   final String videodir = dbdir + "Videos" + File.separator;
  	   final String dbgrdir = dbdir + "DBGR" + File.separator;
  	   final String dbfrdir = dbdir + "DBFR" + File.separator;
  	   final String dbgrvideodir = videodir + "DBGR" + File.separator;
  	   final String dbfrvideodir = videodir + "DBFR" + File.separator;
  	   final String indexdir = System.getProperty("user.home") + File.separator + ".Index" + File.separator;
  	   final String imagesdir = dbdir + ".Images" + File.separator;
  	     	   
  	   Directory settings = new Directory(settingsdir);
  	   Directory video = new Directory(videodir);
  	   Directory dbgr = new Directory(dbgrdir);
  	   Directory dbfr = new Directory(dbfrdir);
  	   Directory videodbgr = new Directory(dbgrvideodir);
	   Directory videodbfr = new Directory(dbfrvideodir);
	   Directory index = new Directory(indexdir);
	   Directory image = new Directory(imagesdir);
	   
  	   if(!settings.ExistDir())
  	   {settings.CreateSubDir();}
  	   if(!video.ExistDir())
  	   {video.CreateSubDir();}
  	   if(!dbgr.ExistDir())
  	   {dbgr.CreateSubDir();}
  	   if(!dbfr.ExistDir())
  	   {dbfr.CreateSubDir();}
  	   if(!videodbfr.ExistDir())
	   {videodbfr.CreateSubDir();}
  	   if(!videodbgr.ExistDir())
	   {videodbgr.CreateSubDir();}
  	   if(!index.ExistDir())
	   {index.CreateSubDir();}
  	   if(!image.ExistDir())
	   {image.CreateSubDir();}
  	   
  		/*******************************************************************************************************/
  		//Creates db
  		Index_Elements elmindx = new Index_Elements(dbdir,".Index.index", ".Elements.elms", ".DBInitialized.db", ".AllDirs", settingsdir);
  		boolean flag = elmindx.GetDBInit();
  		
  		if(!flag)
  		{	
  		 elmindx.Create();
  		 }
  		/*******************************************************************************************************/	
  	 }
	
	public String GetPersonaSession(int ID)
	{
		String dbdir = GetDBDir();
		String Session = dbdir + "P" + String.valueOf(ID) + File.separator + ".sesion"; 
		try
        {		
    	 FileInputStream fstream = new FileInputStream(Session);
         DataInputStream in = new DataInputStream(fstream);
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         String strLine;
         String actualsesion = null;
    	 
         while ((strLine = br.readLine()) != null)   
    	 {
    	    actualsesion = strLine;
    	  }
         in.close();
         return actualsesion;
         }
        catch(Exception e)
        {
      	 System.out.println(e.getMessage());
      	 return null;
         }	
	}
	
	public void UpdateSession(int ID)
	{
		String dbdir = GetDBDir();
		String SessionDir = dbdir + "P" + String.valueOf(ID) + File.separator + ".sesion"; 
		String sesion = null;
		try
        {		
    	 FileInputStream fstream = new FileInputStream(SessionDir);
         DataInputStream in = new DataInputStream(fstream);
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         String strLine;
         //String dbdir = null;
    	 
         while ((strLine = br.readLine()) != null)   
    	 {
    	    sesion = strLine;
    	  }
         in.close();
         //return dbdir;
         }
        catch(Exception e)
        {
      	 System.out.println(e.getMessage());
      	 //return null;
         }	
		
		int charValue = sesion.charAt(0);
		String next = String.valueOf( (char) (charValue + 1));
		System.out.println(next);
		
		FileW filew = new FileW(SessionDir,next);
	   	filew.Write();

	 }
	
	public void StartSession(int ID)
	{
		String sesion = "A";
		String dbdir = GetDBDir();
		String SessionDir = dbdir + "P" + String.valueOf(ID) + File.separator + ".sesion"; 
		FileW filew = new FileW(SessionDir,sesion);
	   	filew.Write();
	 }
}