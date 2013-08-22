package edu.lsivc.directory;

import java.io.File;

public class FileWalker 
{ 
    /**
	 * @uml.property  name="dirs"
	 */
    private String Dirs = " ";
    /**
	 * @uml.property  name="files"
	 */
    private String Files = " ";
    
	public FileWalker()
    {
	  	
	 }
    public String WalkDir( String path ) 
    { 

        File root = new File( path ); 
        File[] list = root.listFiles(); 

        for ( File f : list )
        { 
            if ( f.isDirectory() )
            { 
                WalkDir( f.getAbsolutePath() ); 
                Dirs += f.getAbsoluteFile() + " "; 
            } 
         }
        return Dirs;
     }
    
    public String WalkFile(String path)
    {
    	File root = new File( path ); 
        File[] list = root.listFiles(); 

        for ( File f : list )
        { 
    	 if(f.isFile()) 
    	 { 
            Files += f.getAbsoluteFile() + " "; 
          } 
        } 
      	return Files;
     }
} 
