package edu.lsivc.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.*;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

public class Searcher 
{
  
  /**
 * @uml.property  name="indexDir"
 */
private String indexDir;               
  /**
 * @uml.property  name="qry"
 */
private String qry;
  /**
 * @uml.property  name="field"
 */
private String field;
/**
 * @uml.property  name="docs"
 */
private Vector<Document> docs = new Vector<Document>();
  
  public Searcher(String dir, String fld, String q)
  {
	 indexDir = dir;
	 qry = q;
	 field = fld;
	}
  
  public Vector<Document> search() throws IOException, ParseException 
  {	
	Document doc = null;
	Directory dir = FSDirectory.open(new File(indexDir));
    IndexSearcher searcher = new IndexSearcher(dir,true);   
    QueryParser parser = new QueryParser(Version.LUCENE_29, field, new StandardAnalyzer(Version.LUCENE_29));  
    Query query = parser.parse(qry);
    System.out.println(query.toString());
    long start = System.currentTimeMillis();
    TopDocs hits = searcher.search(query, 100); 
    long end = System.currentTimeMillis();

    System.err.println("Found "+ hits.totalHits +" document(s) (in "+ (end - start) +" milliseconds) that matched query '" + query + "':");                                   

    for(ScoreDoc scoreDoc : hits.scoreDocs) 
    {
      doc = searcher.doc(scoreDoc.doc);
      docs.add(doc);
      System.out.println(doc.get("URL"));  
     }
     
    searcher.close();
    return docs;
  }
}
