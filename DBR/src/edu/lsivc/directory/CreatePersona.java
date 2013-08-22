package edu.lsivc.directory;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

import edu.lsivc.db.DBR;
import edu.lsivc.xml.XMLFile;
import org.w3c.dom.Document;

public class CreatePersona
{
 /**
 * @uml.property  name="dir"
 */
private String dir;
 /**
 * @uml.property  name="pdir"
 */
private String pdir;
/**
 * @uml.property  name="d_E"
 */
private String D_E;
/**
 * @uml.property  name="d_F"
 */
private String D_F;
/**
 * @uml.property  name="d_SAE"
 */
private String D_SAE;
/**
 * @uml.property  name="f_G"
 */
private String F_G;
/**
 * @uml.property  name="f_RGB"
 */
private String F_RGB; 
/**
 * @uml.property  name="m_C"
 */
private String M_C;
/**
 * @uml.property  name="m_E"
 */
private String M_E;
/**
 * @uml.property  name="s_B"
 */
private String S_B;
/**
 * @uml.property  name="s_E"
 */
private String S_E;
/**
 * @uml.property  name="s_N"
 */
private String S_N;

/**
 * @uml.property  name="file"
 */
private String file;
 /**
 * @uml.property  name="root"
 */
private String root;
 /**
 * @uml.property  name="node"
 */
private String node;
 /**
 * @uml.property  name="angulo" multiplicity="(0 -1)" dimension="1"
 */
private String[] angulo = {" "};
//private final String[] angulo = {"90"};
 /**
 * @uml.property  name="camara" multiplicity="(0 -1)" dimension="1"
 */
private String[] camara = {" "};
 /**
 * @uml.property  name="sentido" multiplicity="(0 -1)" dimension="1"
 */
private String[] sentido = {" "};
 //private final String tipo[] = {"VIDEO", "FRAME", "FONDO", "SEGM"};
 /**
 * @uml.property  name="tipo" multiplicity="(0 -1)" dimension="1"
 */
private String sesion;
private final String tipo[] = {"VIDEO"};
 /**
 * @uml.property  name="dbtype" multiplicity="(0 -1)" dimension="1"
 */
private String dbtype[] = {" "};
private String facetypes[] = {"A","B"};
 /**
 * @uml.property  name="elms" multiplicity="(0 -1)" dimension="1"
 */
private String[] elms; 
 //private String[] extension = {".pgm",".ppm",".avi"};
 /**
 * @uml.property  name="extension" multiplicity="(0 -1)" dimension="1"
 */
private String[] extension = {".avi"};
 /**
 * @uml.property  name="attr" multiplicity="(0 -1)" dimension="1"
 */
private String[] attr;
/**
 * @uml.property  name="gaitattr" multiplicity="(0 -1)" dimension="1"
 */
private String[] gaitattr;
/**
 * @uml.property  name="gaitelms" multiplicity="(0 -1)" dimension="1"
 */
private String[] gaitelms;
/**
 * @uml.property  name="faceattr" multiplicity="(0 -1)" dimension="1"
 */
private String[] faceattr;
/**
 * @uml.property  name="faceelms" multiplicity="(0 -1)" dimension="1"
 */
private String[] faceelms;
/**
 * @uml.property  name="xmlfile"
 * @uml.associationEnd  
 */
private XMLFile xmlfile = null; 
/**
 * @uml.property  name="id_persona"
 */
private int id_persona;
 /**
 * @uml.property  name="num_frames"
 */
private int num_frames;
/**
 * @uml.property  name="dirs"
 */
private ArrayList<String> dirs = new ArrayList<String>();
/**
 * @uml.property  name="settdir"
 */
private String settdir;
/**
 * @uml.property  name="delay"
 */
private int delay = 100;
private int gait_index, face_index;

 public CreatePersona(String rootpath, String settingsdir, String rootE, int idperson, int nframes, String[] elements, String[] attributes, Object[] DBValues, Object[] AngleValues, Object[] CameraValues, Object[] MotionValues)
 {  //To create Persona
	this.dir = rootpath;
	this.settdir = settingsdir;
  	this.id_persona = idperson;
  	this.num_frames = nframes;
  	this.attr = attributes;
  	this.elms = elements;
  	
  	this.root = rootE;
  	this.node = "";
  	this.dbtype =  Arrays.asList(DBValues).toArray(new String[DBValues.length]);
  	this.angulo = Arrays.asList(AngleValues).toArray(new String[AngleValues.length]);
  	this.camara = Arrays.asList(CameraValues).toArray(new String[CameraValues.length]);
  	this.sentido = Arrays.asList(MotionValues).toArray(new String[MotionValues.length]);
  	//this.sesion = Arrays.asList(SesionValues).toArray(new String[SesionValues.length]);
  	
  	Index_Elements id = new Index_Elements(dir,".Index.index", ".Elements.elms", ".DBInitialized.db", ".AllDirs", settdir);
	String number = id.GetIndex();
	DBR db = new DBR();
	sesion = "A";
	if (id_persona == (Integer.valueOf(number) + 1))
	{
	   id.SetIndex(String.valueOf(id_persona));
	   db.StartSession(id_persona);
	  }
	else
	{
	   id_persona = Integer.valueOf(number) + 1;
	   id.SetIndex(String.valueOf(id_persona));
	   db.StartSession(id_persona);
	 }
  }
 
