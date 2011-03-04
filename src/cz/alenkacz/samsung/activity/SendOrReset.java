package cz.alenkacz.samsung.activity;

import cz.alenkacz.samsung.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SendOrReset extends Activity {
	
	TextView tv_sent_text = null;
	Button btn_try_again = null;
	Button btn_send_attemp = null;
	String _text = null;
	String _time = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_or_reset);
        
        tv_sent_text = (TextView) findViewById(R.id.tv_sent_text);
        tv_sent_text.setText("Vyplnili jste tento text:\n" + getIntent().getStringExtra("content"));
        btn_send_attemp = (Button) findViewById(R.id.btn_send_attemp);
        btn_try_again = (Button) findViewById(R.id.btn_try_again);
        
        _text = getIntent().getStringExtra("content");
        _time = getIntent().getStringExtra("time");
        
        setupOnclickHandlers();
	}

	private void setupOnclickHandlers() {
		btn_try_again.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	Intent myIntent = new Intent(SendOrReset.this, Contest.class);
            	startActivity(myIntent);
            }
        });
		
		btn_send_attemp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	Intent myIntent = new Intent(SendOrReset.this, Form.class);
            	myIntent.putExtra("content", _text);
            	myIntent.putExtra("time", _time);
            	startActivity(myIntent);
            }
        });
	}
}
