package edu.lsivc.index;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.File;
//import java.io.FileFilter;
import java.io.IOException;
//import java.io.FileReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Indexer 
{
  /**
 * @uml.property  name="writer"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
private IndexWriter writer;
  /**
 * @uml.property  name="path"
 */
private String path;

  public Indexer(String indexDir) throws IOException 
  {
    path = indexDir;
    File pathfile = new File(path);
    Directory dir = FSDirectory.open(pathfile);
    if(pathfile.exists())
    {
     writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29), false, IndexWriter.MaxFieldLength.UNLIMITED); 	
    }	
    else
    {	
     writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29), true, IndexWriter.MaxFieldLength.UNLIMITED);
    }
    //writer.setInfoStream(System.out);
   }

  public void close() throws IOException 
  {
    writer.close();                             
   }
  
  public void indexFile(String fd, String data) throws Exception 
  {
    Document doc = new Document();
    Field test = new Field(fd, data, Field.Store.YES, Field.Index.ANALYZED);
    //dftest.setBoost(1.5F);
    doc.add(test);
    //doc.add(new Field(fd, data, Field.Store.YES, Field.Index.NOT_ANALYZED))
    //doc.add(new NumericField("price").setDoubleValue(19.99));
    //doc.setBoost(1.5F);
    writer.addDocument(doc);
    writer.optimize();
    writer.close();                           
   }
  
  public void UpdateIndex(String fd, String data, String filename) throws Exception
  {
	if(!filename.contentEquals(" "))
	{	
	 Document doc = new Document();
	 Field test = new Field(fd, data, Field.Store.YES, Field.Index.ANALYZED); 
	 doc.add(test);
	 writer.updateDocument(new Term("File_Name",filename), doc);
	 writer.commit();
	 writer.close();
	}
  }
  
  public void UpdateIndex(String[][] fd, String[][] data, String filename) throws Exception
  {
	Document doc = new Document();
	Field test = null;
	String keyword = "";
	if(!filename.contentEquals(" "))
	{	
	 for(int i=0;i<fd.length;i++)
     {	 
	   for(int j=0;j<fd[0].length;j++)
	   {
		   if(fd[i][j].contentEquals("URL"))
			{
			 test = new Field(fd[i][j], data[i][j], Field.Store.YES, Field.Index.NOT_ANALYZED);
			 //System.out.println("URL");
			 doc.add(test); 	
			}	
			else if(fd[i][j].contentEquals("File_Name"))
			{
			 test = new Field(fd[i][j], data[i][j], Field.Store.YES, Field.Index.ANALYZED);
			 //System.out.println("File Name");
			 doc.add(test);
			}
			else
			{	
			 test = new Field(fd[i][j], data[i][j], Field.Store.YES, Field.Index.ANALYZED);
			 doc.add(test);
			}
		    keyword += data[i][j] + " ";	
	     }
	  }
	 
	 test = new Field("Keyword", keyword, Field.Store.YES, Field.Index.ANALYZED);
	 doc.add(test);
	 
	 writer.deleteDocuments(new Term("File_Name",filename));
	 writer.expungeDeletes();
	 writer.commit();
	 //System.out.println("Deleted: " + writer.numDocs() + " docs");
	 writer.addDocument(doc);
	 writer.commit();
	 writer.close();
	}
   } 
  
  public void indexFile(String[][] fd, String[][] data) throws Exception 
  {
    Document doc = new Document();
    Field test = null;
    String keyword = "";
    
    for(int i=0;i<fd.length;i++)
	{	 
	  for(int j=0;j<fd[0].length;j++)
      {
		if(fd[i][j].contentEquals("URL"))
		{
		 test = new Field(fd[i][j], data[i][j], Field.Store.YES, Field.Index.NOT_ANALYZED);
		 doc.add(test); 	
		}	
		else if(fd[i][j].contentEquals("File_Name"))
		{
		 test = new Field(fd[i][j], data[i][j], Field.Store.YES, Field.Index.ANALYZED);
		 doc.add(test);
		}
		else
		{	
		 test = new Field(fd[i][j], data[i][j], Field.Store.YES, Field.Index.ANALYZED);
		 doc.add(test);
		}
	   keyword += data[i][j] + " ";	
      }
	}
    
    test = new Field("Keyword", keyword, Field.Store.YES, Field.Index.ANALYZED);
	doc.add(test);
    //System.out.println(keyword);
    //dftest.setBoost(1.5F);
    //doc.add(test);
    //doc.add(new Field(fd, data, Field.Store.YES, Field.Index.NOT_ANALYZED))
    //doc.add(new NumericField("price").setDoubleValue(19.99));
    //doc.setBoost(1.5F);
    writer.addDocument(doc);
    writer.optimize();
    writer.close();
   }
  
  public void indexFile(Connection conn, String table) throws Exception 
  {
    PreparedStatement pstmt = conn.prepareStatement("Select * from " + table);
    ResultSet rs = pstmt.executeQuery();
    //Alternative
    //String sql = "select * from " + table;  
    //Statement pstmt = conn.createStatement();  
    //ResultSet rs = pstmt.executeQuery(sql);
    
    if(rs != null)
    {
      while(rs.next())
      {
        Document doc = new Document();
        //System.out.println(rs.getString("path"));
        doc.add(new Field("path", rs.getString("path"), Field.Store.YES, Field.Index.ANALYZED));
        writer.addDocument(doc);     
        } 
	  }
    writer.close();
   }
  
  public void DeleteIndex(String[][] fd, String[][] data, String filename) throws Exception
  {
	  writer.deleteDocuments(new Term("File_Name",filename));
	  writer.expungeDeletes();
	  writer.commit();
	  writer.close();
   }
 }
