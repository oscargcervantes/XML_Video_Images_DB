import edu.lsivc.xml.*;

public class XMLAPITest
{
	public static void main(String[] args)
	{
		XMLFile xmlfile = new XMLFile("/home/oscargcervantes/Desktop/DBR/DBGR/P1/R/LR/45/P1RLR45.xml");
		xmlfile.WriteResultFile(xmlfile.Insert("ID", "1", "Prueba","Hola mundo"));
		xmlfile.WriteResultFile(xmlfile.Delete("ID", "1", "Prueba"));
		xmlfile.WriteResultFile(xmlfile.Update("ID", "1", "ID","1A"));
	}
}