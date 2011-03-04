package cz.alenkacz.samsung.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpSender {
	
	private final String _serverUrl = "http://seznam.cz";

	public boolean sendAttemp(String content) {
		try {
			HttpClient hc = new DefaultHttpClient();
			HttpPost post = new HttpPost(_serverUrl);
			
			StringEntity se = new StringEntity(content,HTTP.UTF_8);
			se.setContentType("text/xml");
			
			post.setHeader("Content-Type","application/xml;charset=UTF-8");
			post.setEntity(se);
			
			HttpResponse rp = hc.execute(post);
			
			if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				return true;
			} else {
				return false;
			}
			
		} catch( IOException e ) {
			e.printStackTrace();
			return false;
		}
	}
	
}
