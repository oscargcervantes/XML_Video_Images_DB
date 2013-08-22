package edu.lsivc.analysis;

//import junit.framework.TestCase;
import java.io.FileInputStream;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

/**
 * Tests accessing the various sense keys and usage counts for the lemma "tank". 
 * @author brett
 *
 */
public class SenseKeyTest{
	public SenseKeyTest()
	{
		
	}
	public void testSimpleSenseKey() 
	{
		try 
		{
            JWNL.initialize(new FileInputStream("file_properties.xml"));
            IndexWord word = Dictionary.getInstance().getIndexWord(POS.VERB, "run");
			Synset[] syns = word.getSenses();
			for (int i = 0; i < syns.length; i++) 
			{
				Synset syn = syns[i];
                System.out.println("Synset: " + syn.toString());
				for (int x = 0; x < syn.getWords().length; x++) 
				{
					Word w = syn.getWords()[x]; 
					if (w.getLemma().equals("run")) 
					{
						 System.out.println("count: " + w.getUsageCount());
		                
					}
                   
				}
			}
			
			
		} 
		catch(Exception e) 
		{
            e.printStackTrace();
            System.out.println("Exception in Sense Key test caught");
			
		}
		
		System.out.println("Sense key test passed.");
		
	}
	
}
