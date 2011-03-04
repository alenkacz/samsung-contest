package cz.alenkacz.samsung.dao;

import cz.alenkacz.samsung.model.Attemp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class EntryDatabase {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "samsung.db";
    public static final String ENTRY_TABLE_NAME = "attemp";
    
    public static final String ID_FIELD = "id";
    public static final int ID_FIELD_NUM = 0;
    public static final String NAME_FIELD = "name";
    public static final int NAME_FIELD_NUM = 1;
    public static final String EMAIL_FIELD = "email";
    public static final int EMAIL_FIELD_NUM = 2;
    public static final String TEL_FIELD = "tel";
    public static final int TEL_FIELD_NUM = 3;
    public static final String DATETIME_FIELD = "datetime";
    public static final int DATETIME_FIELD_NUM = 4;
    public static final String TEXT_FIELD = "text";
    public static final int TEXT_FIELD_NUM = 5;
    public static final String LENGTH_FIELD = "length";
    public static final int LENGTH_FIELD_NUM = 6;
	
	private SQLiteDatabase _db;
	private final Context _context;
	private EntryOpenHelper _helper;
	
	public EntryDatabase( Context context ) {
		_context = context;
		_helper = new EntryOpenHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void open() throws SQLException {
		try {
			_db = _helper.getWritableDatabase();
		} catch( SQLiteException e ) {
			_db = _helper.getReadableDatabase();
		}
	}
	
	public void close() {
		_db.close();
	}
	
	public long inserEntry(Attemp attemp) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(NAME_FIELD, attemp.get_name());
		contentValues.put(EMAIL_FIELD, attemp.get_email());
		contentValues.put(TEL_FIELD, attemp.get_tel());
		contentValues.put(DATETIME_FIELD, attemp.get_datetime());
		contentValues.put(TEXT_FIELD, attemp.get_text());
		contentValues.put(LENGTH_FIELD, attemp.get_length());
		
		return _db.insert(ENTRY_TABLE_NAME, null, contentValues);
	}
	
	public Cursor getAllEntries() {
		return _db.query(ENTRY_TABLE_NAME, new String[] {ID_FIELD,NAME_FIELD,EMAIL_FIELD,TEL_FIELD,DATETIME_FIELD,TEXT_FIELD, LENGTH_FIELD}, 
				null, null, null, null, null);
	}
}

class EntryOpenHelper extends SQLiteOpenHelper {
	
	SQLiteDatabase _db;
	
	private static final String ENTRY_TABLE_CREATE =
        "CREATE TABLE " + EntryDatabase.ENTRY_TABLE_NAME + " (" +
        EntryDatabase.ID_FIELD + " integer primary key autoincrement, " +
        EntryDatabase.NAME_FIELD + " text not null, " +
        EntryDatabase.EMAIL_FIELD + " text not null, " +
        EntryDatabase.TEL_FIELD + " text not null, " +
        EntryDatabase.DATETIME_FIELD + " text not null, " +
        EntryDatabase.TEXT_FIELD + " text not null, " +
        EntryDatabase.LENGTH_FIELD + " text not null);";

    EntryOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ENTRY_TABLE_CREATE);
        _db = db;
    }

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		_db.execSQL("DROP TABLE IF EXISTS " + EntryDatabase.ENTRY_TABLE_NAME);
		onCreate(_db);
	}
}
