package is.justcurious.itsthewords;

import is.justcurious.itsthewords.R;
import is.justcurious.itsthewords.data.DBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		DBAdapter db = new DBAdapter(this);
		
		final Button buttonQuiz = (Button) findViewById(R.id.button_quiz);
		buttonQuiz.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), WordQuiz.class));
				
			}
		});
		
		final Button buttonControl = (Button) findViewById(R.id.button_control);
		buttonControl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), WordControl.class));
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
