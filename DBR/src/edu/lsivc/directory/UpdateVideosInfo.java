package edu.lsivc.directory;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import edu.lsivc.directory.Directory;
import edu.lsivc.xml.*;
import com.xuggle.xuggler.*;
import org.w3c.dom.*;


public class UpdateVideosInfo
{
  /**
 * @uml.property  name="videoFile"
 */
private String VideoFile;
  /**
 * @uml.property  name="confdir"
 */
private String confdir = null;
  /**
 * @uml.property  name="dbdir"
 */
private String dbdir = null;
  /**
 * @uml.property  name="videoname"
 */
private String videoname;
/**
 * @uml.property  name="extension"
 */
private String extension;
/**
 * @uml.property  name="basename"
 */
private String basename;
/**
 * @uml.property  name="parentpath"
 */
private String parentpath;
/**
 * @uml.property  name="filesize"
 */
private String filesize;
  /**
 * @uml.property  name="framesnumber"
 */
private String framesnumber;
/**
 * @uml.property  name="duration"
 */
private String duration;
/**
 * @uml.property  name="starttime"
 */
private String starttime;
/**
 * @uml.property  name="bitrate"
 */
private String bitrate;
/**
 * @uml.property  name="codectype"
 */
private String codectype;
/**
 * @uml.property  name="codecid"
 */
private String codecid;
  /**
 * @uml.property  name="videowidth"
 */
private String videowidth;
/**
 * @uml.property  name="videoheight"
 */
private String videoheight;
/**
 * @uml.property  name="videoformat"
 */
private String videoformat;
/**
 * @uml.property  name="framerate"
 */
private String framerate;
  /**
 * @uml.property  name="container"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
private IContainer container;
  /**
 * @uml.property  name="frameRate"
 */
private double FrameRate;
  /**
 * @uml.property  name="duration"
 */
private double Duration;
  /**
 * @uml.property  name="elements" multiplicity="(0 -1)" dimension="2"
 */
private String[][] Elements;
  /**
 * @uml.property  name="attributes" multiplicity="(0 -1)" dimension="2"
 */
private String[][] Attributes;
  /**
 * @uml.property  name="xml"
 * @uml.associationEnd  
 */
private XMLFile xml;
  
  public UpdateVideosInfo(String F)
  {
	VideoFile = F;

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
     }
    // Create a Xuggler container object
    container = IContainer.make();
    
