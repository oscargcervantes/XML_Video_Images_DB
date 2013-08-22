package edu.lsivc.client;

import java.awt.*; // Color, Dimension, etc.
import java.awt.event.*; // ActionListener, ActionEvent, etc.
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

import javax.swing.*; // JPanel, JButton, JOptionPane, etc.

public class SearchClient implements Runnable 
{ 
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
 * @uml.property  name="tokens"
 */
private StringTokenizer tokens;
 /**
 * @uml.property  name="ready"
 */
private String Ready = "";
 //private Vector<String> Users = new Vector<String>();
 //private Vector<Integer> Ids = new Vector<Integer>();
 /**
 * @uml.property  name="user"
 */
private String User;
 // Constructor: sets up the GUI
 public SearchClient(String hostname, int portnumber, String User)
 {
  host = hostname;
  port = portnumber;
  this.User = User;
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
		 tokens = new StringTokenizer(messages);
		 if(messages.startsWith("Result"))
		 {
		   //JOptionPane.showMessageDialog(null, playuser.GetUserName() + " " + "Users message received");
		   while(tokens.hasMoreTokens())
		   {
			 User = tokens.nextToken();
			 if(!User.contentEquals("Result"))
			 {		 
			    
			  }
			}
		  }
		 else if(messages.startsWith("Ready"))
		 {
		   Ready = tokens.nextToken();
		   //JOptionPane.showMessageDialog(null,Ready + " " + playuser.GetUserName());
		  }		 
	    } 
    catch (IOException e)
    {
		 e.printStackTrace();
		 //return null;
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
 	messages = "Connection" + User;
 	out.println(messages);
 	//messages = "Client>>>End"; 
 	//output.writeObject(messages);
 	//output.flush();
 	return true;
  }

public void run() 
{
  this.RunClient();
 }
}
