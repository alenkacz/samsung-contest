package cz.alenkacz.samsung.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.alenkacz.samsung.R;
import cz.alenkacz.samsung.component.FileExporter;
import cz.alenkacz.samsung.component.HttpSender;
import cz.alenkacz.samsung.component.XMLSerializer;
import cz.alenkacz.samsung.dao.EntryDatabase;
import cz.alenkacz.samsung.exception.EmailFormatException;
import cz.alenkacz.samsung.exception.FormNotFilledException;
import cz.alenkacz.samsung.model.Attemp;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	String _time = null;
	
	ProgressDialog _dialog;
	
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
        _time = getIntent().getStringExtra("time");
        
        setupOnclickListeners();
	}

	private void setupOnclickListeners() {
		btn_send_form.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	
            	try {
            		_dialog = ProgressDialog.show(Form.this, "", "Odesílám a ukládám data. Prosím čekejte...", true);
	            	SaveThread thread = new SaveThread(getInsertedData(),getApplicationContext(),_dialog);
	            	thread.start();
            	} catch( EmailFormatException e ) {
            		_dialog.dismiss();
            		tv_form_error.setText(EMAIL_ERROR);
            	} catch( FormNotFilledException e ) {
            		_dialog.dismiss();
            		tv_form_error.setText(FORM_NOT_FILLED_ERROR);
            	}
            }
        });
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
		
		return new Attemp(name, email,tel, getDateAsString(), _time, _text);
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

class SaveThread extends Thread {
	
	Attemp a = null;
	Context c = null;
	
	EntryDatabase _db;
	long _id;
	ProgressDialog _dialog;
	
	boolean _sent = false;
	
	public SaveThread( Attemp a, Context c, ProgressDialog dialog ) {
		this.a = a;
		this.c = c;
		_dialog = dialog;
	}
	
	@Override
    public void run() {  
		XMLSerializer serializer = new XMLSerializer(c);
		String xml = serializer.serialize(a);
		HttpSender sender = new HttpSender();
    	_sent = sender.sendAttemp(xml);
    	
    	saveToDb();
    	saveXmlFile();

        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            _dialog.dismiss();
            redirectToThanks(_sent);
        }
    };
    
    private void redirectToThanks( boolean success ) {
		_dialog.dismiss();
		
		Intent myIntent = new Intent(c, Thanks.class);
    	myIntent.putExtra("success", success);
    	myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	c.startActivity(myIntent);
	}
    
    private void saveToDb() {
		_db = new EntryDatabase(c);
		_db.open();
		_id = _db.inserEntry(a);
		_db.close();
	}
	
	private void saveXmlFile() {
		_db = new EntryDatabase(c);
		_db.open();
		Cursor cur = _db.getAllEntries();
		String id,name,email,tel,datetime,length,text = "";
		List<Attemp> attemps = new ArrayList<Attemp>();
		
		cur.moveToFirst();
        while (cur.isAfterLast() == false) {
        	id = cur.getString(EntryDatabase.ID_FIELD_NUM);
        	name = cur.getString(EntryDatabase.NAME_FIELD_NUM);
        	email = cur.getString(EntryDatabase.EMAIL_FIELD_NUM);
        	tel = cur.getString(EntryDatabase.TEL_FIELD_NUM);
        	datetime = cur.getString(EntryDatabase.DATETIME_FIELD_NUM);
        	text = cur.getString(EntryDatabase.TEXT_FIELD_NUM);
        	length = cur.getString(EntryDatabase.LENGTH_FIELD_NUM);
        	
        	attemps.add(new Attemp(name, email, tel, datetime, length, text, id));
        	
       	    cur.moveToNext();
        }
        cur.close();
		
		_db.close();
		
		FileExporter exporter = new FileExporter();
		XMLSerializer serializer = new XMLSerializer(c);
		exporter.saveFile(serializer.serialize(attemps));
	}
}

