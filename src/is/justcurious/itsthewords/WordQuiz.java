package is.justcurious.itsthewords;

import is.justcurious.itsthewords.data.QuizLogic;
import is.justcurious.itsthewords.data.Word;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Shows the Quiz and handles the user's answers.
 * 
 * @author max
 *
 */
public class WordQuiz extends Activity {

	private QuizLogic quiz;
	
	private EditText fieldText;
	private TextView quizText;
	private TextView quizCategory;
	private Button btnCheck;
	
	private Word currentWord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_quiz);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Connect View
		fieldText = (EditText) findViewById(R.id.quiz_textEdit);
		quizText = (TextView) findViewById(R.id.quiz_textView);
		quizCategory = (TextView) findViewById(R.id.quiz_category);
		btnCheck = (Button) findViewById(R.id.btn_check);
		
		// Initiate Quiz
		quiz = new QuizLogic(this);
		
		quiz.initQuiz();
		
		// Whhoooops! No words at all? – Then stop it right here
		if(!quiz.hasWords()){
			quizText.setText("Please enter some words first.");
			return;
		}
		
		currentWord = quiz.getNextWord();
		
		setWordToView(currentWord);
		
		btnCheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String enteredSolution = fieldText.getText().toString();
				boolean solutionIsCorrect = quiz.checkWord(enteredSolution);
				
				final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
				
				if(solutionIsCorrect){
					setSuccessMessage(dialog);
				}else{
					setFailMessage(dialog);
				}
				
				dialog.show();
					
				// Instead of having the user to tap all the time, 
				// hide the popup automatically after 2.5 sec. 
				
				final Timer t = new Timer();
				t.schedule(new TimerTask() {
					public void run() {
						if(dialog.isShowing()){
							dialog.dismiss(); 	
						}
						
						t.cancel(); 
					}
				}, 2500);
				showNextWord();
			}
		});
		
	}
	
	private void showNextWord(){
		currentWord = quiz.getNextWord();
		setWordToView(currentWord);
	}
	
	private void setSuccessMessage(AlertDialog dialog){
		dialog.setTitle("Yayhooray!");
		dialog.setMessage("You were correct \""+currentWord.getLang1()+"\" indeed means \""+currentWord.getLang2()+"\".");
	}
	
	private void setFailMessage(AlertDialog dialog){
		dialog.setTitle("Ouch!");
		dialog.setMessage("You were wrong...  \""+currentWord.getLang1()+"\" does mean \""+currentWord.getLang2()+"\".");
	}
	
	
	private void setWordToView(Word word){
		quizText.setText(word.getLang1());
		quizCategory.setText(word.getCategory());
		fieldText.setText("");
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {       
        finish();
        return true;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