 public CreatePersona(String rootpath, String settingsdir, String rootE, int idperson, String session, int nframes, String[] elements, String[] attributes, Object[] DBValues, Object[] AngleValues, Object[] CameraValues, Object[] MotionValues)
 {  //To add Persona's session
	this.dir = rootpath;
	this.settdir = settingsdir;
  	this.id_persona = idperson;
  	this.num_frames = nframes;
  	this.attr = attributes;
  	this.elms = elements;
  	
  	this.root = rootE;
  	this.node = "";
  	this.dbtype =  Arrays.asList(DBValues).toArray(new String[DBValues.length]);
  	this.angulo = Arrays.asList(AngleValues).toArray(new String[AngleValues.length]);
  	this.camara = Arrays.asList(CameraValues).toArray(new String[CameraValues.length]);
  	this.sentido = Arrays.asList(MotionValues).toArray(new String[MotionValues.length]);
  	//this.sesion = Arrays.asList(SesionValues).toArray(new String[SesionValues.length]);
  	
  	Index_Elements id = new Index_Elements(dir,".Index.index", ".Elements.elms", ".DBInitialized.db", ".AllDirs", settdir);
	String number = id.GetIndex();
	DBR db = new DBR();
	this.sesion = session;
  }
 
 
 private void GaitDirs()
 {
	 int i,j,k;
	 for(i=0;i<camara.length;i++)
	    {
	   	 for(j=0;j<sentido.length;j++)
	   	 {
	   	   for(k=0;k<angulo.length;k++)
	   	   {
	   	     pdir = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator;
	   	     if(camara[i].contentEquals("L"))
	   	     {
	   	    	D_E = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "D" + File.separator + "E" + File.separator; 
	   	    	D_F = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "D" + File.separator + "F" + File.separator;
	   	        D_SAE = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "D" + File.separator + "SAE" + File.separator;
	   	        F_G = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "F" + File.separator + "G" + File.separator;
	   	        F_RGB = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "F" + File.separator + "RGB" + File.separator; 
	   	        M_C = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "M" + File.separator + "C" + File.separator;
	   	        M_E = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "M" + File.separator + "E" + File.separator;
	   	        S_B = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "S" + File.separator + "B" + File.separator;
	   	        S_E = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "S" + File.separator + "E" + File.separator;
	   	        S_N  = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "S" + File.separator + "N" + File.separator;
	   	        //file = "P" + String.valueOf(id_persona) + camara[i] + sentido[j] + angulo[k] + ".xml"; 
	   	        //xmlfile = new XMLFile(dir + dbtype[0] + File.separator + pdir + file);
	   	        Directory cr = new Directory(dir + dbtype[gait_index] + File.separator + pdir);
	   	        Directory de = new Directory(dir + dbtype[gait_index] + File.separator + D_E);
	   	        Directory df = new Directory(dir + dbtype[gait_index] + File.separator + D_F);
	   	        Directory dsae = new Directory(dir + dbtype[gait_index] + File.separator + D_SAE);
	   	        Directory fg = new Directory(dir + dbtype[gait_index] + File.separator + F_G);
	   	        Directory frgb = new Directory(dir + dbtype[gait_index] + File.separator + F_RGB);
	   	        Directory mc = new Directory(dir + dbtype[gait_index] + File.separator + M_C);
	   	        Directory me = new Directory(dir + dbtype[gait_index] + File.separator + M_E);
	   	        Directory sb = new Directory(dir + dbtype[gait_index] + File.separator + S_B);
	   	        Directory se = new Directory(dir + dbtype[gait_index] + File.separator + S_E);
	   	        Directory sn = new Directory(dir + dbtype[gait_index] + File.separator + S_N);
	   	        if (!cr.ExistDir())
	            {
	      	      cr.CreateSubDir(); 
	   	         }
	   	        if(!de.ExistDir())
		        {de.CreateSubDir();}
	   	        if(!df.ExistDir())
		        {df.CreateSubDir();}
	   	        if(!dsae.ExistDir())
		        {dsae.CreateSubDir();}
	   	        if(!fg.ExistDir())
		        {fg.CreateSubDir();}
		        if(!frgb.ExistDir())
		        {frgb.CreateSubDir();}
		        if(!mc.ExistDir())
		        {mc.CreateSubDir();}
		        if(!me.ExistDir())
		        {me.CreateSubDir();}
		        if(!sb.ExistDir())
		        {sb.CreateSubDir();}
		        if(!se.ExistDir())
		        {se.CreateSubDir();}
		        if(!sn.ExistDir())
		        {sn.CreateSubDir();}
		        
	   	      }
	   	     else if(camara[i].contentEquals("R"))
	   	     {
	   	    	F_G = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "F" + File.separator + "G" + File.separator;
	   	        F_RGB = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "F" + File.separator + "RGB" + File.separator;
	   	        //file = "P" + String.valueOf(id_persona) + camara[i] + sentido[j] + angulo[k] + ".xml"; 
	   	        //xmlfile = new XMLFile(dir + dbtype[0] + File.separator + pdir + file);
	   	        Directory cr = new Directory(dir + dbtype[gait_index] + File.separator + pdir);
	   	        Directory fg = new Directory(dir + dbtype[gait_index] + File.separator + F_G);
		        Directory frgb = new Directory(dir + dbtype[gait_index] + File.separator + F_RGB);
		        if (!cr.ExistDir())
	            {
	      	      cr.CreateSubDir(); 
	   	         }
		        if(!fg.ExistDir())
		        {fg.CreateSubDir();}
		        if(!frgb.ExistDir())
		        {frgb.CreateSubDir();}
	   	       }	 
	   		 }
	   	   }
	   	 }
  }
 
