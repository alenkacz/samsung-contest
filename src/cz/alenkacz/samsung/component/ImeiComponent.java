package cz.alenkacz.samsung.component;

import android.content.Context;
import android.telephony.TelephonyManager;

public class ImeiComponent {
	
	private Context _context;

	public ImeiComponent( Context context ) {
		_context = context;
	}
	
	public String get() {
		TelephonyManager telephonyMgr = (TelephonyManager) _context.getSystemService(_context.TELEPHONY_SERVICE);
		return telephonyMgr.getDeviceId(); 
	}
}
