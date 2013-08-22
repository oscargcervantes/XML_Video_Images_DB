package edu.lsivc.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;

import edu.lsivc.directory.CreateDirectory;
import edu.lsivc.directory.FileWalker;
import edu.lsivc.search.Searcher;
import edu.lsivc.xml.XMLFile;

public class SearchServer 
{  
  //private static final int port = 8181;
  private static Vector<String> names = new Vector<String>();
  private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
  private static int index = 0;

  /*public static void main(String[] args) throws IOException
  {
    ServerSocket listener = new ServerSocket(port);
    JOptionPane.showMessageDialog(null,"Server Started " + String.valueOf(port));
    try
    {
      while(true)
      {
        //Socket socket = listener.accept();
        try
        {
          new ServeOneConnection(listener.accept()).start();
         }
        catch(IOException e)
        {
          listener.close();
         }
       }
     }
    finally
    {
      listener.close();
     }
  }*/
 
  public static class OneConnection extends Thread 
  {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private StringTokenizer tokens;
    private String User;
    private String Opponent;
    private String Id;
    private Iterator iterator;
    private String message;
    private String dbdir;
    private Vector<Document> docs;
    private String Files;
    //private String Name;
	private String Dirs;
	private String Elements = " ";
	private String Attributes = " ";
    
    public OneConnection(Socket s) throws IOException
    {
      this.socket = s;
      in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
 	  out = new PrintWriter(this.socket.getOutputStream(),true);
 	  this.LoadDBDir();
     }
    
    private void LoadDBDir()
    {
    	try
        {		
    	 FileInputStream fstream = new FileInputStream(System.getProperty("user.home") + File.separator + ".dbr");
         DataInputStream in = new DataInputStream(fstream);
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         String strLine;
        
    		 while ((strLine = br.readLine()) != null)   
    	     {
    		   dbdir = strLine;
    		  }
         in.close();
         }
        catch(Exception e)
        {
      	System.out.println(e.getMessage());  
         }
    }
    
