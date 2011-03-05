package cz.alenkacz.samsung.activity;

import cz.alenkacz.samsung.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Thanks extends Activity {
	
	Button btn_back_start = null;
	TextView tv_thanks = null;
	
	private final String SUCCESS = "Údaje byly úspěšně odeslány na server a v případě odeslání správného textu budete zařazeni do žebříčku.";
	private final String FAIL = "Nepodařilo se odeslat údaje na server. Ale nebojte, o váš pokus nepřijdete. Byl uložen v telefonu a na internetu se objeví během zítřejšího dne.";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanks);
        
        btn_back_start = (Button) findViewById(R.id.btn_back_start);
        tv_thanks = (TextView) findViewById(R.id.tv_thanks);
        
        boolean success = getIntent().getBooleanExtra("success", false);
        
        if( success ) {
        	tv_thanks.setText(SUCCESS);
        } else {
        	tv_thanks.setText(FAIL);
        }
        
        btn_back_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { 
            	Intent myIntent = new Intent(Thanks.this, Main.class);
            	startActivity(myIntent);
            }
        });
	}
}
