package cz.alenkacz.samsung.activity;

import cz.alenkacz.samsung.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Contest extends Activity {
	
	Button btn_end_attemp = null;
	EditText et_contest_content = null;
	TextView tv_counter;
	
	private Handler _handler = new Handler();
	long _startTime = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest);
        
        btn_end_attemp = (Button) findViewById(R.id.btn_end_attemp);
        et_contest_content = (EditText) findViewById(R.id.et_contest_content);
        tv_counter = (TextView) findViewById(R.id.tv_counter);
        
        _startTime = System.currentTimeMillis();
        _handler.removeCallbacks(_updateTimeTask);
        _handler.postDelayed(_updateTimeTask, 100);
        
        btn_end_attemp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	_handler.removeCallbacks(_updateTimeTask);
            	
            	Intent myIntent = new Intent(Contest.this, SendOrReset.class);
            	myIntent.putExtra("content", et_contest_content.getText().toString());
            	myIntent.putExtra("time", getElapsedTimeAsString());
            	startActivity(myIntent);
            }
        });
	}
	
	private String getElapsedTimeAsString() {
		final long start = _startTime;
		long millis = System.currentTimeMillis() - start;
		int seconds = (int) (millis / 1000);
		int minutes = seconds / 60;
		seconds     = seconds % 60;
		
		if (seconds < 10) {
	    	  return "" + minutes + ":0" + seconds;
	       } else {
	    	  return "" + minutes + ":" + seconds;            
	       }
	}
	
	private Runnable _updateTimeTask = new Runnable() {
 	   public void run() {
 	       
    	  tv_counter.setText(getElapsedTimeAsString());
 	     
 	      _handler.postDelayed(this, 100);
 	   }
 	};
}
