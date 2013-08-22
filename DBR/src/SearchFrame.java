import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.Icon;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerType;

import edu.lsivc.String.StringMatch;
import edu.lsivc.analysis.*;
import edu.lsivc.transfer.*;
import edu.lsivc.transfer.ScpFrom.MyUserInfo;
import edu.lsivc.client.SearchClient;
import edu.lsivc.directory.FileChooser;
import edu.lsivc.directory.FileListenerClient;
import edu.lsivc.directory.FileWalker;
import edu.lsivc.icon.Icons;

import com.jcraft.jsch.UserInfo;
import com.zehon.BatchTransferProgressDefault;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.sftp.SFTP;

/*
 * SearchFrame.java
 *
 * Created on Sep 30, 2011, 9:23:14 AM
 */

/**
 *
 * @author oscargcervantes
 */
public class SearchFrame extends javax.swing.JFrame implements Runnable, ActionListener
{
    /**
	 * @uml.property  name="searchPhrase"
	 */
    private String SearchPhrase;
    /**
	 * @uml.property  name="tokens" multiplicity="(0 -1)" dimension="1"
	 */
    private String[] tokens;
    /**
	 * @uml.property  name="wdh"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private WordNetHelper wdh;
    /**
	 * @uml.property  name="wrd"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private WordNet wrd;
    /**
	 * @uml.property  name="dbdir"
	 */
    private String  dbdir;
    /**
	 * @uml.property  name="port"
	 */
    private int port;  
    /**
	 * @uml.property  name="host"
	 */
    private String host;
    /**
	 * @uml.property  name="client"
	 */
    private Socket client;
    /**
	 * @uml.property  name="in"
	 */
    private BufferedReader in;
    /**
	 * @uml.property  name="out"
	 */
    private PrintWriter out;
    /**
	 * @uml.property  name="messages"
	 */
    private String messages = "";
    /**
	 * @uml.property  name="strtokens"
	 */
    private StringTokenizer Strtokens;
    /**
	 * @uml.property  name="ready"
	 */
    private String Ready = "";
    /**
	 * @uml.property  name="user"
	 */
    private String User;
    /**
	 * @uml.property  name="indexs" multiplicity="(0 -1)" dimension="1"
	 */
    private int[] indexs;
    /**
	 * @uml.property  name="resultSearchElements"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private Vector<String> ResultSearchElements = new Vector<String>();
    /**
	 * @uml.property  name="dirs"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private Vector<String> Dirs = new Vector<String>();
    /**
	 * @uml.property  name="files"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private Vector<String> Files = new Vector<String>();
    private Vector<String> Attributes = new Vector<String>();
    private Vector<String> Elements = new Vector<String>();
    /**
	 * @uml.property  name="serveruser"
	 */
    private String serveruser;
    /**
	 * @uml.property  name="serveripaddr"
	 */
    private String serveripaddr;
    /**
	 * @uml.property  name="fs" multiplicity="(0 -1)" dimension="1"
	 */
    private File[] fs;
    /**
	 * @uml.property  name="filePath"
	 */
    private String FilePath;
    private String OS;
    private String password;
    private Map<Object, Icon> icon;
    
