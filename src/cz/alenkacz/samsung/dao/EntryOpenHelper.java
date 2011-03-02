package cz.alenkacz.samsung.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntryOpenHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "Samsung";
    private static final String ENTRY_TABLE_NAME = "entry";
    
    private static final String NAME_FIELD = "name";
    private static final String EMAIL_FIELD = "email";
    private static final String TEL_FIELD = "tel";
    private static final String DATETIME_FIELD = "datetime";
    private static final String TEXT_FIELD = "name";
    
    private SQLiteDatabase _database;
    private Context _context;
    
    private static final String ENTRY_TABLE_CREATE =
                "CREATE TABLE " + ENTRY_TABLE_NAME + " (" +
                NAME_FIELD + " TEXT, " +
                EMAIL_FIELD + " TEXT, " +
                TEL_FIELD + " TEXT, " +
                DATETIME_FIELD + " TEXT, " +
                TEXT_FIELD + " TEXT);";

    EntryOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ENTRY_TABLE_CREATE);
        _database = db;
    }

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}
}
