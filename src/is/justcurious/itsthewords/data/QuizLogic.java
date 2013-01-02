package is.justcurious.itsthewords.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Contains the actual logic of dealing with the dataSource, getting new words
 * and checking user-entered words for correctness. 
 *  
 * @author max
 *
 */
public class QuizLogic{
	
	private WordDataSource dataSource;
	
	private Cursor quizCursor;
	
	private Word currentWord;

	public QuizLogic(Context ctx) {
		dataSource = new WordDataSource(ctx);
		
	}
	
	public void initQuiz(){
		// Open Connection
		dataSource.open();
		
		
		// TODO: Use propper Handling via DB-Classes
		String query = "SELECT *, " +
				"CASE WHEN (" + DBAdapter.KEY_INCORRECT +" + "+ DBAdapter.KEY_CORRECT +") = 0 "+
				"THEN 1 "+ // Make sure elements which haven't been tested yet show up first
				"ELSE ("+ DBAdapter.KEY_INCORRECT +" / ("+DBAdapter.KEY_INCORRECT +" + "+ DBAdapter.KEY_CORRECT +") ) "+
				"END "+
				"AS ratio " +
				"FROM "+ DBAdapter.DATABASE_TABLE +" "+
				"ORDER BY ratio DESC";
				
		quizCursor = dataSource.getCursorForQuery(query);
		if(quizCursor.getCount() > 0){
			quizCursor.moveToFirst();
		}
		Log.d("QuizLogic.class", "Results for Raw Query: " + String.valueOf(quizCursor.getCount()));
	}
	
	public void resetQuiz(){
		dataSource.close();
		initQuiz();
	}
	
	public Word getNextWord(){
		if(!quizCursor.isAfterLast()){	
			currentWord = WordDataSource.cursorToWord(quizCursor);
			quizCursor.moveToNext();
			return currentWord;
		}else{
			// All Words have been played. 
			if(quizCursor.getCount() > 0){
				resetQuiz();
				return getNextWord();
			}
			
			//it should never get here...
			return null;
		}
	}
	
	public boolean hasWords(){
		if(quizCursor.getCount() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean checkWord(String solution){
		
		boolean isGuessCorrect = currentWord.getLang2().equals(solution);
		
		if(isGuessCorrect){
			currentWord.addCorrect();
			Log.d("QuizLogic.Class", "Guess \""+ solution +"\" for \""+currentWord.getLang1()+"\" is correct.");
		}else{
			currentWord.addIncorrect(); 
			Log.d("QuizLogic.Class", "Guess \""+ solution +"\" for \""+
						currentWord.getLang1()+"\" is wrong. Correct: \""+currentWord.getLang2()+"\"");
		}
		
		dataSource.updateWord(currentWord);
		
		return isGuessCorrect;
		
	}
	
	
}