    /** Creates new form SearchFrame */
    public SearchFrame(String hostname, int portnumber, String User, String Password) 
    {
    	this.host = hostname;
    	this.port = portnumber;
    	this.password = Password;
    	this.User = User;
    	this.serveruser = User;
        try 
        {
			this.serveripaddr = InetAddress.getByName(host).getHostAddress();
			//IPAddr = serveripaddr.split("/");
			//System.out.println(IPAddr[1]);
		} 
        catch (UnknownHostException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	initComponents();
    	this.OS = System.getProperty("os.name");
		System.out.println(this.OS);
    	if(OS.contentEquals("Linux"))
    	{	
          wdh = new WordNetHelper();
          wdh.initialize("file_properties.xml");
          wrd = new WordNet();
    	 }
    	else if(OS.contains("Windows"))
    	{
    	  wdh = new WordNetHelper();
          wdh.initialize("file_properties_windows.xml");
          wrd = new WordNet();	
    	 }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SearchLabel = new javax.swing.JLabel();
        SearchTextBox = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listModel = new DefaultListModel();
        SearchList = new javax.swing.JList(listModel);
        DownloadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Search");
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing....");
                out.println("Close " + User);
                out.println("Client>>>End");
          	    System.exit(0);
              }
            });
        
        icon = new HashMap<Object, Icon>();
		//Agregar al listmodel, en lugar de cadenas agregar como icons.......
        /*icon.put("details",
			MetalIconFactory.getFileChooserDetailViewIcon());
		icon.put("folder",
			MetalIconFactory.getTreeFolderIcon());
		icon.put("computer",
			MetalIconFactory.getTreeComputerIcon());*/
        
        popupMenu = new JPopupMenu();
        popupMenu.add(jmi1= new JMenuItem("Download file(s)"));
        popupMenu.add(new JPopupMenu.Separator());
        popupMenu.add(jmi2 = new JMenuItem("Directory content"));
        popupMenu.add(new JPopupMenu.Separator());
        popupMenu.add(jmi3 = new JMenuItem("Download directory"));
        popupMenu.add(new JPopupMenu.Separator());
        popupMenu.add(jmi4 = new JMenuItem("Upload file(s)"));
        popupMenu.add(new JPopupMenu.Separator());
        popupMenu.add(jmi5 = new JMenuItem("Upload directory"));
        popupMenu.add(new JPopupMenu.Separator());
        popupMenu.add(jmi6 = new JMenuItem("Create directory"));
        popupMenu.add(new JPopupMenu.Separator());
        popupMenu.add(jmi7 = new JMenuItem("Get information"));
        
        SearchList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) { 
              // if right mouse button clicked (or me.isPopupTrigger())
            	if (me.getClickCount() >= 2 && !me.isConsumed())
            	{

            	    me.consume();
            	    //handle double click. 
                    //System.out.println("Double");
                    for(int i=0;i<indexs.length;i++)
                	{ 
                		String file = listModel.elementAt(indexs[i]).toString();
                	    if(file.startsWith("[DIR]"))
                	    {
                	       //Dirs
                	       //String FilePath = " ";
                 		   if(indexs.length == 1)
                 		   {	   	   
                 		     FilePath = Dirs.elementAt(indexs[i]-1).toString() + File.separator;
                 		     String message = "Directory" + " " + User + " " + FilePath; 
                 		     out.println(message);
                 		    }
                	     }
                	    else if(file.startsWith("[FILE]"))
                	    {
                	    	//JOptionPane.showMessageDialog(null, "Select directory to save file(s)");
                	        //FileChooser chooser = new FileChooser();
                	        //String dir = chooser.Run();
                	    	JFileChooser ch = new JFileChooser();
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
                	        
                	        dir = dir + File.separator;
                	        String RemoteFile = FilenameUtils.getName(Files.elementAt(indexs[i]-1-Dirs.size()).toString());
                	    	String RemoteFilePath = FilenameUtils.getFullPath(Files.elementAt(indexs[i]-1-Dirs.size()).toString());
                	    	String LocalFilePath = dir;
                	    	DownloadFile(RemoteFile, RemoteFilePath, LocalFilePath);
                	      }
                	     else if(file.startsWith(".."))
                	     {
                	    	 listModel.removeAllElements();
                	    	 SearchList.setEnabled(false);
                	    	 DownloadButton.setEnabled(false);
                	    	 for(int j=0;j<ResultSearchElements.size();j++)
                	    	 {
                	    	   listModel.addElement(FilenameUtils.getBaseName(ResultSearchElements.elementAt(j)));
                	    	  }
                	    	 SearchList.setEnabled(true);
                	      }
                	     else
                	     {
                	    	/*JOptionPane.showMessageDialog(null, "Select directory to save file(s)");
                 	        FileChooser chooser = new FileChooser();
                 	        String dir = chooser.Run();
                 	        //System.out.println(dir);
                 	    	
                 	    	String RemoteFile = ResultSearchElements.elementAt(indexs[i]).toString();
                 	    	String LocalFile = dir + FilenameUtils.getName(ResultSearchElements.elementAt(indexs[i]).toString());
                 	    	System.out.println(RemoteFile);
                 	    	System.out.println(LocalFile);
                 	    	try
                 	    	{
                 	    	  if(OS.contentEquals("Linux"))
                 	    	  {	  
                 	    		String command = "scp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
                 			    Process proc1 = Runtime.getRuntime().exec(command);
                 	    	   }
                 	    	  else if(OS.contains("Windows"))
                 	    	  {
                 	    		String command = "C:\\pscp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
                  			    Process proc1 = Runtime.getRuntime().exec(command);  
                 	    	   } 	  
             				 } 
                 	    	catch (IOException e)
                 	    	{
             				   // TODO Auto-generated catch block
             				   e.printStackTrace();
             			     }*/
                	    	JFileChooser ch = new JFileChooser();
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
                 	        
                 	        dir = dir + File.separator;
                 	        //String RemoteFile = ResultSearchElements.elementAt(indexs[i]).toString();
                	    	//String LocalFile = dir + FilenameUtils.getName(ResultSearchElements.elementAt(indexs[i]).toString());
                 	        String RemoteFile = FilenameUtils.getName(ResultSearchElements.elementAt(indexs[i]).toString());
                 	    	String RemoteFilePath = FilenameUtils.getFullPath(ResultSearchElements.elementAt(indexs[i]).toString());
                 	    	String LocalFilePath = dir;
                 	    	DownloadFile(RemoteFile, RemoteFilePath, LocalFilePath);
                	      }	
                	 }
                    //Determine if option is file or dir to perform an action (Download or open dir) 
            	 }
            	else
            	{
            	  if (SwingUtilities.isRightMouseButton(me)
                    && !SearchList.isSelectionEmpty()
                    && SearchList.locationToIndex(me.getPoint())
                       == SearchList.getSelectedIndex()) {
                        popupMenu.show(SearchList, me.getX(), me.getY());
                      }
            	   }
                 }
               }
            );
        
        jmi1.addActionListener(this);
        jmi2.addActionListener(this);
        jmi3.addActionListener(this);
        jmi4.addActionListener(this);
        jmi5.addActionListener(this);
        jmi6.addActionListener(this);
        jmi7.addActionListener(this);
        
        SearchLabel.setText("Search:");
        
        SearchTextBox.addActionListener(new java.awt.event.ActionListener() 
        {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                try 
                {
					SearchTextBoxActionPerformed(evt);
				 } 
                catch (JWNLException e) 
                {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
            }
        });
        
        listModel.addElement(" ");
        //listModel.clear();
       
        SearchList.setEnabled(false);
        SearchList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                SearchListValueChanged(evt);
            }
        });
        
        jScrollPane1.setViewportView(SearchList);

        DownloadButton.setText("Download");
        DownloadButton.setEnabled(false);
        DownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					DownloadButtonActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DownloadButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SearchLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SearchTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchLabel)
                    .addComponent(SearchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(DownloadButton)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void actionPerformed(ActionEvent ae)
    {
    	  if (ae.getSource() == jmi1)
    	  {
    		    //JOptionPane.showMessageDialog(null, "Select directory to save file(s)");
    	        //FileChooser chooser = new FileChooser();
    	        //String dir = chooser.Run();
    	        //System.out.println(dir);
    		    JFileChooser ch = new JFileChooser();
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
  	        
  	            dir = dir + File.separator;
    	        
    	    	for(int i=0;i<indexs.length;i++)
    	    	{ 
    	    		String file = listModel.elementAt(indexs[i]).toString();
    	    		if(file.startsWith("[DIR]"))
    	    		{
    	    			JOptionPane.showMessageDialog(null,"Incorrect action");
    	    		 }
    	    		else if(file.startsWith("[FILE]"))
    	    		{
    	    			/*String RemoteFile = Files.elementAt(indexs[i]-1-Dirs.size()).toString();
        	    		String RemoteFilePath = FilenameUtils.getFullPath(RemoteFile);
        	    		String RemoteFileName = FilenameUtils.getName(RemoteFile);
    	    			String LocalFile = dir + FilenameUtils.getName(Files.elementAt(indexs[i]-1-Dirs.size()).toString());
        	    		String LocalFilePath = dir;
        	    		System.out.println(RemoteFile);
        	    	    System.out.println(LocalFile);
        	    	    try
        	    	    {
        	    	       if(OS.contentEquals("Linux"))
        	    	       {	   
        	    	    	 String command = "scp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
        					 Process proc1 = Runtime.getRuntime().exec(command);
        	    	        }
        	    	       else if(OS.contains("Windows"))
        	    	       {
        	    	    	 String command = "C:\\pscp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
          					 Process proc1 = Runtime.getRuntime().exec(command);   
        	    	        }	   
    					 } 
        	    	    catch (IOException e)
        	    	    {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					 }*/
    	    			
            	        String RemoteFile = FilenameUtils.getName(Files.elementAt(indexs[i]-1-Dirs.size()).toString());
            	    	String RemoteFilePath = FilenameUtils.getFullPath(Files.elementAt(indexs[i]-1-Dirs.size()).toString());
            	    	String LocalFilePath = dir;
            	    	DownloadFile(RemoteFile, RemoteFilePath, LocalFilePath);
    	    		 }
    	    		else if(file.startsWith(".."))
    	    		{
    	    			JOptionPane.showMessageDialog(null,"Incorrect action");	 
    	    		 }
    	    		else
    	    		{
    	    			/*String RemoteFile = ResultSearchElements.elementAt(indexs[i]).toString();
        	    		String LocalFile = dir + FilenameUtils.getName(ResultSearchElements.elementAt(indexs[i]).toString());
        	    		System.out.println(RemoteFile);
        	    	    System.out.println(LocalFile);
        	    	    try
        	    	    {
        	    	       if(OS.contentEquals("Linux"))
        	    	       {	   
        	    	    	 String command = "scp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
        					 Process proc1 = Runtime.getRuntime().exec(command);
        	    	        }
        	    	       else if(OS.contains("Windows"))
        	    	       {
        	    	    	 String command = "C:\\pscp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
          					 Process proc1 = Runtime.getRuntime().exec(command);  
        	    	        }	    
    					 } 
        	    	    catch (IOException e)
        	    	    {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					 }	*/
             	        //String RemoteFile = ResultSearchElements.elementAt(indexs[i]).toString();
            	    	//String LocalFile = dir + FilenameUtils.getName(ResultSearchElements.elementAt(indexs[i]).toString());
             	        String RemoteFile = FilenameUtils.getName(ResultSearchElements.elementAt(indexs[i]).toString());
             	    	String RemoteFilePath = FilenameUtils.getFullPath(ResultSearchElements.elementAt(indexs[i]).toString());
             	    	String LocalFilePath = dir;
             	    	DownloadFile(RemoteFile, RemoteFilePath, LocalFilePath);
    	    		 }	
    	    		
    	    	  //listModel.getElementAt(indexs[i]).toString();
    	    	 }
    	     }
    	   else if (ae.getSource() == jmi2)
    	   {
    		   //String FilePath = " ";
    		   if(indexs.length == 1)
    		   {	   
    		     for(int i=0;i<indexs.length;i++)
    		     {	   
    		       String file = listModel.elementAt(indexs[i]).toString();
    		       if(file.startsWith("[DIR]"))
    		       {
    		    	   //String FilePath = " ";
             		   if(indexs.length == 1)
             		   {	   	   
             		     FilePath = Dirs.elementAt(indexs[i]-1).toString() + File.separator;
             		     String message = "Directory" + " " + User + " " + FilePath; 
             		     out.println(message);
             		    } 
    		        }
    		       else if(file.startsWith("[FILE]"))
    		       {
    		    	   JOptionPane.showMessageDialog(null,"Incorrect action");	 
    		        }
    		       else if(file.startsWith(".."))
    		       {
    		    	   JOptionPane.showMessageDialog(null,"Incorrect action");	
    		        }
    		       else
    		       {
    		    	   FilePath = FilenameUtils.getFullPath(ResultSearchElements.elementAt(indexs[i]).toString());
        		       String message = "Directory" + " " + User + " " + FilePath; 
        		       out.println(message); 
    		        }	   
    		      }  
    		    }
    	    }
    	   else if (ae.getSource() == jmi3)
    	   {
    		   //JOptionPane.showMessageDialog(null, "Select folder to save entire directory");
   	           //FileChooser ch = new FileChooser();
   	           //String dir = ch.Run();
    		   JFileChooser ch = new JFileChooser();
    	       String chtitle = "Select folder to save entire directory";
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
    	        
    	       dir = dir + File.separator;
   	           for(int i=0;i<indexs.length;i++)
	    	   { 
   	        	String file = listModel.elementAt(indexs[i]).toString();
                if(file.startsWith("[DIR]"))
                {
                	String RemoteDir = /*FilenameUtils.getFullPath*/(Dirs.elementAt(indexs[i]-1).toString());
    	    		String LocalDir = dir;
    	    		DownloadDirectory(RemoteDir,LocalDir);
    	    	    /*try
    	    	    {
    				   if(OS.contentEquals("Linux"))
    				   {	   
    	    	    	 String command = "scp -r " + serveruser + "@" + serveripaddr + ":" + RemoteDir + " " + LocalDir;
    					 Process proc1 = Runtime.getRuntime().exec(command);
    				    }
    				   else if(OS.contains("Windows"))
    				   {
    					   String command = "C:\\pscp -r " + serveruser + "@" + serveripaddr + ":" + RemoteDir + " " + LocalDir;
      					 Process proc1 = Runtime.getRuntime().exec(command);  
    				    }	   
    				 } 
    	    	    catch (IOException e)
    	    	    {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				 }*/
                 }
                else if(file.startsWith("[FILE]"))
                {
                	JOptionPane.showMessageDialog(null,"Incorrect action");
                 }
                else if(file.startsWith(".."))
                {
                	JOptionPane.showMessageDialog(null,"Incorrect action");
                 }
                else
                {
                	String RemoteDir = FilenameUtils.getFullPath(ResultSearchElements.elementAt(indexs[i]).toString());
    	    		String LocalDir = dir;
    	    		DownloadDirectory(RemoteDir,LocalDir);
    	    	    /*try
    	    	    {
    	    	       if(OS.contentEquals("Linux"))
     				   {	
    	    	    	 String command = "scp -r " + serveruser + "@" + serveripaddr + ":" + RemoteDir + " " + LocalDir;
    					 Process proc1 = Runtime.getRuntime().exec(command);
     				    }
    	    	       else if(OS.contains("Windows"))
    				   {
    	    	    	 String command = "C:\\pscp -r " + serveruser + "@" + serveripaddr + ":" + RemoteDir + " " + LocalDir;
      					 Process proc1 = Runtime.getRuntime().exec(command);  
    				    }
    				 } 
    	    	    catch (IOException e)
    	    	    {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				 }*/	
                 }	
	    	    //listModel.getElementAt(indexs[i]).toString();
	    	   }
    	    }
    	   else if (ae.getSource() == jmi4)
    	   {
    		   String LocalFile, RemoteFile, RemoteDir;
    		   JFileChooser fileChooser = new JFileChooser("File Dialog");
    		   fileChooser.setMultiSelectionEnabled(true);
    		   int returnVal = fileChooser.showOpenDialog(this);
    		   if (returnVal == JFileChooser.APPROVE_OPTION) 
    		   {
    		     fs = fileChooser.getSelectedFiles();
    		    }
    		   
    		   for(int i=0;i<indexs.length;i++)
	    	   {
    			String file = listModel.elementAt(indexs[i]).toString();   
    		    if(file.startsWith("[DIR]"))
    		    {
    		    	for(int j=0;j<fs.length;j++)
        		    {	   
        		      LocalFile = fs[j].getPath();
        		      RemoteDir = Dirs.elementAt(indexs[i]-1).toString();
        		      UploadFile(RemoteDir, LocalFile);
        		      //RemoteFile = FilenameUtils.getName(fs[j].getPath());
        		      /*try
        		      {
        		    	if(OS.contentEquals("Linux"))
       				    {
        		    	  String command = "scp " + LocalFile + " " + serveruser + "@" + serveripaddr + ":" + RemoteDir + File.separator + RemoteFile;
    				      Process proc1 = Runtime.getRuntime().exec(command);
       				     }
        		    	else if(OS.contains("Windows"))
     				    {
        		    	  String command = "C:\\pscp " + LocalFile + " " + serveruser + "@" + serveripaddr + ":" + RemoteDir + File.separator + RemoteFile;
      				      Process proc1 = Runtime.getRuntime().exec(command);	
     				     }
        		      }
        		      catch (IOException e)
      	    	      {  
      					// TODO Auto-generated catch block
      					e.printStackTrace();
      				   }*/
        		     }	
    		     }
    		    else if(file.startsWith("[FILE]"))
    		    {
    		        	
    		     }
    		    else if(file.startsWith(".."))
    		    {
    		    	
   		         }
    		    else
    		    {
    		    	for(int j=0;j<fs.length;j++)
        		    {	   
        		      LocalFile = fs[j].getPath();
        		      RemoteDir = FilenameUtils.getFullPath(ResultSearchElements.elementAt(indexs[i]).toString());
        		      UploadFile(RemoteDir, LocalFile);
        		      //RemoteFile = FilenameUtils.getName(fs[j].getPath());
        		      /*try
        		      {
        		    	if(OS.contentEquals("Linux"))
       				    {
        		    	  String command = "scp " + LocalFile + " " + serveruser + "@" + serveripaddr + ":" + RemoteDir + RemoteFile;
    				      Process proc1 = Runtime.getRuntime().exec(command);
       				     }
        		    	else if(OS.contains("Windows"))
     				    {
        		    	  String command = "C:\\pscp " + LocalFile + " " + serveruser + "@" + serveripaddr + ":" + RemoteDir + RemoteFile;
      				      Process proc1 = Runtime.getRuntime().exec(command);	
     				     }
        		      }
        		      catch (IOException e)
      	    	      {  
      					// TODO Auto-generated catch block
      					e.printStackTrace();
      				   }*/
        		     }	
    		     }	
	    	    }
    	    }
    	   else if (ae.getSource() == jmi5)
    	   {
    		    //JOptionPane.showMessageDialog(null, "Select folder to upload directory");
    	        //FileChooser chooser = new FileChooser();
    	        //String dir = chooser.Run();
    		   JFileChooser ch = new JFileChooser();
    	       String chtitle = "Select folder to upload";
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
    	        
    	       dir = dir + File.separator;
    	        
    	        for(int i=0;i<indexs.length;i++)
 	    	    {
     			  String file = listModel.elementAt(indexs[i]).toString();   
     		      if(file.startsWith("[DIR]"))
     		      {
     		    	String RemoteDir = Dirs.elementAt(indexs[i]-1).toString() + File.separator;
     	    		String LocalDir = dir;
     	    		UploadDirectory(LocalDir,RemoteDir);
     	    	    /*try
     	    	    {
     	    	       if(OS.contentEquals("Linux"))
     				   {
     	    	    	 String command = "scp -r " + LocalDir + " " + serveruser + "@" + serveripaddr + ":" + RemoteDir;
     					 Process proc1 = Runtime.getRuntime().exec(command);
     				    }
     	    	       else if(OS.contains("Windows"))
    				   {
     	    	    	 String command = "C:\\pscp -r " + LocalDir + " " + serveruser + "@" + serveripaddr + ":" + RemoteDir;
      					 Process proc1 = Runtime.getRuntime().exec(command);  
    				    }
     				 } 
     	    	    catch (IOException e)
     	    	    {
     					// TODO Auto-generated catch block
     					e.printStackTrace();
     				 } */
     		       }
     		      else if(file.startsWith("[FILE]"))
    		      {
    		    	  
    		       }
     		      else if(file.startsWith("[..]"))
     		      {
     		    	  
     		       }
     		      else
     		      {
     		    	String RemoteDir = FilenameUtils.getFullPath(ResultSearchElements.elementAt(indexs[i]).toString());
      	    		String LocalDir = dir;
      	    		UploadDirectory(LocalDir,RemoteDir);
      	    	    /*try
      	    	    {
      	    	       if(OS.contentEquals("Linux"))
     				   {	
      	    	    	 String command = "scp -r " + LocalDir + " " + serveruser + "@" + serveripaddr + ":" + RemoteDir;
      					 Process proc1 = Runtime.getRuntime().exec(command);
     				    }
      	    	       else if(OS.contains("Windows"))
    				   {
      	    	    	 String command = "C:\\pscp -r " + LocalDir + " " + serveruser + "@" + serveripaddr + ":" + RemoteDir;
      					 Process proc1 = Runtime.getRuntime().exec(command);  
    				    }
      				 } 
      	    	    catch (IOException e)
      	    	    {
      					// TODO Auto-generated catch block
      					e.printStackTrace();
      				 }*/  
     		       }	  
 	    	    }
    	     }
    	   else if (ae.getSource() == jmi6)
    	   {
    		   String NewDir = JOptionPane.showInputDialog("New directory name");
    		   for(int i=0;i<indexs.length;i++)
  		       {	   
  		         String file = listModel.elementAt(indexs[i]).toString();
  		         if(file.startsWith("[DIR]"))
  		         {
  		    	   //String FilePath = " ";
           		   if(indexs.length == 1)
           		   {	   	   
           		     FilePath = Dirs.elementAt(indexs[i]-1).toString() + File.separator;
           		     String message = "Create" + " " + User + " " + FilePath + NewDir; 
           		     out.println(message);
           		    } 
  		          }
  		         else if(file.startsWith("[FILE]"))
  		         {
  		    	   JOptionPane.showMessageDialog(null,"Incorrect action");	 
  		          }
  		         else if(file.startsWith(".."))
  		         {
  		    	   JOptionPane.showMessageDialog(null,"Incorrect action");	
  		          }
  		         else
  		         {
  		    	   FilePath = FilenameUtils.getFullPath(ResultSearchElements.elementAt(indexs[i]).toString());
      		       String message = "Create" + " " + User + " " + FilePath + NewDir; 
      		       out.println(message); 
  		          }	   
  		        }    
    	    }
    	   else if (ae.getSource() == jmi7)
    	   {
    		   Boolean answer;
    		   for(int i=0;i<indexs.length;i++)
  		       {	   
  		         String file = listModel.elementAt(indexs[i]).toString();
  		         if(file.startsWith("[DIR]"))
  		         {
  		           JOptionPane.showMessageDialog(null,"Incorrect action");
  		          }
  		         else if(file.startsWith("[FILE]"))
  		         {
  		    	   JOptionPane.showMessageDialog(null,"Incorrect action");	 
  		          }
  		         else if(file.startsWith(".."))
  		         {
  		    	   JOptionPane.showMessageDialog(null,"Incorrect action");	
  		          }
  		         else
  		         {
  		    	   //Show Output Dialog with video information
  		           //System.out.println(ResultSearchElements.elementAt(indexs[i]));
  		           String message = "GetInfo" + " " + User + " " + ResultSearchElements.elementAt(indexs[i]);
    		       out.println(message);
  		          }	   
  		        }  
    	    }
    	  }

    private void DownloadFile(String RemoteFile, String RemoteFilePath, String LocalFilePath)
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
    
    private void DownloadDirectory(String sftpFolder, String toLocalFolder)
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
    
    private void UploadFile(String destFolder, String filePath)
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

    private void UploadDirectory(String sendingFolder, String destFolder)
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
    private void DownloadButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_DownloadButtonActionPerformed
        // TODO add your handling code here:
    	JOptionPane.showMessageDialog(null, "Select directory to save file(s)");
        FileChooser chooser = new FileChooser();
        String dir = chooser.Run();
        //System.out.println(dir);
        
    	for(int i=0;i<indexs.length;i++)
    	{ 
    		String file = listModel.elementAt(indexs[i]).toString();
    		if(file.startsWith("[DIR]"))
    		{
    			String RemoteDir = FilenameUtils.getFullPath(Dirs.elementAt(indexs[i]-1).toString());
	    		String LocalDir = dir;
	    	    try
	    	    {
	    	       if(OS.contentEquals("Linux"))
 				   {
	    	    	 String command = "scp -r " + serveruser + "@" + serveripaddr + ":" + RemoteDir + " " + LocalDir;
					 Process proc1 = Runtime.getRuntime().exec(command);
 				    }
	    	       else if(OS.contains("Windows"))
				   {
	    	    	 String command = "C:\\pscp -r " + serveruser + "@" + serveripaddr + ":" + RemoteDir + " " + LocalDir;
					 Process proc1 = Runtime.getRuntime().exec(command);  
				    }
				 } 
	    	    catch (IOException e)
	    	    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
    		 }
    		else if(file.startsWith("[FILE]"))
    		{
    			String RemoteFile = Files.elementAt(indexs[i]-1-Dirs.size()).toString();
	    		String LocalFile = dir + FilenameUtils.getName(Files.elementAt(indexs[i]-1-Dirs.size()).toString());
	    		System.out.println(RemoteFile);
	    	    System.out.println(LocalFile);
	    	    try
	    	    {
	    	       if(OS.contentEquals("Linux"))
 				   {
	    	    	 String command = "scp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
					 Process proc1 = Runtime.getRuntime().exec(command);
 				    }
	    	       else if(OS.contains("Windows"))
				   {
	    	    	 String command = "C:\\pscp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
					 Process proc1 = Runtime.getRuntime().exec(command);  
				    }
				 } 
	    	    catch (IOException e)
	    	    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
    		 }
    		else if(file.startsWith(".."))
    		{
    			JOptionPane.showMessageDialog(null,"Incorrect action");	 
    		 }
    		else
    		{
    			String RemoteFile = ResultSearchElements.elementAt(indexs[i]).toString();
	    		String LocalFile = dir + FilenameUtils.getName(ResultSearchElements.elementAt(indexs[i]).toString());
	    		System.out.println(RemoteFile);
	    	    System.out.println(LocalFile);
	    	    try
	    	    {
	    	       if(OS.contentEquals("Linux"))
 				   {
	    	    	 String command = "scp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
					 Process proc1 = Runtime.getRuntime().exec(command);
 				    }
	    	       else if(OS.contains("Windows"))
				   {
	    	    	 String command = "C:\\pscp " + serveruser + "@" + serveripaddr + ":" + RemoteFile + " " + LocalFile;
					 Process proc1 = Runtime.getRuntime().exec(command);  
				    }
				 } 
	    	    catch (IOException e)
	    	    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }	
    		 }	
    	 }
    }//GEN-LAST:event_DownloadButtonActionPerformed

    private void SearchListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_SearchListValueChanged
        // TODO add your handling code here:
    	indexs = SearchList.getSelectedIndices();
    	if(indexs.length>0)
    	{DownloadButton.setEnabled(true);}	
    }//GEN-LAST:event_SearchListValueChanged

    private void SearchTextBoxActionPerformed(java.awt.event.ActionEvent evt) throws JWNLException
    {
    	String message = "Search" + " " + User + " " + WAction(SearchTextBox.getText());//SearchTextBox.getText();
    	System.out.println("Sending Search to Server..." + " " + message);
    	out.println(message);
    	//int distance = StringUtils.getLevenshteinDistance("iscar", "oscar");
    	//System.out.println(distance);
    	
    }
    
    private String WAction(String SearchPhrase) throws JWNLException
    {
    	// TODO add your handling code here:
    	System.out.println("text changed");
    	List<String> DoubleQuote, SingleQuote, NoQuotes;
    	String SynStem, SynSearchPhrase, stem;
    	//SearchPhrase = SearchTextBox.getText();
    	Stemmer st = new Stemmer();
    	
    	DoubleQuote = new ArrayList<String>();
    	SingleQuote = new ArrayList<String>();
    	NoQuotes = new ArrayList<String>();
    	Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
    	Matcher regexMatcher = regex.matcher(SearchPhrase);
    	while (regexMatcher.find()) {
    	    if (regexMatcher.group(1) != null) {
    	        // Add double-quoted string without the quotes
    	        DoubleQuote.add("\"" + regexMatcher.group(1) + "\"");
    	    } else if (regexMatcher.group(2) != null) {
    	        // Add single-quoted string without the quotes
    	        SingleQuote.add(regexMatcher.group(2));
    	    } else {
    	        // Add unquoted word
    	        NoQuotes.add(regexMatcher.group());
    	    }
    	}  
    		
    	  //tokens = NoQuotes.size();//SearchPhrase.split(" ");
    	  for(int i=0;i<NoQuotes.size();i++)
    	  {
    	   //String check = NoQuotes.get(i).toString();
    	   if((!NoQuotes.get(i).toString().contentEquals("AND")) && (!NoQuotes.get(i).toString().contentEquals("OR")) && (!NoQuotes.get(i).toString().contentEquals("NOT")) && (!NoQuotes.get(i).toString().contentEquals("and")) && (!NoQuotes.get(i).toString().contentEquals("or")) && (!NoQuotes.get(i).toString().contentEquals("not"))) 
    	   { 
    	    stem = st.Stem(NoQuotes.get(i).toString());
    	    SynReplace synstem = new SynReplace(stem);
      	    SynReplace synsearch = new SynReplace(NoQuotes.get(i).toString());
      	    SynStem = synstem.ReturnPhrase();
      	    SynSearchPhrase = synstem.ReturnPhrase();
      	  
      	    if(!SynStem.contentEquals(SynSearchPhrase))
      	    {
      		  SearchPhrase += " " + SynStem + " " + SynSearchPhrase;  
      	     }
      	    else
      	    {
      	    	SearchPhrase += " " + "OR" + " " + SynStem;	
      	     }	
    	    System.out.println(SearchPhrase);
    	  
    	    /*POS[] pos;
    	    pos = wrd.findPartsOfSpeech(tokens[i]);
    	    if(pos != null)
    	    {	  
    	     for(int j=0; j<pos.length;j++)
    	     {
    	      String wordpos = tokens[i] + "/" + pos[j].getLabel();
    	      System.out.println(wordpos);
    	     }
    	    }*/
    	   }
    	  }
    	return SearchPhrase;
    }
    
    private void RegisterWithServer()
    {
      //client = new Client(playuser,"localhost",8181);	 
      try 
      {
   	   if(this.Register())
   	   {
   		 JOptionPane.showMessageDialog(null, "Connection Succesfull");
   		 //this.RunClient();
   	    }
   	   else
   	   {
   	     JOptionPane.showMessageDialog(null, "Connection not performed with Server");   
   		}
      } 
      catch (HeadlessException e) 
      {
   	e.printStackTrace();
       } 
      catch (IOException e) 
      {
   	e.printStackTrace();
       } 
     } 
    
    private void RunClient()//Listen
    {
      
      try
      {
    	if(this.ConnectWithServer())
    	{	
    	 this.GetStreams();
    	 this.RegisterWithServer();
    	 this.ProcessConnection();
    	 }
    	//return messages;
       }
      catch(EOFException e)
      {
    	JOptionPane.showMessageDialog(null, "Client ends connection");
    	//return null;
       }
      catch(IOException e)
      {
    	e.printStackTrace();
    	//return null;
       }
      finally
      {
    	this.CloseConnection();  
       }
     }
    
    private boolean Register() throws IOException 
    {
      if(client.isConnected())
      {	
   	 //this.GetStreams();
   	 this.SendInfo();
   	 //this.CloseConnection();
   	 return true;
   	}
      else
      {
   	 return false;	
   	}
     }
    
    private boolean ConnectWithServer()
    {
      try
      {
   	    client = new Socket(InetAddress.getByName(host),port);
   	    System.out.println(client.getInetAddress().getHostName().toString());
    	return client.isConnected();
       }
      catch(IOException e)
      {
    	e.printStackTrace();
    	return false;
       }
     }
    
    private boolean GetStreams()
    {
      try
      {
   	     in = new BufferedReader(new InputStreamReader(client.getInputStream()));
     	 out = new PrintWriter(client.getOutputStream(), true);
   	     return true;
   	   } 
      catch (IOException e) 
      {
   	    e.printStackTrace();
   	    return false;
   	   }
     }
    
    private void ProcessConnection() throws IOException
    {
      do
      {	  
       try 
       {
   		 messages = in.readLine();
   		 System.out.println("Message from server: " + messages);
   		 Strtokens = new StringTokenizer(messages);
   		 if(messages.startsWith("Result"))
   		 {
   		   //JOptionPane.showMessageDialog(null, playuser.GetUserName() + " " + "Users message received");
   		   //messages = messages.substring(7);
   		   //Strtokens = new StringTokenizer(Result);
   		   //listModel.clear();
   		   listModel.removeAllElements();
   		   ResultSearchElements.removeAllElements();
   		   SearchList.setEnabled(false);
   		   DownloadButton.setEnabled(false);
	       String Mess = Strtokens.nextToken();
	       String Us = Strtokens.nextToken();
	       if(Us.contentEquals(User))
	       {	   
	        while(Strtokens.hasMoreTokens())
	        {
	        	String temp = Strtokens.nextToken();
	        	ResultSearchElements.add(temp);
	        	temp = FilenameUtils.getBaseName(temp);
	        	listModel.addElement(temp);
	         }
	        
	        SearchList.repaint();
	        SearchList.setEnabled(true);
	        //DownloadButton.setEnabled(true);
	       }
   		  }
   		 else if(messages.startsWith("Created"))
   		 {
   			String Mess = Strtokens.nextToken();
   			String Us = Strtokens.nextToken();
   			String Res = Strtokens.nextToken();
   			if(Us.contentEquals(User))
   			{
   			  JOptionPane.showMessageDialog(null, Res);	
   			 }
   		  }
   		 else if(messages.startsWith("Directory"))
   		 {
   			String tmpdir;
   			String Mess = Strtokens.nextToken();
   			String Us = Strtokens.nextToken();
   			Dirs.removeAllElements();
   			if(Us.contentEquals(User))
   			{
   				while(Strtokens.hasMoreTokens())
   		        {
   				  String temp = Strtokens.nextToken();
   				  File tmp = new File(temp);
   				  tmpdir = tmp.getParent() + File.separator;
   				  if(OS.contains("Windows"))
   				  {
   					tmpdir = tmpdir.replace("\\", "/");  
   				   }	  
   				  if(FilePath.contentEquals(tmpdir))
   				  {	  
   				    Dirs.add(temp);
   				   }
   		        }
   			 }
   			listModel.removeAllElements();
   			listModel.addElement("..");
   			for(int i=0;i<Dirs.size();i++)
   			{
   			  listModel.addElement("[DIR] " + FilenameUtils.getBaseName(Dirs.elementAt(i)));
   			  ImageIcon image_icon = new ImageIcon("/media/Respaldo/Desktop/Tesis/DBRIcons/groups_folder.png");
   			  icon.put("[DIR] " + FilenameUtils.getBaseName(Dirs.elementAt(i)), image_icon);
   			  
   			  //listModel.addElement();
   			 }
   			SearchList.setCellRenderer(new Icons(icon));
   		  }
   		 else if(messages.startsWith("Files"))
  		 {
   			String Mess = Strtokens.nextToken();
   			String Us = Strtokens.nextToken();
   			Files.removeAllElements();
   			if(Us.contentEquals(User))
   			{
   				while(Strtokens.hasMoreTokens())
   		        {
   				  String temp = Strtokens.nextToken();
   				  Files.add(temp);
   		         }
   			 }
   			for(int i=0;i<Files.size();i++)
   			{
   			  listModel.addElement("[FILE] " + FilenameUtils.getName(Files.elementAt(i)));
   			  if(FilenameUtils.getExtension(Files.elementAt(i)).contentEquals("xml"))
   			  {	  
   			    ImageIcon image_icon = new ImageIcon("/media/Respaldo/Desktop/Tesis/DBRIcons/xml.png");
   			    icon.put("[FILE] " + FilenameUtils.getName(Files.elementAt(i)), image_icon);
   			   }
   			  else if(FilenameUtils.getExtension(Files.elementAt(i)).contentEquals("avi"))
 			  {	  
 			    ImageIcon image_icon = new ImageIcon("/media/Respaldo/Desktop/Tesis/DBRIcons/filetype_avi.png");
 			    icon.put("[FILE] " + FilenameUtils.getName(Files.elementAt(i)), image_icon);
 			   }
   			 }
   			SearchList.setCellRenderer(new Icons(icon));
  		  }
   		 else if(messages.startsWith("Ready"))
   		 {
   		     Ready = Strtokens.nextToken();
   		     dbdir = Strtokens.nextToken();
   		     try 
  		     {
      	       //JOptionPane.showMessageDialog(null, "Select video directory");
  	           //FileChooser ch = new FileChooser();
  	           //String dir = ch.Run();
   		       JFileChooser ch = new JFileChooser();;
   		       String chtitle = "Select video directory";
   		       String dir = null;
   		       ch.setCurrentDirectory(new java.io.File("."));
   		       ch.setDialogTitle(chtitle);
   		       ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
   		       ch.setAcceptAllFileFilterUsed(false);
   		       if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
   		       {  
   		    	   dir = ch.getSelectedFile().getPath();
   		    	}
   		       else 
   		       {
   		    	   System.out.println("No Selection ");
   		    	}
   		       
   		       dir = dir + File.separator;	 
      	       FileSystemManager fsManager = VFS.getManager();
  	           FileObject listendir = fsManager.resolveFile(dir);
  	           DefaultFileMonitor fm = new DefaultFileMonitor(new FileListenerClient(this.User,this.serveripaddr,this.password,this.dbdir));
  	           fm.setRecursive(true);
  	           fm.addFile(listendir);
  	           fm.start();
  	          } 
  		     catch (FileSystemException e) 
  		     {
  	           // TODO Auto-generated catch block
  	           e.printStackTrace();
  	          }
   		      //JOptionPane.showMessageDialog(null,Ready + " " + playuser.GetUserName());
   		  }
   		 else if(messages.startsWith("Elements"))
  		 {
   			String Mess = Strtokens.nextToken();
   			String Us = Strtokens.nextToken();
   			Elements.clear();
   			if(Us.contentEquals(User))
   			{
   				while(Strtokens.hasMoreTokens())
   		        {
   				  String temp = Strtokens.nextToken();
   				  Elements.add(temp);
   		        }
   			 }
  		  }
   		 else if(messages.startsWith("Attributes"))
 		 {
   			String Mess = Strtokens.nextToken();
   			String Us = Strtokens.nextToken();
   			Attributes.clear();
   			if(Us.contentEquals(User))
   			{
   				while(Strtokens.hasMoreTokens())
   		        {
   				  String temp = Strtokens.nextToken();
   				  Attributes.add(temp);
   		        }
   			 }
   			DisplayFrame frame = new DisplayFrame(Elements, Attributes);
   			frame.setVisible(true);
 		  }
   	    } 
       catch (IOException e)
       {
   		 e.printStackTrace();
   		 //return null;Opframes
   	    }
       }
      while(!messages.equals("Server>>>End"));
      System.out.println("Client Ends");
     }
    
    private boolean CloseConnection()
    {
      try
      {
    	out.close();
    	in.close();
    	client.close();
    	return true;
       }
      catch(IOException e)
      {
    	e.printStackTrace();
    	return false;
       }
     }
    
    private boolean SendInfo()
    {
    	messages = "Connection" + " " + User;
    	out.println(messages);
    	//messages = "Client>>>End"; 
    	//output.writeObject(messages);
    	//output.flush();
    	return true;
     }
   
    /*// setup mappings for which icon to use for each value

	Map<Object, Icon> icons = new HashMap<Object, Icon>();
	icons.put("details",
		MetalIconFactory.getFileChooserDetailViewIcon());
	icons.put("folder",
		MetalIconFactory.getTreeFolderIcon());
	icons.put("computer",
		MetalIconFactory.getTreeComputerIcon());

	JFrame frame = new JFrame("Icon List");
	frame.setDefaultCloseOperation(
		JFrame.DISPOSE_ON_CLOSE);

	// create a list with some test data

	JList list = new JList(
		new Object[] {
			"details", "computer", "folder", "computer"});

	// create a cell renderer to add the appropriate icon

	list.setCellRenderer(new Icons(icons));
	frame.add(list);
	frame.pack();
	frame.setVisible(true);*/
    
   public void run() 
   {
     this.RunClient();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    /**
	 * @uml.property  name="downloadButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JButton DownloadButton;
    /**
	 * @uml.property  name="searchLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel SearchLabel;
    /**
	 * @uml.property  name="searchList"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JList SearchList;
    /**
	 * @uml.property  name="searchTextBox"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JTextField SearchTextBox;
    /**
	 * @uml.property  name="jScrollPane1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JScrollPane jScrollPane1;
    /**
	 * @uml.property  name="listModel"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private DefaultListModel listModel;
    /**
	 * @uml.property  name="popupMenu"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JPopupMenu popupMenu;
    /**
	 * @uml.property  name="jmi1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JMenuItem jmi1;
	/**
	 * @uml.property  name="jmi2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JMenuItem jmi2;
	/**
	 * @uml.property  name="jmi3"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JMenuItem jmi3;
	/**
	 * @uml.property  name="jmi4"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JMenuItem jmi4;
	/**
	 * @uml.property  name="jmi5"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JMenuItem jmi5;
	/**
	 * @uml.property  name="jmi6"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JMenuItem jmi6;
	/**
	 * @uml.property  name="jmi7"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JMenuItem jmi7;
    
    // End of variables declaration//GEN-END:variables
}
