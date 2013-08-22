import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import edu.lsivc.server.SearchServer;
import edu.lsivc.video.PlayVideo;
import edu.lsivc.directory.FileListenerServer;
import edu.lsivc.audio.*;

public class Test
{	
	private static final int port = 8181;
	public static void main(String[] args) throws Exception 
	{	
		// Throw a nice little title page up on the screen first
	    //SplashScreen splash = new SplashScreen(10000);
	    // Normally, we'd call splash.showSplash() and get on with the program.
	    // But, since this is only a test...
	    //splash.showSplashAndExit();
		/*Interface i = new Interface();
		i.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    i.setSize( 900, 650); // set frame size
		i.setResizable(true);
	    i.setVisible( true ); // display frame*/
		//PlayAudio play = new PlayAudio("/home/oscargcervantes/Downloads/sampl.mp4");
		//play.Play();
		//PlayVideo playvideo = new PlayVideo("/home/oscargcervantes/Downloads/sampl.mp4");
	    //playvideo.PlaySingle();
	    
		try 
		{
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                /*else if ("Nimbus".equals(info.getName())) 
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }*/
            }
        } 
		catch (ClassNotFoundException ex) 
		{
            java.util.logging.Logger.getLogger(ModifyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
		catch (InstantiationException ex) 
		{
            java.util.logging.Logger.getLogger(ModifyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
		catch (IllegalAccessException ex) 
		{
            java.util.logging.Logger.getLogger(ModifyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
		catch (javax.swing.UnsupportedLookAndFeelException ex) 
		{
            java.util.logging.Logger.getLogger(ModifyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.version"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("user.home"));
		XMLDBFrame principal = new XMLDBFrame();
	    principal.setVisible(true);
	    
	    String confdir = null;
	    String dbdir = null;
	
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
	    	
	    	try 
			{
		       FileSystemManager fsManager = VFS.getManager();
		       FileObject listendir = fsManager.resolveFile(dbdir);
		       DefaultFileMonitor fm = new DefaultFileMonitor(new FileListenerServer());
		       fm.setRecursive(true);
		       fm.addFile(listendir);
		       fm.start();
		       
		       //FileSystemManager fsManager2 = VFS.getManager();
		       //FileObject listendir2 = fsManager2.resolveFile(dbdir + "DBFR" + File.separator);
		       //DefaultFileMonitor fm2 = new DefaultFileMonitor(new FileListenerEx());
		       //fm2.setRecursive(true);
		       //fm2.addFile(listendir2);
		       //fm2.start();
		     } 
			catch (FileSystemException e) 
			{
		       // TODO Auto-generated catch block
		       e.printStackTrace();
		     }
	    	
	    	ServerSocket listener = new ServerSocket(port);
	        JOptionPane.showMessageDialog(null,"Server Started " + String.valueOf(port));
	        try
	        {
	          while(true)
	          {
	            //Socket socket = listener.accept();
	            try
	            {
	              new SearchServer.OneConnection(listener.accept()).start();
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
	          System.out.println("Server Closed");
	         }
	     }	
	 }
 }
