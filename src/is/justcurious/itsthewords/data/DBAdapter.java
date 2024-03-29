package is.justcurious.itsthewords.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Controlling the database connection.
 * @author max
 *
 */

public class DBAdapter extends SQLiteOpenHelper {
	
	private static final String TAG = "DBAdapter";
	
	private static final String DATABASE_NAME = "itw.db";
	public static final String DATABASE_TABLE = "words";
	private static final int DATABASE_VERSION = 1;
																	// Column-ID
	public static final String KEY_ROWID 	= "_id";				// 0
	public static final String KEY_LANG1 	= "lang_1";				// 1
	public static final String KEY_LANG2 	= "lang_2";				// 2
	public static final String KEY_CATEGORY = "category";			// 3
	public static final String KEY_CORRECT 	= "played_correct";		// 4
	public static final String KEY_INCORRECT = "played_incorrect";	// 5
	
	private SQLiteDatabase db;

	public DBAdapter(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		db = getWritableDatabase();
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		final String CREATE_TABLE_WORDS = "CREATE TABLE "+ DATABASE_TABLE + " ("+
				KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
				KEY_LANG1 + " TEXT," +
				KEY_LANG2 + " TEXT," + 
				KEY_CATEGORY + " TEXT," +
				KEY_CORRECT + " INTEGER," +
				KEY_INCORRECT + " INTEGER);";
		db.execSQL(CREATE_TABLE_WORDS);
		
		fillDBforFirstStart(db);
		
		Log.w(DBAdapter.class.getName(), "Created Database");
		
		
	}
	
	// Initiall Data
	private void fillDBforFirstStart(SQLiteDatabase db){
		enterNewWord(db, "Haus", "House", "Everyday");
		enterNewWord(db, "Straße", "Street", "Everyday");
		enterNewWord(db, "Baum", "Tree", "Everyday");
		enterNewWord(db, "Tür", "Door", "Everyday");
		enterNewWord(db, "Tisch", "Table", "Everyday");
		
		enterNewWord(db, "Suppe", "Soup", "Food");
		enterNewWord(db, "Salat", "Salad", "Food");
		enterNewWord(db, "Fleisch", "Meat", "Food");
		enterNewWord(db, "Fisch", "Fish", "Food");
		enterNewWord(db, "Brot", "Bread", "Food");
		
		
	}
	
	// Mini Helper 
	private void enterNewWord(SQLiteDatabase db, String lang1, String lang2, String category){
		ContentValues values = new ContentValues();
		values.put(KEY_LANG1, lang1);
		values.put(KEY_LANG2, lang2);
		values.put(KEY_CATEGORY, category);
				
		db.insert(
				DATABASE_TABLE, 
				null, 
				values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
