import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.apache.lucene.queryParser.ParseException;

import net.didion.jwnl.JWNLException;
import edu.lsivc.client.SearchClient;
import edu.lsivc.directory.FileChooser;
import edu.lsivc.directory.FileListenerClient;
import edu.lsivc.search.*;

public class SearchTest
{
  public static void main(String[] args) throws JWNLException
  {
	  //BooleanQuery query = new BooleanQuery();
	  //query.add(new BooleanClause(new TermQuery(new Term("field", "big"))), Occur.MUST);
	  //query.add(new BooleanClause(new TermQuery(new Term("field", "abcdefg"))), Occur.MUST);
	  //query.add(new BooleanClause(new TermQuery(new Term("field", "test1234"))), Occur.MUST)
	      
		  try 
			{
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
	            {
	                if ("Nimbus".equals(info.getName())) 
	                {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
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
		
		  String Host = JOptionPane.showInputDialog("Server IP address");
		  String UserName = JOptionPane.showInputDialog("Server username");
		  JLabel label = new JLabel("Please enter Server password:");
		  JPasswordField jpf = new JPasswordField();
		  JOptionPane.showConfirmDialog(null,
		    new Object[]{label, jpf}, "Password:",
		    JOptionPane.OK_CANCEL_OPTION);
		  char[] password = jpf.getPassword();
		  String Password = "";
		  for(int i=0;i<password.length;i++)
		  {
			  Password += password[i];
		   }	  
		  if((Host!=null) && (UserName!=null) && (Password!=null) && (Host!="") && (UserName!="") && (Password!="") && (Host!=" ") && (UserName!=" ") && (Password!=" "))
		  {	  
		   SearchFrame frame = new SearchFrame(Host,8181,UserName,Password);
	       frame.setVisible(true);
	       Thread thr1 = new Thread(frame);
	       thr1.start(); //Player thread
		   }
		  //frame.setVisible(true);
  }
}