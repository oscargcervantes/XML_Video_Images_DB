package edu.lsivc.directory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;

import edu.lsivc.directory.Directory;

public class FileListenerServer implements FileListener 
{
  /**
 * @uml.property  name="delay"
 */
private int delay = 100;	
  public void fileChanged(FileChangeEvent fe) throws Exception 
  {
    String Path = fe.getFile().getName().getPath().toString();
    String extension = fe.getFile().getName().getExtension();
    
    if(extension.contentEquals("avi"))
    {
      /*try
	  {
	     Thread.sleep(delay);   
		}
	  catch(InterruptedException e)
	  {
			   
	    }*/
      UpdateVideosInfo f = new UpdateVideosInfo(Path);
      f.SearchUpdate();
      System.out.println("changed–"+fe.getFile().getName());
     }
    else if(extension.contentEquals("xml"))
    {
      //indexfiles
    	/*try
		   {
			 Thread.sleep(delay);   
		    }
		   catch(InterruptedException e)
		   {
			   
		    }*/	
      IndexXMLs x = new IndexXMLs(Path);
      x.UpdateIndex();
      System.out.println("changed–"+fe.getFile().getName());
     } 	
   }

  public void fileCreated(FileChangeEvent fe) throws Exception 
  {
	//String BaseName = fe.getFile().getName().getBaseName().toString();
	String Path = fe.getFile().getName().getPath().toString();
    String extension = fe.getFile().getName().getExtension();
    //String ParentPath = fe.getFile().getName().getParent().getPath().toString() + File.separator;
    
    if(extension.contentEquals("avi"))
    {
      /*try
	  {
	     Thread.sleep(delay);   
		}
	  catch(InterruptedException e)
	  {
			   
	    }*/
      UpdateVideosInfo f = new UpdateVideosInfo(Path);
      f.SearchUpdate();
      System.out.println("created–"+fe.getFile().getName());
     }
    else if(extension.contentEquals("xml"))
    {
      //indexfiles
    	/*try
		   {
			 Thread.sleep(delay);   
		    }
		   catch(InterruptedException e)
		   {
			   
		    }*/	
      IndexXMLs x = new IndexXMLs(Path);
      x.Index();
      System.out.println("created–"+fe.getFile().getName());
     } 	
   }

  public void fileDeleted(FileChangeEvent fe) throws Exception 
  {
    System.out.println("deleted–"+fe.getFile().getName());
    String extension = fe.getFile().getName().getExtension();
   }
}