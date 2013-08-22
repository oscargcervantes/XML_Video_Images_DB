package edu.lsivc.directory;

public class Persona extends CreatePersona
{
  /**
 * @uml.property  name="dir"
 */
private String dir;
  
  public Persona(String dir, String settdir, String rootE, int idperson, int nframes, String[] elements, String[] attributes, Object[] DBValues, Object[] AngleValues, Object[] CameraValues, Object[] MotionValues)
  {
	super(dir, settdir, rootE, idperson, nframes, elements, attributes, DBValues, AngleValues, CameraValues, MotionValues);
	this.dir = dir;
   }
 }