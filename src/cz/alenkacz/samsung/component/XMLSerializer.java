package cz.alenkacz.samsung.component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import cz.alenkacz.samsung.model.Attemp;

public class XMLSerializer {
	
	ImeiComponent _imei;
	Context _context;
	
	public XMLSerializer(Context context) {
		_imei = new ImeiComponent(context);
		_context = context;
	}
	
	public String serialize(Attemp a) {
		XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    try {
	        serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag("", "contest");
	        serializer.attribute("", "imei", _imei.get());
	        
	        serializer = serializeSingleAttemp(a,serializer);
	        	        
	        serializer.endTag("", "contest");
	        serializer.endDocument();
	        return writer.toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
	}
	
	public String serialize(List<Attemp> list) {
		XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    try {
	        serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag("", "contest");
	        serializer.attribute("", "imei", _imei.get());
	        
	        for( Attemp a : list ) {
	        	serializer = serializeSingleAttemp(a,serializer);
	        }
	        	        
	        serializer.endTag("", "contest");
	        serializer.endDocument();
	        return writer.toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
	}
	
	private XmlSerializer serializeSingleAttemp(Attemp a, XmlSerializer serializer) {
		
		try {
			serializer.startTag("", "contestant");
			
			if( a.get_id() != null ) {
				serializer.startTag("", "id");
		        serializer.text(a.get_id());
		        serializer.endTag("", "id");
			}
			
	        serializer.startTag("", "name");
	        serializer.text(a.get_name());
	        serializer.endTag("", "name");
	        serializer.startTag("", "email");
	        serializer.text(a.get_email());
	        serializer.endTag("", "email");
	        serializer.startTag("", "tel");
	        serializer.text(a.get_tel());
	        serializer.endTag("", "tel");
	        serializer.startTag("", "datetime");
	        serializer.text(a.get_datetime());
	        serializer.endTag("", "datetime");
	        serializer.startTag("", "length");
	        serializer.text(a.get_length());
	        serializer.endTag("", "length");
	        serializer.startTag("", "text");
	        serializer.text(a.get_text());
	        serializer.endTag("", "text");
	        serializer.endTag("", "contestant");
		} catch( IOException e ) {
			e.printStackTrace();
		}
        
        return serializer;
	}
}
