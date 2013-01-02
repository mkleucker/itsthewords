package is.justcurious.itsthewords;

import is.justcurious.itsthewords.R;
import is.justcurious.itsthewords.data.Word;
import is.justcurious.itsthewords.data.WordDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class WordControl extends ListActivity {

	private WordDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_control);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		// Get all words
		dataSource = new WordDataSource(this);
		
		showData();
		
		// Button "Add New"
		final Button buttonNew = (Button) findViewById(R.id.btn_add);
		buttonNew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ON CLICK: Change to the WordDetailActivity where the Information can be inserted
				startActivity(new Intent(getApplicationContext(), WordDetailActivity.class));
			}
		});
		
	}

	/**
	 * Gets the Words from the DataSource and populates the list.
	 * Extracted from onCreate() to be callable onResume() as well.
	 */
	private void showData(){
		dataSource.open();
		
		List<Word> words = dataSource.getAllWords();
		
		List<Map<String, String>> listData = new ArrayList<Map<String,String>>();
		
		for(Word word : words){
			Map<String, String> singleWord = new HashMap<String,String>(2);
			singleWord.put("lang1", word.getLang1());
			singleWord.put("lang2", word.getLang2());
			listData.add(singleWord);
		}
		
		// Create List Adapter
		SimpleAdapter adapter = new SimpleAdapter(
				this, 
				listData,
				android.R.layout.simple_list_item_2, 
				new String[] {"lang1", "lang2"},
				new int[] {android.R.id.text1, android.R.id.text2}
				);
				
		// Show List-Data		
		setListAdapter(adapter);
	}
	
	/**
	 * onResume() is called when the "New" view is popped. New words wouldn't be
	 * in the list otherwise. 
	 */
	@Override
	protected void onResume(){
		super.onResume();
		showData();
	}

	
	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {       
        startActivity(new Intent(WordControl.this,StartActivity.class)); 
        return true;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
