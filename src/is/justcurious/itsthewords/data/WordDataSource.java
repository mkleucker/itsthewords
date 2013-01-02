package is.justcurious.itsthewords.data;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.UserDictionary.Words;

public class WordDataSource {

	private SQLiteDatabase database;
	private DBAdapter dbHelper;
	
	private String[] allFields = { 
			DBAdapter.KEY_ROWID, 
			DBAdapter.KEY_LANG1,
			DBAdapter.KEY_LANG2,
			DBAdapter.KEY_CATEGORY,
			DBAdapter.KEY_CORRECT,
			DBAdapter.KEY_INCORRECT
	};
	
	public WordDataSource(Context ctx){
		dbHelper = new DBAdapter(ctx);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Word createWord(String lang1, String lang2, String category){
		ContentValues values = new ContentValues();
		values.put(DBAdapter.KEY_LANG1, lang1);
		values.put(DBAdapter.KEY_LANG2, lang2);
		values.put(DBAdapter.KEY_CATEGORY, category);
		
		long insertId = database.insert(DBAdapter.DATABASE_TABLE, null, values);
		
		Cursor cursor = database.query(DBAdapter.DATABASE_TABLE, 
				allFields, 
				DBAdapter.KEY_ROWID + " = "+ insertId,
				null, null, null, null);
		
		cursor.moveToFirst();
		Word newWord = cursorToWord(cursor);
		cursor.close();
		
		return newWord;
	}
	
	private void deleteWord(Word word){
		long id = word.getId();
		database.delete(DBAdapter.DATABASE_TABLE, DBAdapter.KEY_ROWID + " = "+ id, null);
	}
	
	public List<Word> getAllWords() {
		List<Word> words = new ArrayList<Word>();
		
		Cursor cursor = database.query(DBAdapter.DATABASE_TABLE, allFields, null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Word word = cursorToWord(cursor);
			words.add(word);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return words;
		
	}
	
	protected Cursor getCursorForQuery(String rawQuery){
		
		Cursor cursor = database.rawQuery(rawQuery, null);
		return cursor; 
		
	}
	
	protected static Word cursorToWord(Cursor cursor){
		Word word = new Word();
		word.setId(cursor.getLong(0));
		word.setLang1(cursor.getString(1));
		word.setLang2(cursor.getString(2));
		word.setCategory(cursor.getString(3));
		word.setCountCorrect(cursor.getInt(4));
		word.setCountIncorrect(cursor.getInt(5));
		
		return word;
	}
	
	
	public void updateWord(Word word){
		long id = word.getId();
		ContentValues values = new ContentValues();
		values.put(DBAdapter.KEY_LANG1, word.getLang1());
		values.put(DBAdapter.KEY_LANG2, word.getLang2());
		values.put(DBAdapter.KEY_CATEGORY, word.getCategory());
		values.put(DBAdapter.KEY_CORRECT, word.getCountCorrect());
		values.put(DBAdapter.KEY_INCORRECT, word.getCountIncorrect());
		
		database.update(DBAdapter.DATABASE_TABLE, values, DBAdapter.KEY_ROWID + " = "+ id, null);
	}
}
