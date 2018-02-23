/*
 * The integration developer needs to create the method processData 
 * This method takes Message object of package com.sap.gateway.ip.core.customdev.util
 * which includes helper methods useful for the content developer:
 * 
 * The methods available are:
    public java.lang.Object getBody()

    public void setBody(java.lang.Object exchangeBody)

    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()

    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)

    public void setHeader(java.lang.String name, java.lang.Object value)

    public java.util.Map<java.lang.String,java.lang.Object> getProperties()

    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 

	public void setProperty(java.lang.String name, java.lang.Object value)
 * 
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

def Message processData(Message message) {
	
	//Body 
	def body = message.getBody();
	//message.setBody(body + " ");
	
	StringBuffer str = new StringBuffer();

	//properties
	def pMap = message.getProperties();
	def Company_Territory_Code_ext = pMap.get("Company_Territory_Code");
	def Person_Id_External_ext = pMap.get("Person_Id_External");
	def Operating_Unit_ext = pMap.get("Operating_Unit");
	def Location_ext = pMap.get("Location");
	def Company_ext = pMap.get("Company");
	def Include_ContingentWorkers_ext = pMap.get("Include_ContingentWorkers");
	def Simulation_Enddate_ext = pMap.get("Simulation_Enddate");
	def Last_Execution_Date_ext = pMap.get("Last_Execution_Date");
	
	DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	
	
	if(!(Simulation_Enddate_ext.trim().isEmpty()))
	{
		str.append("effective_end_date=to_date('" + dateFormat.format(Simulation_Enddate_ext) + "') ");
	}else
	{
		str.append("effective_end_date=to_date('" + dateFormat.format(date) + "') ");
	}	
	if(!(Simulation_Startdate_ext.trim().isEmpty()))
	{
		str.append("and last_modified_on > to_date('" + dateFormat.format(Simulation_Startdate_ext) + "') ");
	}else
	{
		str.append("and last_modified_on > to_date('" + dateFormat.format(Last_Execution_Date_ext) + "') ");
	}
	if(!(Company_Territory_Code_ext.trim().isEmpty()))
	{
		str.append("and company_territory_code in(" + Company_Territory_Code_ext +") ");
	}
	if(!(Person_Id_External_ext.trim().isEmpty()))
	{
		str.append("and person_id_external in(" + Person_Id_External_ext +")");
	}
	if(!(Operating_Unit_ext.trim().isEmpty()))
	{
		str.append("and business_unit in(" + Operating_Unit_ext +")");
	}
	if(!(Location_ext.trim().isEmpty()))
	{
		str.append("and location in(" + Location_ext +")");
	}
	if(!(Company_ext.trim().isEmpty()))
	{
		str.append("and company in(" + Company_ext +")");
	}
	if((Include_ContingentWorkers_ext.trim().toUpperCase().equals("YES")))
	{
		str.append("and isContingentWorker in('true','false')");
	}
	
	//def val = str.toString().replace(",","','")
	

	message.setProperty("QueryFilter",str.toString());
	//message.setBody(val);
	
	//CUSTOM
	//def messageLog = messageLogFactory.getMessageLog(message)
	//messageLog.addAttachmentAsString("query", str.toString(), "application/text");
	//
	return message;
}