    public void run()
    {
  	  try
      {
        while (true)
        {  
          if (!writers.contains(out))
          {
        	writers.add(out);
           }
          message = in.readLine();
          if(message == null)
          {
        	//System.out.println("Null message");
        	message = "DBR Null";
           }	  
          else if (message.equals("Client>>>End")) 
          {	
            out.println("Server>>>End");
            index--;
            return;
            //break; 
           }
          tokens = new StringTokenizer(message);
    	  //System.out.println(message);
    	  if(message.startsWith("Close"))
 		  {
 		    User = message.substring(6); 
 		    names.remove(User);
 		    writers.remove(out);	 
 		   }	 
    	  else if(message.startsWith("Create"))
    	  {
    		 String mess = tokens.nextToken();
     		 String user = tokens.nextToken();
     		 String newdir = tokens.nextToken();
     		 CreateDirectory ndir = new CreateDirectory(newdir);
     		 if(!ndir.ExistDir())
     		 {
     			ndir.CreateSubDir();
     			message = "Created" + " " + User + " " + "Directory_created";
    		    for (PrintWriter writer : writers)
        	    {
        	       writer.println(message);
                 } 
     		  }
     		 else
     		 {
     			message = "Created" + " " + User + " " + "Directory_already_exists";
    		    for (PrintWriter writer : writers)
        	    {
        	       writer.println(message);
                 }  
     		  }	 
     		 //ndir.
    	   }	  
    	  else if(message.startsWith("Directory"))
    	  {
    		 String mess = tokens.nextToken();
    		 String user = tokens.nextToken();
    		 String path = tokens.nextToken();
    		 System.out.println(path);
    		 FileWalker fw = new FileWalker();
  		     Dirs = fw.WalkDir(path);
  		     Files = fw.WalkFile(path);
  		     
  		     message = "Directory" + " " + User + " " + Dirs;
  		     for (PrintWriter writer : writers)
   	         {
   	           writer.println(message);
              } 
  		     
  		     message = "Files" + " " + User + " " + Files;
  		     for (PrintWriter writer : writers)
   	         {
   	           writer.println(message);
              } 
    	   }	  
    	  else if(message.startsWith("Connection")) //Connection Message == Connection username;
    	  {	  	  
    		User = message.substring(11);

            if (!names.contains(User))
            {
              names.insertElementAt(User,index);
              index++;
              //break;
              }
            else
            {
             System.out.println("Name already in use");	
              }
    	   
    	    if(names.size() >= 1)
    	    { 
    	      for (PrintWriter writer : writers)
        	  {
        	    writer.println("Ready" + " " + dbdir);
               }
    	     }	
    	    }
    	   else if(message.startsWith("Search")) //Shoot Request
    	   {
    		//JOptionPane.showMessageDialog(null,"Mensaje 4 Server received");   
    	    //String Search = tokens.next
    		  String mess = tokens.nextToken();
    		  String us = tokens.nextToken();
    		  String Search = tokens.nextToken();
    		  while(tokens.hasMoreTokens())
    		  {
    			Search += " " + tokens.nextToken();  
    		   } 	  
    		  System.out.println("Client Search: " + Search);
    		  String Results = "";
    		  
    		  Searcher s = new Searcher(System.getProperty("user.home") + File.separator + ".Index" + File.separator + "dbrindex","Keyword", Search);
        	  try
        	  {
        		docs = s.search();
    		    for(int i=0;i<docs.size();i++)
    		    {	  
    		       Results += docs.elementAt(i).get("URL") + " ";
    		     }
    		    
    		    message = "Result" + " " + User + " " + Results;
    		    for (PrintWriter writer : writers)
        	    {
        	       writer.println(message);
                 } 
    		   } 
        	  catch (ParseException e)
        	  {
    		     // TODO Auto-generated catch block
    			 e.printStackTrace();
    		   }   
    		 }
    	   else if(message.startsWith("GetInfo")) //Shoot Request
    	   {
    		   String mess = tokens.nextToken();
     		   String us = tokens.nextToken();
     		   String video = tokens.nextToken();
     		   String[][] Elms = null;
     		   String[][] Attr = null;
     		   String[] Els = null;
     		   String[] Att = null;
     		   
     		   int t,s;
     		   Elements = " ";
     		   Attributes = " ";
     		   
     		   video = FilenameUtils.removeExtension(video);
     		   video = video + ".xml";
     		   XMLFile xml = new XMLFile(video);
     		   
     		   Elms = xml.GetElements();
     		   Els = new String[Elms[0].length];
         	   for(s=0;s<Elms.length;s++)
         	   {
         	     for(t=0;t<Elms[s].length;t++)
         	     {
         		   Els[t] = Elms[s][t];   
         	      }
         	    }
     		   
         	   Attr = xml.Read(Els);
     		   Att = new String[Attr[0].length];
        	   for(s=0;s<Attr.length;s++)
        	   {
        	     for(t=0;t<Attr[s].length;t++)
        	     {
        		   Att[t] = Attr[s][t];   
        	      }
        	    }
        	   
        	   for(s=0;s<Att.length;s++)
        	   {
        		 Attributes += Att[s] + " ";
        		 Elements += Els[s] + " ";
        	    }
        	   
     		   message = "Elements" + " " + User + " " + Elements;
   		       for (PrintWriter writer : writers)
    	       {
    	           writer.println(message);
                 } 
   		     
   		       message = "Attributes" + " " + User + " " + Attributes;
   		       for (PrintWriter writer : writers)
    	       {
    	           writer.println(message);
                 } 
    	    }
          }
        //System.out.println("closing...");
       } 
      catch (IOException e)
      {
       } 
      finally
      {
        try 
        {
          socket.close();
         }
        catch(IOException e)
        {  
         }
       }
    }
  } 
}
