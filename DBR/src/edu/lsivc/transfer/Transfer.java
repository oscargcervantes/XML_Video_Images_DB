package edu.lsivc.transfer;

import com.zehon.BatchTransferProgressDefault;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.sftp.SFTP;

public class Transfer
{
  private String serveripaddr, serveruser, password;
  
  public Transfer(String ServerIPAddr, String ServerUser, String PassWord)
  {
	  serveripaddr = ServerIPAddr;
	  serveruser = ServerUser;
	  password = PassWord;
   }
  
  public void DownloadFile(String RemoteFile, String RemoteFilePath, String LocalFilePath)
  {
  	/*JFileChooser ch = new JFileChooser();
      String chtitle = "Select directory to save file(s)";
      String dir = null;
      ch.setCurrentDirectory(new java.io.File("."));
      ch.setDialogTitle(chtitle);
      ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      ch.setAcceptAllFileFilterUsed(false);
      if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
      {  
   	   dir = ch.getSelectedFile().getPath();
   	}
      else 
      {
   	   System.out.println("No Selection ");
   	}
      
      dir = dir + File.separator;*/
  	
  	//String RemoteFile = FilenameUtils.getName(Files.elementAt(indexs[i]-1-Dirs.size()).toString());
  	//String RemoteFilePath = FilenameUtils.getFullPath(Files.elementAt(indexs[i]-1-Dirs.size()).toString());
  	//String LocalFilePath = dir;
  	try
  	{
  			
  	  int status = SFTP.getFile(RemoteFile, RemoteFilePath, serveripaddr, serveruser, password, LocalFilePath);
  	  if(FileTransferStatus.SUCCESS == status)
  	  {
  		System.out.println(RemoteFile + " got downloaded successfully to  folder "+ LocalFilePath);
  	   }
  	  else if(FileTransferStatus.FAILURE == status)
  	  {
  		System.out.println("Fail to download  to  folder "+ LocalFilePath);
  	   } 
  	 } 
  	catch (FileTransferException e)
  	{
  	  e.printStackTrace();
  	 }	
   }
  
  public void DownloadDirectory(String sftpFolder, String toLocalFolder)
  {
		try
		{
			int status = SFTP.getFolder(sftpFolder, toLocalFolder, new BatchTransferProgressDefault(),this.serveripaddr, this.serveruser, this.password);
			if(FileTransferStatus.SUCCESS == status){
				System.out.println(sftpFolder + " got downloaded successfully to  folder "+toLocalFolder);
			}
			else if(FileTransferStatus.FAILURE == status){
				System.out.println("Fail to download  to  folder "+toLocalFolder);
			}
		} 
		catch (FileTransferException e)
		{
			e.printStackTrace();
		}	
   }
  
  public void UploadFile(String destFolder, String filePath)
  {
		try {
			//String filePath = "/home/oscargcervantes/Downloads/qtfm-5.3.tar.gz";
			int status = SFTP.sendFile(filePath, destFolder, this.serveripaddr, this.serveruser, this.password);
			if(FileTransferStatus.SUCCESS == status){
				System.out.println(filePath + " got sftp-ed successfully to  folder "+destFolder);
			}
			else if(FileTransferStatus.FAILURE == status){
				System.out.println("Fail to ssftp  to  folder "+destFolder);
			}
		} catch (FileTransferException e) {
			e.printStackTrace();
		}
   }

  public void UploadDirectory(String sendingFolder, String destFolder)
  {
		//String sendingFolder = "C:\\myfiles";
		//String destFolder = "/test";
		try {
			int status = SFTP.sendFolder(sendingFolder, destFolder, new BatchTransferProgressDefault(), this.serveripaddr, this.serveruser, this.password);
			if(FileTransferStatus.SUCCESS == status){
				System.out.println(sendingFolder + " got sftp-ed successfully to  folder "+destFolder);
			}
			else if(FileTransferStatus.FAILURE == status){
				System.out.println("Fail to ssftp  to  folder "+destFolder);
			}
		} catch (FileTransferException e) {
			e.printStackTrace();
		}	
   }
 }