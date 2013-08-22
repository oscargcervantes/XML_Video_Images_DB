package edu.lsivc.xml;

//import String;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.swing.*;

public class XMLFile 
{
 /**
 * @uml.property  name="xmldir"
 */
private String xmldir; 	
 
 public XMLFile(String filename) 
 {
    xmldir = filename;
  }
 		
 public Document Create(String rootE, String secondE, String[] childEls, String[] text)
 {
	
   //Create a new xml file
	   	 
   int lengthchild = childEls.length;
   int lengthtext = text.length;
   int i = 0;
	   
   if(lengthchild == lengthtext)
   {   
	 try
	 {
       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	   DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
	   //root elements
	   Document doc = docBuilder.newDocument();
	   Element rootElement = doc.createElement(rootE);
	   doc.appendChild(rootElement);
	
	   //second elements
	   Element second = doc.createElement(secondE);
	   rootElement.appendChild(second);
		
	   //child elements
	   for(i = 0 ; i < lengthchild; i++)
	   {	
		 Element childs = doc.createElement(childEls[i]);
		 childs.appendChild(doc.createTextNode(text[i]));
		 second.appendChild(childs);
		}
	   return doc; 	
	   }
	  catch(ParserConfigurationException pce)
	  {
	    pce.printStackTrace();
	    return null;
	    }
	  }
	 else
	 {
	   JFrame frame = new JFrame("Show Message Dialog");
	   JOptionPane.showMessageDialog(frame,"Elements and Text length are different");
	   return null;
	   }	   
 }
 
 public String[][] GetElements()
 {
	 try
	    { 
	      int i,l=0;
	      String[][] text = null;
	      File fXmlFile = new File(xmldir);
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  Document doc = dBuilder.parse(fXmlFile);
		  doc.getDocumentElement().normalize();
		  
		  String rootE = doc.getDocumentElement().getNodeName();
		  String secondE = doc.getDocumentElement().getFirstChild().getNodeName();
		  NodeList nList = doc.getElementsByTagName(secondE);
		  
		  for (int temp = 0; temp < nList.getLength(); temp++)
		  {
		    Node nNode = nList.item(temp);	    
		    Element eElement = (Element) nNode;
		    NodeList eName = eElement.getChildNodes();//Added
		    l = eName.getLength();//Added
		    text = new String[nList.getLength()][l];
			for(i = 0 ; i < l ; i++)
		    {	  
		      text[temp][i] = eName.item(i).getNodeName();
		     }
		   }	  
		  return text;
	     }
	    catch(Exception e)
	    {
	      JFrame frame = new JFrame("Show Message Dialog");
	 	  JOptionPane.showMessageDialog(frame,e.getMessage());
	      return null; 
	     } 
 }
 
 public String[][] Read(String[] childEls)
 {
    try
    { 
      int i;
      File fXmlFile = new File(xmldir);
	  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	  Document doc = dBuilder.parse(fXmlFile);
	  doc.getDocumentElement().normalize();
	  
	  //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	  String rootE = doc.getDocumentElement().getNodeName();
	  String secondE = doc.getDocumentElement().getFirstChild().getNodeName();
	  NodeList nList = doc.getElementsByTagName(secondE);
	  //System.out.println("Number of "+ secondE + ": " + nList.getLength() + " Number of childs: " + childEls.length);
	  String[][] text = new String[nList.getLength()][childEls.length];
	  
	  for (int temp = 0; temp < nList.getLength(); temp++)
	  {
	    Node nNode = nList.item(temp);	    
	    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	    {
	      //String Name = nNode.getNodeName(); //Added
	      Element eElement = (Element) nNode;
	      //NodeList eName = eElement.getChildNodes();//Added
	      //int l = eName.getLength();//Added
	      //String Name = eName.item(1).getNodeName();
	      for(i = 0 ; i < childEls.length ; i++)
	      {	  
	         text[temp][i] = getTagValue(childEls[i],eElement);
	         //System.out.println(eName); //Addded
	         //System.out.println(text[temp][i]);//Added
	       }
	      }
	    }
	  return text;
     }
    catch(Exception e)
    {
      JFrame frame = new JFrame("Show Message Dialog");
 	  JOptionPane.showMessageDialog(frame,e.getMessage());
      return null; 
     }
  }
 
