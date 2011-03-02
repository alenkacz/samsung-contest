package cz.alenkacz.samsung.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.alenkacz.samsung.R;
import cz.alenkacz.samsung.component.XMLSerializer;
import cz.alenkacz.samsung.model.Attemp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Form extends Activity {
	
	Button btn_send_form = null;
	EditText et_name = null;
	EditText et_email = null;
	EditText et_tel = null;
	String _text = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        
        btn_send_form = (Button) findViewById(R.id.btn_send_form);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_tel = (EditText) findViewById(R.id.et_tel);
        
        _text = getIntent().getStringExtra("content");
        
        setupOnclickListeners();
	}

	private void setupOnclickListeners() {
		btn_send_form.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	boolean res = false;
            	
            	XMLSerializer serializer = new XMLSerializer(getApplicationContext());
            	serializer.serialize(getInsertedData());
            	
            	redirectToThanks(res);
            }
            
            private Attemp getInsertedData() {
				return new Attemp(et_name.getText().toString(), et_email.getText().toString(),
						et_tel.getText().toString(), getDateAsString(), _text);
			}
            
            private String getDateAsString() {
                Date today = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh.mm");
                return formatter.format(today);
            }

			private void redirectToThanks( boolean success ) {
        		Intent myIntent = new Intent(Form.this, Thanks.class);
            	myIntent.putExtra("success", success);
            	startActivity(myIntent);
        	}
        });
	}
}
