package is.justcurious.itsthewords;

import is.justcurious.itsthewords.R;
import is.justcurious.itsthewords.data.WordDataSource;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WordDetailActivity extends Activity {

	private WordDataSource dataSource;

	private EditText fieldLang1;
	private EditText fieldLang2;
	private EditText fieldCategory;
	
	private Button btnDone;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_detail);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Get DataSource
		dataSource = new WordDataSource(this);
		dataSource.open();
		
		// Connecting View Elements
		
		fieldLang1 = (EditText) findViewById(R.id.editText1);
		fieldLang2 = (EditText) findViewById(R.id.editText2);
		fieldCategory = (EditText) findViewById(R.id.editText3);
		
		btnDone = (Button) findViewById(R.id.btn_done);
		
		// On pressing of "Done"
		btnDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String language1 = fieldLang1.getText().toString();
				String language2 = fieldLang2.getText().toString();
				String category = fieldCategory.getText().toString();
				
				// Check if all fields have Content
				if(language1.length() < 1 || language2.length() < 1 || category.length() < 1){
					AlertDialog alertEmptyFields = new AlertDialog.Builder(v.getContext()).create();
					alertEmptyFields.setTitle("Empty Fields");
					alertEmptyFields.setMessage("Please fill in all Fields.");
					alertEmptyFields.show();
					
					return;
				}
				
				// Insert word into DataSource
				dataSource.createWord(language1, language2, category);

				finish();
				
			}
		});
		
		
		
		
		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {       
		// Pop current view, so it isn't in the "Back"-Loop
        finish();
        return true;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
