package cz.alenkacz.samsung.activity;

import cz.alenkacz.samsung.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Contest extends Activity {
	
	Button btn_end_attemp = null;
	EditText et_contest_content = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest);
        
        btn_end_attemp = (Button) findViewById(R.id.btn_end_attemp);
        et_contest_content = (EditText) findViewById(R.id.et_contest_content);
        
        btn_end_attemp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	Intent myIntent = new Intent(Contest.this, SendOrReset.class);
            	myIntent.putExtra("content", et_contest_content.getText().toString());
            	startActivity(myIntent);
            }
        });
	}
}