 public Document Append(String[] AppendchildEls, String[] Appendtext)
 {
    //To use only after a xml file creation.
	//Use only if no insert or delete operations were performed over the original xml file.	 
    try
    {
      int i=0;
      File fXmlFile = new File(xmldir);
	  
      DocumentBuilderFactory drFactory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder drBuilder = drFactory.newDocumentBuilder();
	  
	  DocumentBuilderFactory dwFactory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder dwBuilder = dwFactory.newDocumentBuilder();
	  
	  Document docread = drBuilder.parse(fXmlFile);
	  docread.getDocumentElement().normalize();
	  
	  //System.out.println("Root element :" + docread.getDocumentElement().getNodeName());
	  String rootE = docread.getDocumentElement().getNodeName();
	  String secondE = docread.getDocumentElement().getFirstChild().getNodeName();
	  NodeList nList = docread.getElementsByTagName(secondE); //Get second elements
	  
	  String[] textchildread = new String[AppendchildEls.length * nList.getLength()];	
	  
	  //create root element
	  Document docwrite = dwBuilder.newDocument();
	  Element rootElement = docwrite.createElement(rootE);
	  docwrite.appendChild(rootElement);
	  
	  for (int temp = 0; temp < nList.getLength(); temp++)
	  {
	    Node nNode = nList.item(temp);	//Get every second element in xml file and obtains the child elements   
	    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	    {
	      Element eElement = (Element) nNode; 
	      for(i = 0; i < AppendchildEls.length; i++)
	      {	  
	        textchildread[i + (temp * AppendchildEls.length)] = getTagValue(AppendchildEls[i],eElement);
	        //System.out.println(textchildread[i + (temp * AppendchildEls.length)]);
	       }
	      
	      //create second elements
	      Element secondb = docwrite.createElement(secondE);
	      rootElement.appendChild(secondb);
	      
	      for(i = 0; i < AppendchildEls.length; i++)
	      {	    
	        //child elements	
	  	    Element childs = docwrite.createElement(AppendchildEls[i]);
	  	    childs.appendChild(docwrite.createTextNode(textchildread[i + (temp * AppendchildEls.length)]));
	  	    secondb.appendChild(childs);
	  	    }
	      }
	     }
	     
	     Element seconda = docwrite.createElement(secondE);
         rootElement.appendChild(seconda);
      
         for(i = 0; i < AppendchildEls.length; i++)
         {	    
           //child elements	
  	       Element childs = docwrite.createElement(AppendchildEls[i]);
  	       childs.appendChild(docwrite.createTextNode(Appendtext[i]));
  	       seconda.appendChild(childs);
  	      } 
	     return docwrite;
	    }
    catch(Exception e)
    {
      JFrame frame = new JFrame("Show Message Dialog");
   	  JOptionPane.showMessageDialog(frame,e.getMessage());
      return null; 
     }
  }
 
 public Document Update(String El, String text, String UpdateEl, String Updatetext)
 {
	 try
	    { 
	      String tmp;
	      File fXmlFile = new File(xmldir);
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  Document doc = dBuilder.parse(fXmlFile);
		  doc.getDocumentElement().normalize();
		  
		  //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		  String rootE = doc.getDocumentElement().getNodeName();
		  String secondE = doc.getDocumentElement().getFirstChild().getNodeName();
		  NodeList nList = doc.getElementsByTagName(secondE);
		  
		  for (int temp = 0; temp < nList.getLength(); temp++)
		  {
		    Node nNode = nList.item(temp);	    
		    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
		    {
		      Element eElement = (Element) nNode; 	  
		      tmp = getTagValue(El,eElement);
		      if(tmp.contentEquals(text))
		      {
		        //setTagValue(UpdateEl,eElement,Updatetext);
		    	  setTagValue(UpdateEl,eElement,Updatetext);  
		       }
		      }
		     }
		  return doc;    
	     } 
	    catch(Exception e)
	    {
	      JFrame frame = new JFrame("Show Message Dialog");
	   	  JOptionPane.showMessageDialog(frame,e.getMessage());
	      return null; 
	     } 
  }
 
