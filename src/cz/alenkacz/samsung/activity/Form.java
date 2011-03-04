package cz.alenkacz.samsung.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.alenkacz.samsung.R;
import cz.alenkacz.samsung.component.HttpSender;
import cz.alenkacz.samsung.component.XMLSerializer;
import cz.alenkacz.samsung.dao.EntryDatabase;
import cz.alenkacz.samsung.exception.EmailFormatException;
import cz.alenkacz.samsung.exception.FormNotFilledException;
import cz.alenkacz.samsung.model.Attemp;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Form extends Activity {
	
	private final String EMAIL_ERROR = "Formát zadaného emailu není správný";
	private final String FORM_NOT_FILLED_ERROR = "Nevyplnili jste všechny položky formuláře";
	
	Button btn_send_form = null;
	EditText et_name = null;
	EditText et_email = null;
	EditText et_tel = null;
	TextView tv_form_error = null;
	String _text = null;
	
	EntryDatabase _db;
	long _id;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        
        btn_send_form = (Button) findViewById(R.id.btn_send_form);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_tel = (EditText) findViewById(R.id.et_tel);
        tv_form_error = (TextView) findViewById(R.id.tv_form_error);
        
        _text = getIntent().getStringExtra("content");
        
        setupOnclickListeners();
	}

	private void setupOnclickListeners() {
		btn_send_form.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	boolean res = false;
            	
            	XMLSerializer serializer = new XMLSerializer(getApplicationContext());
            	try {
            		String xml = serializer.serialize(getInsertedData());
            		HttpSender sender = new HttpSender();
                	boolean sent = sender.sendAttemp(xml);
                	
                	saveToDb();
                	saveXmlFile();
                	
                	redirectToThanks(res);
            	} catch( EmailFormatException e ) {
            		tv_form_error.setText(EMAIL_ERROR);
            	} catch( FormNotFilledException e ) {
            		tv_form_error.setText(FORM_NOT_FILLED_ERROR);
            	}
            }

			private void redirectToThanks( boolean success ) {
        		Intent myIntent = new Intent(Form.this, Thanks.class);
            	myIntent.putExtra("success", success);
            	startActivity(myIntent);
        	}
        });
	}
	
	private void saveToDb() throws EmailFormatException, FormNotFilledException {
		_db = new EntryDatabase(this);
		_db.open();
		_id = _db.inserEntry(getInsertedData());
		_db.close();
	}
	
	private void saveXmlFile() {
		_db = new EntryDatabase(this);
		_db.open();
		Cursor cur = _db.getAllEntries();
		String id,name,email,datetime,text = "";
		
		cur.moveToFirst();
        while (cur.isAfterLast() == false) {
        	
       	    cur.moveToNext();
        }
        cur.close();
		
		_db.close();
	}
	
	private Attemp getInsertedData() throws EmailFormatException, FormNotFilledException {
		
		String name = et_name.getText().toString();
		String email = et_email.getText().toString();
		String tel = et_tel.getText().toString();
		
		if( !isEverythingFilled(name,email,tel) ) {
			throw new FormNotFilledException();
		}
			
		if( !validateEmail(et_email.getText().toString()) ) {
			throw new EmailFormatException();
		} 
		
		return new Attemp(name, email,tel, getDateAsString(), _text);
	}
	
	private boolean isEverythingFilled(String name, String email, String tel) {
		return isStringNotEmpty(name) && isStringNotEmpty(email) && isStringNotEmpty(tel);
	}
	
	private boolean isStringNotEmpty(String s) {
		return (s != null && !s.equals(""));
	}

	private String getDateAsString() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return formatter.format(today);
    }
	
	private boolean validateEmail(String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
