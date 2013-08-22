package edu.lsivc.directory;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.lsivc.index.Indexer;
import edu.lsivc.search.Searcher;
import edu.lsivc.xml.XMLFile;


public class IndexXMLs
{
  /**
 * @uml.property  name="xMLFileDir"
 */
private String XMLFileDir;
  /**
 * @uml.property  name="confdir"
 */
private String confdir = null;
  /**
 * @uml.property  name="dbdir"
 */
private String dbdir = null;
  /**
 * @uml.property  name="elements" multiplicity="(0 -1)" dimension="2"
 */
private String[][] Elements;
  /**
 * @uml.property  name="attributes" multiplicity="(0 -1)" dimension="2"
 */
private String[][] Attributes;
  
  public IndexXMLs(String F)
  {
	XMLFileDir = F;
	confdir = System.getProperty("user.home") + File.separator + ".dbr";
    File file = new File(confdir);
    if(file.exists())
    {
    	try
        {
         FileInputStream fstream = new FileInputStream(confdir);
         DataInputStream in = new DataInputStream(fstream);
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         String strLine;
    
         while ((strLine = br.readLine()) != null)   
         {
           dbdir = strLine;
          }
         }
    	catch(Exception e)
    	{
          System.out.println("Error dbdir not found");	
    	 }
     }
  }
  
  public void Index()
  {
	 XMLFile xml = new XMLFile(XMLFileDir);
	 Elements = xml.GetElements();
	 String[] Els = new String[Elements[0].length];
	 for(int i=0;i<Elements.length;i++)
	 {	 
	  for(int j=0;j<Elements[0].length;j++)
      {
  	    Els[j] = Elements[i][j];  
       }
	 }
	 Attributes = xml.Read(Els);
	 try 
	 {
	   //Indexer index = new Indexer(dbdir + ".Index" + File.separator + "dbrindex");
		 Indexer index = new Indexer(System.getProperty("user.home") + File.separator + ".Index" + File.separator + "dbrindex");
	   try 
	   {
		index.indexFile(Elements, Attributes);
		//index.close();
		//Searcher s = new Searcher(dbdir + ".Index" + File.separator + "dbrindex","ID","1");
		//s.search();
	    } 
	   catch (Exception e) 
	   {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	  } 
	 catch (IOException e)
	 {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
  }
  
  public void UpdateIndex()
  {
	 XMLFile xml = new XMLFile(XMLFileDir);
	 Elements = xml.GetElements();
	 String[] Els = new String[Elements[0].length];
	 for(int i=0;i<Elements.length;i++)
	 {	 
	  for(int j=0;j<Elements[0].length;j++)
      {
  	    Els[j] = Elements[i][j];  
       }
	 }
	 Attributes = xml.Read(Els);
	 try 
	 {
	   Indexer index = new Indexer(System.getProperty("user.home") + File.separator + ".Index" + File.separator + "dbrindex");
	   try 
	   {
		int findex = xml.GetElementIndex("File_Name", Elements);
		//System.out.println(Attributes[0][findex]);
		index.UpdateIndex(Elements, Attributes, Attributes[0][findex]);
		//index.close();
		//Searcher s = new Searcher(dbdir + ".Index" + File.separator + "dbrindex","ID","1");
		//s.search();
	    } 
	   catch (Exception e) 
	   {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	  } 
	 catch (IOException e)
	 {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
  }
  
  public void DeleteIndex()
  {
	 XMLFile xml = new XMLFile(XMLFileDir);
	 Elements = xml.GetElements();
	 String[] Els = new String[Elements[0].length];
	 for(int i=0;i<Elements.length;i++)
	 {	 
	  for(int j=0;j<Elements[0].length;j++)
      {
  	    Els[j] = Elements[i][j];  
       }
	 }
	 Attributes = xml.Read(Els);
	 try 
	 {
	   Indexer index = new Indexer(System.getProperty("user.home") + File.separator + ".Index" + File.separator + "dbrindex");
	   try 
	   {
		int findex = xml.GetElementIndex("File_Name", Elements);
		//System.out.println(Attributes[0][findex]);
		index.DeleteIndex(Elements, Attributes, Attributes[0][findex]);
		//index.close();
		//Searcher s = new Searcher(dbdir + ".Index" + File.separator + "dbrindex","ID","1");
		//s.search();
	    } 
	   catch (Exception e) 
	   {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	  } 
	 catch (IOException e)
	 {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
  }
}