package cz.alenkacz.samsung.activity;

import cz.alenkacz.samsung.R;
import android.app.Activity;
import android.os.Bundle;

public class Thanks extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        
        boolean success = getIntent().getBooleanExtra("success", false);
	}
}
