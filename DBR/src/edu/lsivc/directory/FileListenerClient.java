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

import edu.lsivc.String.StringMatch;
import edu.lsivc.directory.Directory;
import edu.lsivc.transfer.Transfer;

public class FileListenerClient implements FileListener 
{
  private String ServerUser;
  private String ServerIP;
  private String ServerPassword;
  private String DBDir;
  private String OS;
  
  public FileListenerClient(String ServerUser, String ServerIP, String ServerPassword, String DBDir)
  {
	  this.ServerIP = ServerIP;
	  this.ServerUser = ServerUser;
	  this.ServerPassword = ServerPassword;
	  this.DBDir = DBDir;
	  this.OS = System.getProperty("os.name");
   }
  
  public void fileChanged(FileChangeEvent fe) throws Exception 
  {
	Boolean Answer;
	String Path = fe.getFile().getName().getPath().toString();
    String extension = fe.getFile().getName().getExtension();
    if(extension.contentEquals("avi"))
    {
    	String Name = FilenameUtils.getName(Path);
        StringMatch m = new StringMatch(Name);
        Answer = m.MatchDBGR();
        if(Answer)
        {
        	if(OS.contentEquals("Linux"))
        	{
        		
        	 }
        	else if(OS.contentEquals("Windows"))
        	{
        		
        	 }		
         }
        
        Answer = m.MatchDBFR();
        if(Answer)
        {
        	if(OS.contentEquals("Linux"))
        	{
        		
        	 }
        	else if(OS.contentEquals("Windows"))
        	{
        		
        	 }		
         }
     } 	
   }

  public void fileCreated(FileChangeEvent fe) throws Exception 
  {
	Boolean Answer;
	String Path = fe.getFile().getName().getPath().toString();
    String extension = fe.getFile().getName().getExtension();
    //String ParentPath = fe.getFile().getName().getParent().getPath().toString() + File.separator; 
    if(extension.contentEquals("avi"))
    {
    	String Name = FilenameUtils.getName(Path);
        StringMatch m = new StringMatch(Name);
        Transfer tr = new Transfer(ServerIP,ServerUser,ServerPassword);
        Answer = m.MatchDBGR();
        if(Answer)
        {
        	
        		//String command = "scp " + Path + " " + ServerUser + "@" + ServerIP + ":" + DBDir + "Videos" + File.separator + "DBGR" + File.separator + Name;
			    //Process proc1 = Runtime.getRuntime().exec(command);
			    tr.UploadFile(DBDir + "Videos" + File.separator + "DBGR" + File.separator + Name, Path);
        	 		
         }
        Answer = m.MatchDBFR();
        if(Answer)
        {
        	
        		//String command = "scp " + Path + " " + ServerUser + "@" + ServerIP + ":" + DBDir + "Videos" + File.separator + "DBFR" + File.separator + Name;
			    //Process proc1 = Runtime.getRuntime().exec(command);	
        	    tr.UploadFile(DBDir + "Videos" + File.separator + "DBFR" + File.separator + Name, Path);	
         }	
     }	
   }

  public void fileDeleted(FileChangeEvent fe) throws Exception 
  {
    System.out.println("deleted–"+fe.getFile().getName());
    //String extension = fe.getFile().getName().getExtension();
   }
}
