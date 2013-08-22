package edu.lsivc.directory;

import javax.swing.*;

public class FileChooser
{
  /**
 * @uml.property  name="choosedir"
 */
private String choosedir;
  /**
 * @uml.property  name="currentdir"
 */
private String currentdir;
  
  public FileChooser(String currentdir)
  {
	choosedir = "";
	this.currentdir = currentdir;
   }
  
  public FileChooser()
  {
	choosedir = "";
	this.currentdir = ".";
   }
  
  public String Run()
  {
	  JFileChooser chooser = new JFileChooser();
	  chooser.setCurrentDirectory(new java.io.File(currentdir));
	  chooser.setDialogTitle("Chooser title");
	  chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	  chooser.setAcceptAllFileFilterUsed(false);

	  if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	  {
		//System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
		choosedir = chooser.getSelectedFile().getPath() + "/";
		return choosedir;
	   } 
	  else
	  {
		System.out.println("No Selection ");
		return null;
	   }  
   }
 }