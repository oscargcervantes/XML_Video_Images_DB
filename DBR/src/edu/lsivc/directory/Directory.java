package edu.lsivc.directory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;

public class Directory extends CreateDirectory
{
  static int spc_count=-1;
  /**
 * @uml.property  name="dir"
 */
private String dir;
  /**
 * @uml.property  name="ext"
 */
private String ext;
   
  public Directory(String dir, String ext)
  {
	super(dir);
	this.dir = dir;
	this.ext = ext;
    }
  
  public Directory(String dir)
  {
	super(dir);
	this.dir = dir;
	this.ext = ".avi";
    }
  
  public Directory()
  {
	super(".");
	this.dir = ".";
	this.ext = ".avi";
    }
  
  public void Read(File aFile) 
  {
    spc_count++;
    String spcs = "";
    for (int i = 0; i < spc_count; i++)
      spcs += " ";
    if(aFile.isFile())
    {
      boolean flag;
      //System.out.println("[FILE] " + aFile.getAbsoluteFile());
      //System.out.println(aFile.lastModified());
      Filter flt = new Filter(this.ext);
      flag = flt.accept(aFile);
      if (flag == true)
      {
        FileW f = new FileW(this.dir + ".filteredfiles.txt", aFile.getAbsolutePath()/*+"\n"*/);
        f.Write();
       }
     }
    else if (aFile.isDirectory()) 
    {
      //System.out.println("[DIR] " + aFile.getAbsolutePath());
      //FileW g = new FileW(this.dir + ".videodirs.txt", aFile.getAbsolutePath()+"\n");
      //g.Write();
      File[] listOfFiles = aFile.listFiles();
      if(listOfFiles!=null) 
      {
        for (int i = 0; i < listOfFiles.length; i++)
          this.Read(listOfFiles[i]);
       } 
      else 
      {
        System.out.println(" [ACCESS DENIED]");
       }
     }
    spc_count--;
  }
 
  public void CopyFile(String strSourceFile, String strDestFile) throws IOException 
  {
	  File sourceFile = new File(strSourceFile);
	  File destFile = new File(strDestFile);
	  if(!destFile.exists())
	  {
	   destFile.createNewFile();
	   }
	  
	  FileChannel source = null;
	  FileChannel destination = null;
	  try
	  {
	   source = new FileInputStream(sourceFile).getChannel();
	   destination = new FileOutputStream(destFile).getChannel();
	   destination.transferFrom(source, 0, source.size());
	   }
	  finally 
	  {
	   if(source != null)
	   {
	    source.close();
	    }
	   if(destination != null) 
	   {
	    destination.close();
	    }
	   }
   }
  
  public void Delete(String fileName) 
  {
    try 
    {
      // Construct a File object for the file to be deleted.
      File target = new File(this.dir + fileName);

      if (!target.exists()) {
        System.err.println("File " + this.dir + fileName
            + " not present to begin with!");
        return;
      }

      // Quick, now, delete it immediately:
      if (target.delete())
        System.err.println("** Deleted " + this.dir + fileName + " **");
      else
        System.err.println("Failed to delete " + this.dir + fileName);
     } 
     catch (SecurityException e) 
     {
      System.err.println("Unable to delete " + this.dir + fileName + "("
          + e.getMessage() + ")");
      }
  }
  
  public void DeleteDirectory(String Path)
  {
	  try 
	  {
		FileUtils.deleteDirectory(new File(Path));
	   } 
	  catch (IOException e) 
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }  
   }
 } 

