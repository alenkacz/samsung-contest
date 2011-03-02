package cz.alenkacz.samsung.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import cz.alenkacz.samsung.R;

public class Main extends Activity {
    
	Button btn_start;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btn_start = (Button) findViewById(R.id.btn_start);
        
        btn_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	Intent myIntent = new Intent(Main.this, Contest.class);
            	startActivity(myIntent);
            }
        });
    }
}