 private void LocalGaitDirs()
 {
	 int i,j,k;
	 for(i=0;i<camara.length;i++)
	    {
	   	 for(j=0;j<sentido.length;j++)
	   	 {
	   	   for(k=0;k<angulo.length;k++)
	   	   {
	   	     pdir = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator;
	   	     if(camara[i].contentEquals("L"))
	   	     {
	   	    	D_E = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "D" + File.separator + "E" + File.separator; 
	   	    	D_F = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "D" + File.separator + "F" + File.separator;
	   	        D_SAE = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "D" + File.separator + "SAE" + File.separator;
	   	        F_G = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "F" + File.separator + "G" + File.separator;
	   	        F_RGB = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "F" + File.separator + "RGB" + File.separator; 
	   	        M_C = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "M" + File.separator + "C" + File.separator;
	   	        M_E = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "M" + File.separator + "E" + File.separator;
	   	        S_B = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "S" + File.separator + "B" + File.separator;
	   	        S_E = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "S" + File.separator + "E" + File.separator;
	   	        S_N  = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "S" + File.separator + "N" + File.separator;
	   	        //file = "P" + String.valueOf(id_persona) + camara[i] + sentido[j] + angulo[k] + ".xml"; 
	   	        //xmlfile = new XMLFile(dir + dbtype[0] + File.separator + pdir + file);
	   	        Directory cr = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + pdir);
	   	        Directory de = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + D_E);
	   	        Directory df = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + D_F);
	   	        Directory dsae = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + D_SAE);
	   	        Directory fg = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + F_G);
	   	        Directory frgb = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + F_RGB);
	   	        Directory mc = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + M_C);
	   	        Directory me = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + M_E);
	   	        Directory sb = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + S_B);
	   	        Directory se = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + S_E);
	   	        Directory sn = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + S_N);
	   	        if (!cr.ExistDir())
	            {
	      	      cr.CreateSubDir(); 
	   	         }
	   	        if(!de.ExistDir())
		        {de.CreateSubDir();}
	   	        if(!df.ExistDir())
		        {df.CreateSubDir();}
	   	        if(!dsae.ExistDir())
		        {dsae.CreateSubDir();}
	   	        if(!fg.ExistDir())
		        {fg.CreateSubDir();}
		        if(!frgb.ExistDir())
		        {frgb.CreateSubDir();}
		        if(!mc.ExistDir())
		        {mc.CreateSubDir();}
		        if(!me.ExistDir())
		        {me.CreateSubDir();}
		        if(!sb.ExistDir())
		        {sb.CreateSubDir();}
		        if(!se.ExistDir())
		        {se.CreateSubDir();}
		        if(!sn.ExistDir())
		        {sn.CreateSubDir();}
		        
	   	      }
	   	     else if(camara[i].contentEquals("R"))
	   	     {
	   	    	F_G = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "F" + File.separator + "G" + File.separator;
	   	        F_RGB = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator + "F" + File.separator + "RGB" + File.separator;
	   	        //file = "P" + String.valueOf(id_persona) + camara[i] + sentido[j] + angulo[k] + ".xml"; 
	   	        //xmlfile = new XMLFile(dir + dbtype[0] + File.separator + pdir + file);
	   	        Directory cr = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + pdir);
	   	        Directory fg = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + F_G);
		        Directory frgb = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + F_RGB);
		        if (!cr.ExistDir())
	            {
	      	      cr.CreateSubDir(); 
	   	         }
		        if(!fg.ExistDir())
		        {fg.CreateSubDir();}
		        if(!frgb.ExistDir())
		        {frgb.CreateSubDir();}
	   	       }	 
	   		 }
	   	   }
	   	 } 
  }
 
 private void LocalGaitXMLS()
 {
	 int i,j,k;
	 Document doc;
	 for(i=0;i<camara.length;i++)
	    {
	   	 for(j=0;j<sentido.length;j++)
	   	 {
	   	   for(k=0;k<angulo.length;k++)
	   	   {
	   	     pdir = "P" + String.valueOf(id_persona) + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator;
	   	     //gaitattr.add(0, String.valueOf(id_persona));
	   		 gaitattr[0] = String.valueOf(id_persona);
	   		 //gaitelms[elms.length] = "ID"; 
	   		 gaitattr[attr.length ] =  dir + dbtype[gait_index] + File.separator + pdir + "P" + String.valueOf(id_persona) + camara[i] + sentido[j] + angulo[k] + extension[0];
	   		 gaitelms[elms.length ] = "URL";
	   		 gaitattr[attr.length + 2] = sentido[j];
	   		 gaitelms[elms.length + 2] = "Motion_Direction";
	   		 gaitattr[attr.length + 1] = camara[i];
	   		 gaitelms[elms.length + 1] = "Camera";
	   		 gaitattr[attr.length + 3] = angulo[k];
	  		 gaitelms[elms.length + 3] = "Motion_Angle";
	  		 gaitattr[attr.length + 4] = "P" + String.valueOf(id_persona) + camara[i] + sentido[j] + angulo[k];
	  		 gaitelms[elms.length + 4] = "File_Name";
	   		 gaitattr[attr.length + 5] = tipo[0];
	   		 gaitelms[elms.length + 5] = "File_Type";
	   		 Calendar currentDate = Calendar.getInstance();
	   	     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	   	     String dateNow = formatter.format(currentDate.getTime());
	   	     StringTokenizer tokens = new StringTokenizer(dateNow);
	   	     gaitattr[attr.length + 7] = tokens.nextToken();
	   	     gaitelms[elms.length + 7] = "Creation_Date";
	   	     gaitattr[attr.length + 8] = tokens.nextToken();
	   	     gaitelms[elms.length + 8] = "Hour";
	   	     gaitattr[attr.length + 6] = "0";
	   	     gaitelms[elms.length + 6] = "File_Size";
	   	     gaitattr[attr.length + 9] = String.valueOf(this.num_frames);
	   	     gaitelms[elms.length + 9] = "Frames_Number";
	   	     gaitattr[attr.length + 10] = extension[0];
	   	     gaitelms[elms.length + 10] = "Extension";
	   	     gaitattr[attr.length + 11] = " ";////////////////////////////
		     gaitelms[elms.length + 11] = "Duration";
		     gaitattr[attr.length + 12] = " ";
	   	     gaitelms[elms.length + 12] = "Start_Time";
	   	     gaitattr[attr.length + 13] = " ";
		     gaitelms[elms.length + 13] = "Bit_Rate";
		     gaitattr[attr.length + 14] = " ";
	   	     gaitelms[elms.length + 14] = "Codec_Type";
	   	     gaitattr[attr.length + 15] = " ";
		     gaitelms[elms.length + 15] = "Codec_ID";
		     gaitattr[attr.length + 16] = " ";
	   	     gaitelms[elms.length + 16] = "Video_Width";
	   	     gaitattr[attr.length + 17] = " ";
		     gaitelms[elms.length + 17] = "Video_Height";
		     gaitattr[attr.length + 18] = " ";
	   	     gaitelms[elms.length + 18] = "Video_Format";
	   	     gaitattr[attr.length + 19] = " ";
		     gaitelms[elms.length + 19] = "Frame_Rate";
		     gaitattr[attr.length + 20] = "92";
		     gaitelms[elms.length + 20] = "Left_Camera_Pan";
		     gaitattr[attr.length + 21] = "95";
		     gaitelms[elms.length + 21] = "Left_Camera_Tilt";
		     gaitattr[attr.length + 22] = "0";
		     gaitelms[elms.length + 22] = "Left_Camera_Brightness";
		     gaitattr[attr.length + 23] = "401";
		     gaitelms[elms.length + 23] = "Left_Camera_Gain";
		     gaitattr[attr.length + 24] = "108";
		     gaitelms[elms.length + 24] = "Left_Camera_R";
		     gaitattr[attr.length + 25] = "162";
		     gaitelms[elms.length + 25] = "Left_Camera_B";
		     gaitattr[attr.length + 26] = "16";
		     gaitelms[elms.length + 26] = "Left_Camera_Hue";
		     gaitattr[attr.length + 27] = "181";
		     gaitelms[elms.length + 27] = "Left_Camera_Shutter";
		     gaitattr[attr.length + 28] = "63";
		     gaitelms[elms.length + 28] = "Right_Camera_Pan";
		     gaitattr[attr.length + 29] = "94";
		     gaitelms[elms.length + 29] = "Right_Camera_Tilt";
		     gaitattr[attr.length + 30] = "20";
		     gaitelms[elms.length + 30] = "Right_Camera_Brightness";
		     gaitattr[attr.length + 31] = "475";
		     gaitelms[elms.length + 31] = "Right_Camera_Gain";
		     gaitattr[attr.length + 32] = "108";
		     gaitelms[elms.length + 32] = "Right_Camera_R";
		     gaitattr[attr.length + 33] = "160";
		     gaitelms[elms.length + 33] = "Right_Camera_B";
		     gaitattr[attr.length + 34] = "16";
		     gaitelms[elms.length + 34] = "Right_Camera_Hue";
		     gaitattr[attr.length + 35] = "81";
		     gaitelms[elms.length + 35] = "Right_Camera_Shutter";
		     gaitattr[attr.length + 36] = "Sony";
		     gaitelms[elms.length + 36] = "Cameras_Model";
		     gaitattr[attr.length + 37] = " ";
		     gaitelms[elms.length + 37] = "Complexion";
	   	     
	   	     File gait = new File(this.settdir + ".GaitElements.elms");
	   	     if(!gait.exists())
	   	     {  
	   	       for(int n = 0; n < gaitelms.length; n++)
	   	       {	 
	   	        FileW GaitElements = new FileW(this.settdir + ".GaitElements.elms", gaitelms[n]);
	            GaitElements.Write();
	   	        }
	   	      }
	   	     file = "P" + String.valueOf(id_persona) + camara[i] + sentido[j] + angulo[k] + ".xml"; 
		     xmlfile = new XMLFile(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[gait_index] + File.separator + pdir + file);
	         node = dbtype[gait_index];    
	   		 doc = xmlfile.Create(root, node, gaitelms, gaitattr);
	   		 if(doc!=null)
	   		 {	 
	   		   xmlfile.WriteResultFile(doc);
	   		  } 
	        }
	       }
	     } 
  }
 
 private void GaitXMLS()
 {
	 int i,j,k;
	 Document doc;
	 for(i=0;i<camara.length;i++)
	    {
	   	 for(j=0;j<sentido.length;j++)
	   	 {
	   	   for(k=0;k<angulo.length;k++)
	   	   {
	   	     pdir = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator + camara[i] + File.separator + sentido[j] + File.separator + angulo[k] + File.separator;
	   	     //gaitattr.add(0, String.valueOf(id_persona));
	   		 gaitattr[0] = String.valueOf(id_persona);
	   		 //gaitelms[elms.length] = "ID"; 
	   		 gaitattr[attr.length ] =  dir + dbtype[gait_index] + File.separator + pdir + "P" + String.valueOf(id_persona) + sesion + camara[i] + sentido[j] + angulo[k] + extension[0];
	   		 gaitelms[elms.length ] = "URL";
	   		 gaitattr[attr.length + 2] = sentido[j];
	   		 gaitelms[elms.length + 2] = "Motion_Direction";
	   		 gaitattr[attr.length + 1] = camara[i];
	   		 gaitelms[elms.length + 1] = "Camera";
	   		 gaitattr[attr.length + 3] = angulo[k];
	  		 gaitelms[elms.length + 3] = "Motion_Angle";
	  		 gaitattr[attr.length + 4] = "P" + String.valueOf(id_persona) + sesion + camara[i] + sentido[j] + angulo[k];
	  		 gaitelms[elms.length + 4] = "File_Name";
	   		 gaitattr[attr.length + 5] = tipo[0];
	   		 gaitelms[elms.length + 5] = "File_Type";
	   		 Calendar currentDate = Calendar.getInstance();
	   	     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	   	     String dateNow = formatter.format(currentDate.getTime());
	   	     StringTokenizer tokens = new StringTokenizer(dateNow);
	   	     gaitattr[attr.length + 7] = tokens.nextToken();
	   	     gaitelms[elms.length + 7] = "Creation_Date";
	   	     gaitattr[attr.length + 8] = tokens.nextToken();
	   	     gaitelms[elms.length + 8] = "Hour";
	   	     gaitattr[attr.length + 6] = "0";
	   	     gaitelms[elms.length + 6] = "File_Size";
	   	     gaitattr[attr.length + 9] = String.valueOf(this.num_frames);
	   	     gaitelms[elms.length + 9] = "Frames_Number";
	   	     gaitattr[attr.length + 10] = extension[0];
	   	     gaitelms[elms.length + 10] = "Extension";
	   	     gaitattr[attr.length + 11] = "?";////////////////////////////
		     gaitelms[elms.length + 11] = "Duration";
		     gaitattr[attr.length + 12] = "?";
	   	     gaitelms[elms.length + 12] = "Start_Time";
	   	     gaitattr[attr.length + 13] = "?";
		     gaitelms[elms.length + 13] = "Bit_Rate";
		     gaitattr[attr.length + 14] = "?";
	   	     gaitelms[elms.length + 14] = "Codec_Type";
	   	     gaitattr[attr.length + 15] = "?";
		     gaitelms[elms.length + 15] = "Codec_ID";
		     gaitattr[attr.length + 16] = "?";
	   	     gaitelms[elms.length + 16] = "Video_Width";
	   	     gaitattr[attr.length + 17] = "?";
		     gaitelms[elms.length + 17] = "Video_Height";
		     gaitattr[attr.length + 18] = "?";
	   	     gaitelms[elms.length + 18] = "Video_Format";
	   	     gaitattr[attr.length + 19] = "?";
		     gaitelms[elms.length + 19] = "Frame_Rate";
		     gaitattr[attr.length + 20] = "92";
		     gaitelms[elms.length + 20] = "Left_Camera_Pan";
		     gaitattr[attr.length + 21] = "95";
		     gaitelms[elms.length + 21] = "Left_Camera_Tilt";
		     gaitattr[attr.length + 22] = "0";
		     gaitelms[elms.length + 22] = "Left_Camera_Brightness";
		     gaitattr[attr.length + 23] = "401";
		     gaitelms[elms.length + 23] = "Left_Camera_Gain";
		     gaitattr[attr.length + 24] = "108";
		     gaitelms[elms.length + 24] = "Left_Camera_R";
		     gaitattr[attr.length + 25] = "162";
		     gaitelms[elms.length + 25] = "Left_Camera_B";
		     gaitattr[attr.length + 26] = "16";
		     gaitelms[elms.length + 26] = "Left_Camera_Hue";
		     gaitattr[attr.length + 27] = "181";
		     gaitelms[elms.length + 27] = "Left_Camera_Shutter";
		     gaitattr[attr.length + 28] = "63";
		     gaitelms[elms.length + 28] = "Right_Camera_Pan";
		     gaitattr[attr.length + 29] = "94";
		     gaitelms[elms.length + 29] = "Right_Camera_Tilt";
		     gaitattr[attr.length + 30] = "20";
		     gaitelms[elms.length + 30] = "Right_Camera_Brightness";
		     gaitattr[attr.length + 31] = "475";
		     gaitelms[elms.length + 31] = "Right_Camera_Gain";
		     gaitattr[attr.length + 32] = "108";
		     gaitelms[elms.length + 32] = "Right_Camera_R";
		     gaitattr[attr.length + 33] = "160";
		     gaitelms[elms.length + 33] = "Right_Camera_B";
		     gaitattr[attr.length + 34] = "16";
		     gaitelms[elms.length + 34] = "Right_Camera_Hue";
		     gaitattr[attr.length + 35] = "81";
		     gaitelms[elms.length + 35] = "Right_Camera_Shutter";
		     gaitattr[attr.length + 36] = "Sony";
		     gaitelms[elms.length + 36] = "Cameras_Model";
		     gaitattr[attr.length + 37] = "?";
		     gaitelms[elms.length + 37] = "Complexion";
		     gaitattr[attr.length + 38] = sesion;
	  		 gaitelms[elms.length + 38] = "Session";
	   	     
	   	     File gait = new File(this.settdir + ".GaitElements.elms");
	   	     if(!gait.exists())
	   	     {  
	   	       for(int n = 0; n < gaitelms.length; n++)
	   	       {	 
	   	        FileW GaitElements = new FileW(this.settdir + ".GaitElements.elms", gaitelms[n]);
	            GaitElements.Write();
	   	        }
	   	      }
	   	     file = "P" + String.valueOf(id_persona) + sesion + camara[i] + sentido[j] + angulo[k] + ".xml";
	   	     //File xml = new File(dir + dbtype[gait_index] + File.separator + pdir + file);
	   	     //if(!xml.exists())
	   	     //{	 
		       xmlfile = new XMLFile(dir + dbtype[gait_index] + File.separator + pdir + file);
	           node = dbtype[gait_index];    
	   		   doc = xmlfile.Create(root, node, gaitelms, gaitattr);
	   		   if(doc!=null)
	   		   {	 
	   		     xmlfile.WriteResultFile(doc);
	   		    }
	   	      //}
	        }
	       }
	     } 
  }
 
 private void CreateGaitDirs()
 {
    gaitattr = java.util.Arrays.copyOf(attr, attr.length + 39);
    gaitelms = java.util.Arrays.copyOf(elms, elms.length + 39);
    //ArrayList<String> gaitattr = java.util.Arrays.copyOf(attr, attr.length + 38);
    //ArrayList<String> gaitelms = new ArrayList<String>();
    this.GaitDirs();
    //this.LocalGaitDirs();
    dirs.add(this.dir + dbtype[gait_index] + File.separator + "P" + String.valueOf(id_persona));
    FolderPermissions p = new FolderPermissions(dir + dbtype[gait_index] + File.separator + "P" + String.valueOf(id_persona) + File.separator,"777");
    p.Apply();
    this.GaitXMLS();
  }
 
 private void FaceDirs()
 {
	 int i;
	 for(i=0;i<facetypes.length;i++)
	 {	 
	   pdir = "P" + String.valueOf(id_persona) + File.separator + sesion + File.separator;
	   //System.out.println(dir + "/" + pdir + "/" + file);
	   Directory cr = new Directory(dir + dbtype[face_index] + File.separator + pdir + File.separator + facetypes[i]);	
	   if (cr.ExistDir())
	   {
	     }
	   else
	   {
	    cr.CreateSubDir(); 
	    }
	  }
  }
 
 private void LocalFaceDirs()
 {
	   pdir = "P" + String.valueOf(id_persona) + File.separator;
	   //System.out.println(dir + "/" + pdir + "/" + file);
	   Directory cr = new Directory(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[face_index] + File.separator + pdir);	
	   if (cr.ExistDir())
	   {
	     }
	   else
	   {
	    cr.CreateSubDir(); 
	    }  
  }
 
 private void LocalFaceXMLS()
 {
	   Document doc;
	   faceattr[0] = String.valueOf(id_persona);
	   //faceelms[elms.length] = "ID";
	   faceattr[attr.length] =  dir + dbtype[face_index] + File.separator + pdir + "P" + String.valueOf(id_persona) + extension[0];
	   faceelms[elms.length] = "URL";
	   faceattr[attr.length + 1] = "P" + String.valueOf(id_persona);
	   faceelms[elms.length + 1] = "File_Name";
	   faceattr[attr.length + 2] = tipo[0];
	   faceelms[elms.length + 2] = "File_Type";
	   Calendar currentDate = Calendar.getInstance();
	   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	   String dateNow = formatter.format(currentDate.getTime());
	   StringTokenizer tokens = new StringTokenizer(dateNow);
	   faceattr[attr.length + 4] = tokens.nextToken();
	   faceelms[elms.length + 4] = "Creation_Date";
	   faceattr[attr.length + 5] = tokens.nextToken();
	   faceelms[elms.length + 5] = "Hour";
	   faceattr[attr.length + 3] = "0";
	   faceelms[elms.length + 3] = "File_Size";
	   faceattr[attr.length + 6] = String.valueOf(this.num_frames);
	   faceelms[elms.length + 6] = "Frames_Number";
	   faceattr[attr.length + 7] = extension[0];
	   faceelms[elms.length + 7] = "Extension";
	   faceattr[attr.length + 8] = " ";////////////////////////////
	   faceelms[elms.length + 8] = "Duration";
	   faceattr[attr.length + 9] = " ";
	   faceelms[elms.length + 9] = "Start_Time";
	   faceattr[attr.length + 10] = " ";
	   faceelms[elms.length + 10] = "Bit_Rate";
	   faceattr[attr.length + 11] = " ";
	   faceelms[elms.length + 11] = "Codec_Type";
	   faceattr[attr.length + 12] = " ";
	   faceelms[elms.length + 12] = "Codec_ID";
	   faceattr[attr.length + 13] = " ";
	   faceelms[elms.length + 13] = "Video_Width";
	   faceattr[attr.length + 14] = " ";
	   faceelms[elms.length + 14] = "Video_Height";
	   faceattr[attr.length + 15] = " ";
	   faceelms[elms.length + 15] = "Video_Format";
	   faceattr[attr.length + 16] = " ";
	   faceelms[elms.length + 16] = "Frame_Rate";
	   faceattr[attr.length + 17] = " ";
	   faceelms[elms.length + 17] = "Camera_Pan";
	   faceattr[attr.length + 18] = " ";
	   faceelms[elms.length + 18] = "Camera_Tilt";
	   faceattr[attr.length + 19] = " ";
	   faceelms[elms.length + 19] = "Camera_Brightness";
	   faceattr[attr.length + 20] = " ";
	   faceelms[elms.length + 20] = "Camera_Gain";
	   faceattr[attr.length + 21] = " ";
	   faceelms[elms.length + 21] = "Camera_R";
	   faceattr[attr.length + 22] = " ";
	   faceelms[elms.length + 22] = "Camera_B";
	   faceattr[attr.length + 23] = " ";
	   faceelms[elms.length + 23] = "Camera_Hue";
	   faceattr[attr.length + 24] = " ";
	   faceelms[elms.length + 24] = "Camera_Shutter";
	   faceattr[attr.length + 25] = "Sony";
	   faceelms[elms.length + 25] = "Cameras_Model";
	   faceattr[attr.length + 26] = " ";
	   faceelms[elms.length + 26] = "Complexion";
	   
	   File face = new File(this.settdir + ".FaceElements.elms");
	   if(!face.exists())
	   {	   
	    for(int n = 0; n < faceelms.length; n++)
	    {	 
		  FileW FaceElements = new FileW(this.settdir + ".FaceElements.elms", faceelms[n]);
	      FaceElements.Write();
		 }
	    }
	   
	   file = "P" + String.valueOf(id_persona) + ".xml"; 
	   XMLFile xmlfile = new XMLFile(System.getProperty("user.home") + File.separator + "DBR" + File.separator + dbtype[face_index] + File.separator + pdir + file);
	   node = dbtype[face_index];    
	   doc = xmlfile.Create(root, node, faceelms, faceattr);
	   if(doc!=null)
	   {	 
	   	 xmlfile.WriteResultFile(doc);
	    } 
  }
 
 private void FaceXMLS()
 {
	 int i;  
	 Document doc;
	 for(i=0;i<facetypes.length;i++)
	 {	 
	   faceattr[0] = String.valueOf(id_persona);
	   //faceelms[elms.length] = "ID";
	   faceattr[attr.length] =  dir + dbtype[face_index] + File.separator + pdir + facetypes[i] + File.separator + "P" + String.valueOf(id_persona) + sesion + facetypes[i] + extension[0];
	   faceelms[elms.length] = "URL";
	   faceattr[attr.length + 1] = "P" + String.valueOf(id_persona) + sesion + facetypes[i];
	   faceelms[elms.length + 1] = "File_Name";
	   faceattr[attr.length + 2] = facetypes[i];
	   faceelms[elms.length + 2] = "Shot";
	   faceattr[attr.length + 3] = tipo[0];
	   faceelms[elms.length + 3] = "File_Type";
	   Calendar currentDate = Calendar.getInstance();
	   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	   String dateNow = formatter.format(currentDate.getTime());
	   StringTokenizer tokens = new StringTokenizer(dateNow);
	   faceattr[attr.length + 5] = tokens.nextToken();
	   faceelms[elms.length + 5] = "Creation_Date";
	   faceattr[attr.length + 6] = tokens.nextToken();
	   faceelms[elms.length + 6] = "Hour";
	   faceattr[attr.length + 4] = "0";
	   faceelms[elms.length + 4] = "File_Size";
	   faceattr[attr.length + 7] = String.valueOf(this.num_frames);
	   faceelms[elms.length + 7] = "Frames_Number";
	   faceattr[attr.length + 8] = extension[0];
	   faceelms[elms.length + 8] = "Extension";
	   faceattr[attr.length + 9] = "?";////////////////////////////
	   faceelms[elms.length + 9] = "Duration";
	   faceattr[attr.length + 10] = "?";
	   faceelms[elms.length + 10] = "Start_Time";
	   faceattr[attr.length + 11] = "?";
	   faceelms[elms.length + 11] = "Bit_Rate";
	   faceattr[attr.length + 12] = "?";
	   faceelms[elms.length + 12] = "Codec_Type";
	   faceattr[attr.length + 13] = "?";
	   faceelms[elms.length + 13] = "Codec_ID";
	   faceattr[attr.length + 14] = "?";
	   faceelms[elms.length + 14] = "Video_Width";
	   faceattr[attr.length + 15] = "?";
	   faceelms[elms.length + 15] = "Video_Height";
	   faceattr[attr.length + 16] = "?";
	   faceelms[elms.length + 16] = "Video_Format";
	   faceattr[attr.length + 17] = "?";
	   faceelms[elms.length + 17] = "Frame_Rate";
	   faceattr[attr.length + 18] = "?";
	   faceelms[elms.length + 18] = "Camera_Pan";
	   faceattr[attr.length + 19] = "?";
	   faceelms[elms.length + 19] = "Camera_Tilt";
	   faceattr[attr.length + 20] = "?";
	   faceelms[elms.length + 20] = "Camera_Brightness";
	   faceattr[attr.length + 21] = "?";
	   faceelms[elms.length + 21] = "Camera_Gain";
	   faceattr[attr.length + 22] = "?";
	   faceelms[elms.length + 22] = "Camera_R";
	   faceattr[attr.length + 23] = "?";
	   faceelms[elms.length + 23] = "Camera_B";
	   faceattr[attr.length + 24] = "?";
	   faceelms[elms.length + 24] = "Camera_Hue";
	   faceattr[attr.length + 25] = "?";
	   faceelms[elms.length + 25] = "Camera_Shutter";
	   faceattr[attr.length + 26] = "Sony";
	   faceelms[elms.length + 26] = "Cameras_Model";
	   faceattr[attr.length + 27] = "?";
	   faceelms[elms.length + 27] = "Complexion";
	   faceattr[attr.length + 28] = sesion;
	   faceelms[elms.length + 28] = "Session";
	   
	   File face = new File(this.settdir + ".FaceElements.elms");
	   if(!face.exists())
	   {	   
	    for(int n = 0; n < faceelms.length; n++)
	    {	 
		  FileW FaceElements = new FileW(this.settdir + ".FaceElements.elms", faceelms[n]);
	      FaceElements.Write();
		 }
	    }
	   
	   file = "P" + String.valueOf(id_persona) + sesion + facetypes[i] + ".xml";
	   //File xml = new File(dir + dbtype[face_index] + File.separator + pdir + file);
	   //if(!xml.exists())
	   //{	   
	     XMLFile xmlfile = new XMLFile(dir + dbtype[face_index] + File.separator + pdir + File.separator + facetypes[i] + File.separator + file);
	     node = dbtype[face_index];    
	     doc = xmlfile.Create(root, node, faceelms, faceattr);
	     if(doc!=null)
	     {	 
	   	   xmlfile.WriteResultFile(doc);
	      }
	    //}
	  }
  }
 
 private void CreateFaceDirs()
 {  
   faceattr = java.util.Arrays.copyOf(attr, attr.length + 29);
   faceelms = java.util.Arrays.copyOf(elms, elms.length + 29);
   this.FaceDirs();
   //this.LocalFaceDirs();
   dirs.add(this.dir + dbtype[face_index] + File.separator + "P" + String.valueOf(id_persona));
   this.FaceXMLS();
  }
 
 public String Create()
 {
   int i;
   for(i=0;i<dbtype.length;i++)
   {	   
	 if(dbtype[i].contentEquals("DBGR"))
	 {	 
	   gait_index = i;
	   this.CreateGaitDirs();	   
	  }
	 else if(dbtype[i].contentEquals("DBFR"))
	 {
	   face_index = i;
	   this.CreateFaceDirs();
	  }		 
    }
   return dirs.toString();
  }
 }





