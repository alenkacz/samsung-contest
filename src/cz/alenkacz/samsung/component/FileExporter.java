package cz.alenkacz.samsung.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public class FileExporter {
	
	File _file;
	FileOutputStream _fos;
	boolean _externalStorageAvailable = false;
	boolean _externalStorageWriteable = false;
	
	private final String FILENAME = "samsung-contest.txt";
	
	public FileExporter() {
		try {
			File dir = Environment.getExternalStorageDirectory();
			_file = new File(dir,FILENAME);
			_fos = new FileOutputStream(_file);
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public boolean saveFile( String content ) {
		try {
			if(_fos != null)
				_fos.write(content.getBytes());
			
			return true;
		} catch( IOException e ) {
			return false;
		}
	}
	
	public void checkStorageStatus() {
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    _externalStorageAvailable = _externalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    _externalStorageAvailable = true;
		    _externalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    _externalStorageAvailable = _externalStorageWriteable = false;
		}
	}
}
