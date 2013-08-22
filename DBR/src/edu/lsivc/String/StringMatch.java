package edu.lsivc.String;

public class StringMatch
{
  private String StringCheck;
  
  public StringMatch(String check)
  {
	this.StringCheck = check;  
   }
  
  public boolean MatchDBGR()
  {
	if(StringCheck.matches("[P][0-9][A-Z][RL][RL][RL][0-9][0-9].avi") || StringCheck.matches("[P][0-9][0-9][A-Z][RL][RL][RL][0-9][0-9].avi") || StringCheck.matches("[P][0-9][0-9][0-9][A-Z][RL][RL][RL][0-9][0-9].avi") || StringCheck.matches("[P][0-9][0-9][0-9][0-9][A-Z][RL][RL][RL][0-9][0-9].avi"))
	{
	  return true;	
	 }
	//else if(StringCheck.matches("[P][0-9][AB].avi") || StringCheck.matches("[P][0-9][0-9][AB].avi") || StringCheck.matches("[P][0-9][0-9][0-9][AB].avi"))
	//{
	  //return true;	
	 //}
	else
	{
	  return false;	
	 }	
   }
  
  public boolean MatchDBFR()
  {
	
	if(StringCheck.matches("[P][0-9][A-Z][AB].avi") || StringCheck.matches("[P][0-9][0-9][A-Z][AB].avi") || StringCheck.matches("[P][0-9][0-9][0-9][A-Z][AB].avi") || StringCheck.matches("[P][0-9][0-9][0-9][0-9][A-Z][AB].avi"))
	{
	  return true;	
	 }
	else
	{
	  return false;	
	 }	
   }
 }
