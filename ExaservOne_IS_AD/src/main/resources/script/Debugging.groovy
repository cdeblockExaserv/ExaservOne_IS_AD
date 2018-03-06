import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.transform.Transformer; // TO FORMAT XML IN DEBUGGER
import javax.xml.transform.TransformerFactory; // TO FORMAT XML IN DEBUGGER
import javax.xml.transform.stream.StreamResult; // TO FORMAT XML IN DEBUGGER
import javax.xml.transform.Source; // TO FORMAT XML IN DEBUGGER
import java.io.StringWriter; // TO FORMAT XML IN DEBUGGER
import javax.xml.transform.stream.StreamSource; // TO FORMAT XML IN DEBUGGER
import javax.xml.transform.OutputKeys; // TO FORMAT XML IN DEBUGGER

def Message log01(Message message) {processData("log01_QueryCompoundEmployeeResponse", message,"txt",true);}
def Message log02(Message message) {processData("log02_First50_Warnings", message,"txt",true);}
def Message log02bis(Message message) {processData("log02bis_Data_Warnings", message,"txt",true);}
def Message log03(Message message) {processData("log03_CompoundEmployees_NoFutureHires", message,"txt",true);}
def Message log04(Message message) {processData("log04_CompoundEmployees_After_Mapping", message,"XML",true);}
def Message log05(Message message) {processData("log05_CompoundEmployees_FutureHires", message,"XML",true);}
def Message log06(Message message) {processData("log06_CompoundEmployees_FutureHires_After_Mapping", message,"txt",true);}
def Message log07(Message message) {processData("log07_CompoundEmployees_Combined", message,"txt",true);}
def Message log08(Message message) {processData("log08_IS_Before_Mapping", message,"txt",true);}
def Message log09(Message message) {processData("log09_IS_After_Mapping", message, "txt",true);}
def Message log10(Message message) {processData("log10_XML_Outputhandling", message,"txt",true);}
def Message log11(Message message) {processData("log11_UserUpsertResponse", message,"txt",true);}
def Message log12(Message message) {processData("log12_XML_before_Warning_Mapping", message,"txt",true);}
def Message log13(Message message) {processData("log13_XML_After_Filter", message,"txt",true);}

def Message processData(String prefix, Message message, String contentType, boolean enable) {
	def headers = message.getHeaders();
	def body = message.getBody(java.lang.String) as String;
	def messageLog = messageLogFactory.getMessageLog(message);
	def propertyMap = message.getProperties();
	String logger = propertyMap.get("Debugging");
	if(logger.equals("YES")){
		for (header in headers) {
		   messageLog.setStringProperty("header." + header.getKey().toString(), header.getValue().toString())
		}
		for (property in properties) {
		   messageLog.setStringProperty("property." + property.getKey().toString(), property.getValue().toString())
		}
    	if(messageLog != null && enable){
    		if(contentType == "XML"){
	    		//TO FORMAT XML
	    		if(body){
		    		Source xmlInput = new StreamSource(new StringReader(body));
					Transformer transformer = TransformerFactory.newInstance().newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
					//initialize StreamResult with File object to save to file
					StreamResult result = new StreamResult(new StringWriter());
					transformer.transform(xmlInput, result);
					String xmlString = result.getWriter().toString();
				
		        	messageLog.addAttachmentAsString(prefix, xmlString, "text/plain");
				}
				else{
					messageLog.addAttachmentAsString(prefix, "Empty Body", "text/plain");
				}
        	}
        	else{
        		messageLog.addAttachmentAsString(prefix, body, "text/plain");
        	}
     	}
    }
    return message;
}