    // Open up the container
    if (container.open(VideoFile, IContainer.Type.READ, null) < 0)
      throw new IllegalArgumentException("could not open file");
   }
  
  public void SearchUpdate()
  {	 
	  videoname = FilenameUtils.getName(VideoFile); //with extension
	  basename = FilenameUtils.getBaseName(VideoFile); //without extension
	  extension = FilenameUtils.getExtension(VideoFile);
	  parentpath = FilenameUtils.getFullPath(VideoFile);
	  filesize = String.valueOf(container.getFileSize());
	  duration = String.valueOf((container.getDuration()/1000)/1000);
	  starttime = String.valueOf((container.getStartTime()/1000)/1000);
	  bitrate = String.valueOf(container.getBitRate());
	  
	  // query how many streams the call to open found
	  int numStreams = container.getNumStreams();
	  Duration = container.getDuration();
	  
	  //iterate through the streams to print their meta data
	  for(int i = 0; i < numStreams; i++)
	  {
	      // Find the stream object
	      IStream stream = container.getStream(i);
	      // Get the pre-configured decoder that can decode this stream;
	      IStreamCoder coder = stream.getStreamCoder();
	      
	      if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO)
	      {
	    	codectype = coder.getCodecType().toString();
			codecid = coder.getCodecID().toString();
			videowidth = String.valueOf(coder.getWidth());
			videoheight = String.valueOf(coder.getHeight());
			videoformat = coder.getPixelType().toString();
			framerate = String.valueOf(coder.getFrameRate().getDouble());
			FrameRate = coder.getFrameRate().getDouble();
	      }
	   }
	  
	  double frames = (Duration*FrameRate/1000)/1000;
	  frames = Math.round(frames);
	  int framenumber = (int)frames;
	  
	  framesnumber = String.valueOf(framenumber);
	  
	  if(VideoFile.contains(dbdir + "DBGR" + File.separator))
	  {	  
	    String XML = parentpath + basename + ".xml";
		File f = new File(XML);
	    if(f.exists())
	    {
	       xml = new XMLFile(XML);
	       Elements = xml.GetElements();
	       String[] Els = new String[Elements[0].length];
	       int indexfilename,indexfilesize,indexframesnumber,indexextension,indexduration,indexframerate;
	       int indexstart,indexbitrate,indexcodectype,indexcodecid,indexwidth,indexheight,indexformat;
	       
	       for(int i=0;i<Elements[0].length;i++)
	       {
	    	 Els[i] = Elements[0][i];  
	        }	   
	       
	       Attributes = xml.Read(Els);
	       
	       indexfilename = GetIndex("File_Name", Elements);
	       indexfilesize = GetIndex("File_Size", Elements);
	       indexframesnumber = GetIndex("Frames_Number", Elements);
	       indexextension = GetIndex("Extension", Elements);
	       indexduration = GetIndex("Duration", Elements);
	       indexstart = GetIndex("Start_Time", Elements);
	       indexbitrate = GetIndex("Bit_Rate", Elements);
	       indexcodectype = GetIndex("Codec_Type", Elements);
	       indexcodecid = GetIndex("Codec_ID", Elements);
	       indexwidth = GetIndex("Video_Width", Elements);
	       indexheight = GetIndex("Video_Height", Elements);
	       indexformat = GetIndex("Video_Format", Elements);
	       indexframerate = GetIndex("Frame_Rate", Elements);
	       
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexfilename], basename);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexfilesize], filesize);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexframesnumber], framesnumber);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexextension], extension);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexduration], duration);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexstart], starttime);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexbitrate], bitrate);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexcodectype], codectype);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexcodecid], codecid);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexwidth], videowidth);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexheight], videoheight);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexformat], videoformat);
	       Write(Elements[0][0], Attributes[0][0], Elements[0][indexframerate], framerate);
	     }
	    else
	    {
		  System.out.println("File: " + basename + ".xml" + " not exists in Directory");  
	     }
	   }
	  else if(VideoFile.contains(dbdir + "DBFR" + File.separator))
	  {	  
		    String XML = parentpath + basename + ".xml";
			File f = new File(XML);
		    if(f.exists())
		    {
		       xml = new XMLFile(XML);
		       Elements = xml.GetElements();
		       String[] Els = new String[Elements[0].length];
		       int indexfilename,indexfilesize,indexframesnumber,indexextension,indexduration,indexframerate;
		       int indexstart,indexbitrate,indexcodectype,indexcodecid,indexwidth,indexheight,indexformat;
		       
		      for(int i=0;i<Elements.length;i++)
		  	  {	 
		  	   for(int j=0;j<Elements[0].length;j++)
		       {
		    	    Els[j] = Elements[i][j];  
		        }
		  	   }	   
		       
		       Attributes = xml.Read(Els);
		       
		       indexfilename = GetIndex("File_Name", Elements);
		       indexfilesize = GetIndex("File_Size", Elements);
		       indexframesnumber = GetIndex("Frames_Number", Elements);
		       indexextension = GetIndex("Extension", Elements);
		       indexduration = GetIndex("Duration", Elements);
		       indexstart = GetIndex("Start_Time", Elements);
		       indexbitrate = GetIndex("Bit_Rate", Elements);
		       indexcodectype = GetIndex("Codec_Type", Elements);
		       indexcodecid = GetIndex("Codec_ID", Elements);
		       indexwidth = GetIndex("Video_Width", Elements);
		       indexheight = GetIndex("Video_Height", Elements);
		       indexformat = GetIndex("Video_Format", Elements);
		       indexframerate = GetIndex("Frame_Rate", Elements);
		       
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexfilename], basename);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexfilesize], filesize);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexframesnumber], framesnumber);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexextension], extension);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexduration], duration);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexstart], starttime);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexbitrate], bitrate);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexcodectype], codectype);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexcodecid], codecid);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexwidth], videowidth);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexheight], videoheight);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexformat], videoformat);
		       Write(Elements[0][0], Attributes[0][0], Elements[0][indexframerate], framerate);
		     }
		    else
		    {
			  System.out.println("File: " + basename + ".xml" + " not exists in Directory");  
		     }
	   }
	  else if(VideoFile.contains(dbdir + "Videos" + File.separator))
	  {
		  GetVideosAndSave(VideoFile, parentpath);  
	  }	  
  }
  
  private int GetIndex(String Element, String[][] Elements)
  {
	int i=0,j=0,index=0;
	
	for(i=0;i<Elements.length;i++)
	{	
		for(j=0;j<Elements[0].length;j++)
		{	
			if(Elements[i][j].contentEquals(Element))
			{
			  index = j;
			}
		}
	}
	return index;  
   }
  
  private void Write(String El, String Attr, String UpdEl, String UpdAttr)
  {
	 Document doc = xml.Update(El, Attr, UpdEl, UpdAttr);
     if(doc != null)
     {
   	   xml.WriteResultFile(doc);  
      }
  }
  
  private void GetVideosAndSave(String videofile, String videodir)
  {  
  	  String ID="", Cam="", Direct="", Ang="", videoname="", DBType="", Shot="", Sesion="";
  	  Directory vidir = new Directory(videodir,".avi");
  	  StringTokenizer tokens;

  	  tokens = new StringTokenizer(videofile);
  	  while(tokens.hasMoreTokens())
  	  {	   
  		String str = tokens.nextToken(File.separator);
  		if(str.contentEquals("DBGR"))
  		{	 
  	       videoname = FilenameUtils.getName(videofile);
  	       boolean flag = videoname.matches("P[0-9][A-Z][RL][RL][RL][0-9][0-9].avi"); 
  	       DBType = str;
  	       if(flag)
  	       {
  	    	 ID = videoname.substring(0, 2);
  	         //System.out.println(ID);
  	    	 Sesion = videoname.substring(2, 3);
  	         Cam = videoname.substring(3, 4);
  	         //System.out.println(Cam);
  	         Direct = videoname.substring(4, 6);
  	         //System.out.println(Direct);
  	         Ang = videoname.substring(6, 8);
  	         }
  	       else if(flag = videoname.matches("P[0-9][0-9][A-Z][RL][RL][RL][0-9][0-9].avi"))
  	       {
  	    	 ID = videoname.substring(0, 3);
  	         //System.out.println(ID);
  	    	 Sesion = videoname.substring(3, 4);
  	         Cam = videoname.substring(4, 5);
  	         //System.out.println(Cam);
  	         Direct = videoname.substring(5, 7);
  	         //System.out.println(Direct);
  	         Ang = videoname.substring(7, 9);   
  	         }
  	       else if(flag = videoname.matches("P[0-9][0-9][0-9][A-Z][RL][RL][RL][0-9][0-9].avi"))
	       {
  	    	 ID = videoname.substring(0, 4);
  	         //System.out.println(ID);
  	    	 Sesion = videoname.substring(4, 5);
  	         Cam = videoname.substring(5, 6);
  	         //System.out.println(Cam);
  	         Direct = videoname.substring(6, 8);
  	         //System.out.println(Direct);
  	         Ang = videoname.substring(8, 10); 
	         }
  	       else if(flag = videoname.matches("P[0-9][0-9][0-9][0-9][A-Z][RL][RL][RL][0-9][0-9].avi"))
	       {
  	    	 ID = videoname.substring(0, 5);
  	         //System.out.println(ID);
  	    	 Sesion = videoname.substring(5, 6);
  	         Cam = videoname.substring(6, 7);
  	         //System.out.println(Cam);
  	         Direct = videoname.substring(7, 9);
  	         //System.out.println(Direct);
  	         Ang = videoname.substring(9, 11); 
	         }
  	       else if(flag = videoname.matches("P[0-9][0-9][0-9][0-9][0-9][A-Z][RL][RL][RL][0-9][0-9].avi"))
	       {
  	    	 ID = videoname.substring(0, 6);
  	         //System.out.println(ID);
  	    	 Sesion = videoname.substring(6, 7);
  	         Cam = videoname.substring(7, 8);
  	         //System.out.println(Cam);
  	         Direct = videoname.substring(8, 10);
  	         //System.out.println(Direct);
  	         Ang = videoname.substring(10, 12);  
	         }
  	       else if(flag = videoname.matches("P[0-9][0-9][0-9][0-9][0-9][0-9][A-Z][RL][RL][RL][0-9][0-9].avi"))
	       {
	    	 ID = videoname.substring(0, 7);
	         //System.out.println(ID);
	    	 Sesion = videoname.substring(7, 8);
	         Cam = videoname.substring(8, 9);
	         //System.out.println(Cam);
	         Direct = videoname.substring(9, 11);
	         //System.out.println(Direct);
	         Ang = videoname.substring(11, 13);  
	         }
  	         //System.out.println(Ang);
  	       String savevideo = dbdir + DBType + File.separator + ID + File.separator + Sesion + File.separator + Cam + File.separator + Direct + File.separator + Ang + File.separator + videoname;
  	       String dirvideo = dbdir + DBType + File.separator + ID + File.separator + Sesion + File.separator + Cam + File.separator + Direct + File.separator + Ang + File.separator;
  	       File f = new File(dirvideo);
  	       if(f.exists())
  	       {	   
  	        try 
  	        {
		      vidir.CopyFile(videofile, savevideo);
		 	 } 
  	        catch (IOException e) 
  	        {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
			 }
  	        vidir.Delete(videoname);
  	       }
  	       else
  	       {
  	    	 CreateDirectory cr = new CreateDirectory(dirvideo);
  	    	 cr.CreateSubDir();
  	    	 try 
  	         {
		       vidir.CopyFile(videofile, savevideo);
		 	  } 
  	         catch (IOException e) 
  	         {
		       // TODO Auto-generated catch block
		       e.printStackTrace();
			  }
  	         vidir.Delete(videoname);
  	        }	   
  		 }
  		else if(str.contentEquals("DBFR"))
  		{
  		   videoname = FilenameUtils.getName(videofile);
  		   boolean flag = videoname.matches("P[0-9][A-Z][AB].avi"); 
  		   DBType = str;
  		   if(flag)
  		   {	   
  		     ID = videoname.substring(0, 2);
  		     Sesion = videoname.substring(2,3);
  		     Shot = videoname.substring(3,4);
  		    }
  		   else if(flag = videoname.matches("P[0-9][0-9][A-Z][AB].avi"))
  		   {
  			 ID = videoname.substring(0, 3);
  			 Sesion = videoname.substring(3,4);
  		     Shot = videoname.substring(4,5); 
  		    }
  		   else if(flag = videoname.matches("P[0-9][0-9][0-9][A-Z][AB].avi"))
		   {
  			 ID = videoname.substring(0, 4);
  			 Sesion = videoname.substring(4,5);
  		     Shot = videoname.substring(5,6); 
		    }
  		   else if(flag = videoname.matches("P[0-9][0-9][0-9][0-9][A-Z][AB].avi"))
		   {
  			 ID = videoname.substring(0, 5);
  			 Sesion = videoname.substring(5,6);
  		     Shot = videoname.substring(6,7);  
		    }
  		   else if(flag = videoname.matches("P[0-9][0-9][0-9][0-9][0-9][A-Z][AB].avi"))
		   {
  			 ID = videoname.substring(0, 6);
  			 Sesion = videoname.substring(6,7);
  		     Shot = videoname.substring(7,8);
		    }
  		   else if(flag = videoname.matches("P[0-9][0-9][0-9][0-9][0-9][0-9][A-Z][AB].avi"))
		   {
  			 ID = videoname.substring(0, 7);
  			 Sesion = videoname.substring(7,8);
  		     Shot = videoname.substring(8,9);  
		    }
  		   String savevideo = dbdir + DBType + File.separator + ID + File.separator + Sesion + File.separator + Shot + File.separator + videoname;
  		   String dirvideo = dbdir + DBType + File.separator + ID + File.separator + Sesion + File.separator + Shot + File.separator;
  		   File f = new File(dirvideo);
	       if(f.exists())
	       {	   
  	        try 
  	        {
			  vidir.CopyFile(videofile, savevideo);
			 } 
  	        catch (IOException e) 
  	        {
			  e.printStackTrace();
			 }
  	        vidir.Delete(videoname);
	       }
	       else
  	       {
  	    	 CreateDirectory cr = new CreateDirectory(dirvideo);
  	    	 cr.CreateSubDir();
  	    	 try 
  	         {
			   vidir.CopyFile(videofile, savevideo);
			  } 
  	         catch (IOException e) 
  	         {
			   e.printStackTrace();
			  }
  	         vidir.Delete(videoname);
  	        }	
  		 }	 
  	   }
  	 }
}