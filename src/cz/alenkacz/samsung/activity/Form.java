package cz.alenkacz.samsung.activity;

import cz.alenkacz.samsung.R;
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
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        
        btn_send_form = (Button) findViewById(R.id.btn_send_form);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_tel = (EditText) findViewById(R.id.et_tel);
        
        setupOnclickListeners();
	}

	private void setupOnclickListeners() {
		btn_send_form.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	boolean res = false;
            	
            	redirectToThanks(res);
            }
            
            private void redirectToThanks( boolean success ) {
        		Intent myIntent = new Intent(Form.this, Thanks.class);
            	myIntent.putExtra("success", success);
            	startActivity(myIntent);
        	}
        });
	}
}
