/*
 * The integration developer needs to create the method processData 
 * This method takes Message object of package com.sap.gateway.ip.core.customdev.util
 * which includes helper methods useful for the content developer:
 * 
 * The methods available are:
    public java.lang.Object getBody()
    
    //This method helps User to retrieve message body as specific type ( InputStream , String , byte[] ) - e.g. message.getBody(java.io.InputStream)
    public java.lang.Object getBody(java.lang.String fullyQualifiedClassName)

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
import java.lang.StringBuilder;

def Message RemoveLastCharFromBody(Message message) {
	 //Body 
       def body = message.getBody(java.lang.String) as String;
       def body_length = body.length();
       
		StringBuilder sb = new StringBuilder(body);
		sb.deleteCharAt(body_length - 1);

		message.setProperty("new_hires", sb.toString());
		message.setBody(sb.toString());
       	
       	return message;
}

def Message PrepareLastRuntime(Message message) {
        def properties = message.getProperties();
        def execution_timestamp = properties.get("Execution_Timestamp");
        
        StringBuilder sb = new StringBuilder(execution_timestamp);
		sb.deleteCharAt(22);
		sb.deleteCharAt(21);
		sb.deleteCharAt(20);
		sb.deleteCharAt(19);   
		
		message.setProperty("Execution_Timestamp",  sb.toString());
		
		return message;    
}