 public Document Update(String El, String text, String Updatetext)
 {
	 try
	    { 
	      String tmp;
	      File fXmlFile = new File(xmldir);
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  Document doc = dBuilder.parse(fXmlFile);
		  doc.getDocumentElement().normalize();
		  
		  //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		  String rootE = doc.getDocumentElement().getNodeName();
		  String secondE = doc.getDocumentElement().getFirstChild().getNodeName();
		  NodeList nList = doc.getElementsByTagName(secondE);
		  
		  for (int temp = 0; temp < nList.getLength(); temp++)
		  {
		    Node nNode = nList.item(temp);	    
		    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
		    {
		      Element eElement = (Element) nNode; 	  
		      tmp = getTagValue(El,eElement);
		      if(tmp.contentEquals(text))
		      {
		        //setTagValue(UpdateEl,eElement,Updatetext);
		    	  setTagValue(El,eElement,Updatetext);  
		       }
		      }
		     }
		  return doc;    
	     } 
	    catch(Exception e)
	    {
	      JFrame frame = new JFrame("Show Message Dialog");
	   	  JOptionPane.showMessageDialog(frame,e.getMessage());
	      return null; 
	     } 
  }
 
 public Document Insert(String El, String text, String InsertEl, String Inserttext)
 {
	 //This method needs an existing reference element and attribute to insert the new element and attribute
	 try
	    { 
	      String tmp;
	      File fXmlFile = new File(xmldir);
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  Document doc = dBuilder.parse(fXmlFile);
		  doc.getDocumentElement().normalize();
		  
		  //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		  String rootE = doc.getDocumentElement().getNodeName();
		  String secondE = doc.getDocumentElement().getFirstChild().getNodeName();
		  NodeList nList = doc.getElementsByTagName(secondE);
		  
		  for (int temp = 0; temp < nList.getLength(); temp++)
		  {
		    Node nNode = nList.item(temp);	    
		    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
		    {
		      Element eElement = (Element) nNode; 	  
		      tmp = getTagValue(El,eElement);
		      if(tmp.contentEquals(text))
		      {
		       Node sec = doc.getElementsByTagName(secondE).item(temp);
		   	   //append a new node to second
		   	   Element insert = doc.createElement(InsertEl);
		   	   insert.appendChild(doc.createTextNode(Inserttext));
		   	   sec.appendChild(insert);
		       }
		      }
		     }
		  return doc;    
	     } 
	    catch(Exception e)
	    {
	      JFrame frame = new JFrame("Show Message Dialog");
	   	  JOptionPane.showMessageDialog(frame,e.getMessage());
	      return null; 
	     } 
  }

 public Document Delete(String El, String text, String DeleteEl)
 {
	 try
	    { 
	      String tmp;
	      File fXmlFile = new File(xmldir);
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  Document doc = dBuilder.parse(fXmlFile);
		  doc.getDocumentElement().normalize();
		  
		  //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		  String rootE = doc.getDocumentElement().getNodeName();
		  String secondE = doc.getDocumentElement().getFirstChild().getNodeName();
		  NodeList nList = doc.getElementsByTagName(secondE);
		  
		  for (int temp = 0; temp < nList.getLength(); temp++)
		  {
		    Node nNode = nList.item(temp);	    
		    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
		    {
		      Element eElement = (Element) nNode; 	  
		      tmp = getTagValue(El,eElement);
		      if(tmp.contentEquals(text))
		      {
		   	   //delete node to second
		   	   Element delete = (Element)doc.getElementsByTagName(DeleteEl).item(0);
		   	   delete.getParentNode().removeChild(delete);
		       }
		      }
		     }
		  return doc;    
	     } 
	    catch(Exception e)
	    {
	      JFrame frame = new JFrame("Show Message Dialog");
	   	  JOptionPane.showMessageDialog(frame,e.getMessage());
	      return null; 
	     } 
  }
 
 private static String getTagValue(String sTag, Element eElement)
 {
   NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
   Node nValue = (Node) nlList.item(0);  
   return nValue.getNodeValue();    
  }
 
 private static void setTagValue(String sTag, Element eElement, String value)
 {
   NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
   Node nValue = (Node) nlList.item(0);  
   nValue.setNodeValue(value);    
  }
 
 public boolean WriteResultFile(Document doc)
 {
   try
   {
	//write the content into xml file
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
	DOMSource source = new DOMSource(doc);
	StreamResult result =  new StreamResult(new File(xmldir));
	transformer.transform(source, result);
	return true;
   }
   catch(TransformerException tfe)
   {
	JFrame frame = new JFrame("Show Message Dialog");
	JOptionPane.showMessageDialog(frame,tfe.getMessage());
    return false;
    }
  }
 
 public int GetElementIndex(String Element, String[][] Elements)
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
}

