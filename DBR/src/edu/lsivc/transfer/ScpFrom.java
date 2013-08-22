package edu.lsivc.transfer;

import com.jcraft.jsch.*;

import java.awt.*;
import javax.swing.*;
import java.io.*;

public class ScpFrom
{
  /**
 * @uml.property  name="user"
 */
private String user;
  /**
 * @uml.property  name="host"
 */
private String host;
  /**
 * @uml.property  name="rfile"
 */
private String rfile;
  /**
 * @uml.property  name="lfile"
 */
private String lfile;
  /**
 * @uml.property  name="jsch"
 * @uml.associationEnd  
 */
private JSch jsch;
  /**
 * @uml.property  name="session"
 * @uml.associationEnd  
 */
private Session session;
  /**
 * @uml.property  name="ui"
 * @uml.associationEnd  
 */
private UserInfo ui;
  /**
 * @uml.property  name="channel"
 * @uml.associationEnd  
 */
private Channel channel;
  /**
 * @uml.property  name="out"
 */
private OutputStream out;
  /**
 * @uml.property  name="in"
 */
private InputStream in;
  /**
 * @uml.property  name="fos"
 */
private FileOutputStream fos;
  
  public ScpFrom(String usr, String hst)
  {
	user = usr;
    host = hst;
  }
  
  public void Transfer(String remotefile, String localfile, boolean dir) throws IOException
  {
	  this.rfile = remotefile;
	  this.lfile = localfile;
	  fos=null;
	  String prefix=null;
      if(new File(lfile).isDirectory()){
        prefix=lfile;
      }
      
      String command = "";
   // exec 'scp -f rfile' remotely
      if(!dir)
      {	  
        command="scp -f "+rfile;
       }
      else
      {
    	command="scp -r "+rfile + " " + user + ":" + lfile;
       }	  
      try {
		channel=session.openChannel("exec");
	} catch (JSchException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      ((ChannelExec)channel).setCommand(command);

      // get I/O streams for remote scp
      out=channel.getOutputStream();
      in=channel.getInputStream();

      try {
		channel.connect();
	} catch (JSchException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      
      byte[] buf=new byte[1024];

      // send '\0'
      buf[0]=0;  
      try {
    	  out.write(buf, 0, 1);
    	  out.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      while(true){
	int c = 0 ;
	try 
	{
		c = checkAck(in);
	} 
	catch (IOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
	if(c!='C')
	{
	  break;
	}

        // read '0644 '
        try {
			in.read(buf, 0, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        long filesize=0L;
        while(true){
          try {
			if(in.read(buf, 0, 1)<0){
			    // error
			    break; 
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          if(buf[0]==' ')break;
          filesize=filesize*10L+(long)(buf[0]-'0');
        }

        String file=null;
        for(int i=0;;i++){
          try {
			in.read(buf, i, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          if(buf[i]==(byte)0x0a){
            file=new String(buf, 0, i);
            break;
  	  }
        }

	//System.out.println("filesize="+filesize+", file="+file);

        // send '\0'
        buf[0]=0; 
        try {
			out.write(buf, 0, 1);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        

        // read a content of lfile
        fos=new FileOutputStream(prefix==null ? lfile : prefix+file);
        int foo;
        while(true){
          if(buf.length<filesize) foo=buf.length;
	  else foo=(int)filesize;
          foo=in.read(buf, 0, foo);
          if(foo<0){
            // error 
            break;
          }
          fos.write(buf, 0, foo);
          filesize-=foo;
          if(filesize==0L) break;
        }
        fos.close();
        fos=null;

	if(checkAck(in)!=0){
	  System.exit(0);
	}

        // send '\0'
        buf[0]=0; out.write(buf, 0, 1); out.flush();
      }

      //System.exit(0);
   }
  
  public void Connect()
  {
	    try
	    {
	      jsch=new JSch();
	      session=jsch.getSession(user, host, 22);

	      // username and password will be given via UserInfo interface.
	      ui=new MyUserInfo();
	      session.setUserInfo(ui);
	      session.connect();
	    }
	    catch(Exception e){
	      System.out.println(e);
	      try{if(fos!=null)fos.close();}catch(Exception ee){}
	    }  
  }
  
  public void Disconnect()
  {
	  session.disconnect();  
   }

  static int checkAck(InputStream in) throws IOException{
    int b=in.read();
    // b may be 0 for success,
    //          1 for error,
    //          2 for fatal error,
    //          -1
    if(b==0) return b;
    if(b==-1) return b;

    if(b==1 || b==2){
      StringBuffer sb=new StringBuffer();
      int c;
      do {
	c=in.read();
	sb.append((char)c);
      }
      while(c!='\n');
      if(b==1){ // error
	System.out.print(sb.toString());
      }
      if(b==2){ // fatal error
	System.out.print(sb.toString());
      }
    }
    return b;
  }

  public static class MyUserInfo implements UserInfo, UIKeyboardInteractive{
    public String getPassword(){ return passwd; }
    public boolean promptYesNo(String str){
      Object[] options={ "yes", "no" };
      int foo=JOptionPane.showOptionDialog(null, 
             str,
             "Warning", 
             JOptionPane.DEFAULT_OPTION, 
             JOptionPane.WARNING_MESSAGE,
             null, options, options[0]);
       return foo==0;
    }
  
    String passwd;
    JTextField passwordField=(JTextField)new JPasswordField(20);

    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return true; }
    public boolean promptPassword(String message){
      Object[] ob={passwordField}; 
      int result=
	  JOptionPane.showConfirmDialog(null, ob, message,
					JOptionPane.OK_CANCEL_OPTION);
      if(result==JOptionPane.OK_OPTION){
	passwd=passwordField.getText();
	return true;
      }
      else{ return false; }
    }
    public void showMessage(String message){
      JOptionPane.showMessageDialog(null, message);
    }
    final GridBagConstraints gbc = 
      new GridBagConstraints(0,0,1,1,1,1,
                             GridBagConstraints.NORTHWEST,
                             GridBagConstraints.NONE,
                             new Insets(0,0,0,0),0,0);
    private Container panel;
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      panel = new JPanel();
      panel.setLayout(new GridBagLayout());

      gbc.weightx = 1.0;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.gridx = 0;
      panel.add(new JLabel(instruction), gbc);
      gbc.gridy++;

      gbc.gridwidth = GridBagConstraints.RELATIVE;

      JTextField[] texts=new JTextField[prompt.length];
      for(int i=0; i<prompt.length; i++){
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        panel.add(new JLabel(prompt[i]),gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        if(echo[i]){
          texts[i]=new JTextField(20);
        }
        else{
          texts[i]=new JPasswordField(20);
        }
        panel.add(texts[i], gbc);
        gbc.gridy++;
      }

      if(JOptionPane.showConfirmDialog(null, panel, 
                                       destination+": "+name,
                                       JOptionPane.OK_CANCEL_OPTION,
                                       JOptionPane.QUESTION_MESSAGE)
         ==JOptionPane.OK_OPTION){
        String[] response=new String[prompt.length];
        for(int i=0; i<prompt.length; i++){
          response[i]=texts[i].getText();
        }
	return response;
      }
      else{
        return null;  // cancel
      }
    }
  }
}
