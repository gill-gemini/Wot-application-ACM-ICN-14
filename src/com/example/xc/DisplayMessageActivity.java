package com.example.xc;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class DisplayMessageActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the message from the intent
	    Intent intent = getIntent();
	  
	    Bundle extras = intent.getExtras();
	    String message = extras.getString("NODEA");
	    String message1 = extras.getString("NODEB");
	    String message2 = extras.getString("TRUST");
	    String message3 = extras.getString("TIME");
	    String message4 = extras.getString("GRAPHTIME");
	    String message5 = extras.getString("exptrue");
	    // Create the text view
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText("Trust Computation based on Web of Trust\n\n"+message+"\n"+message1+"\n"+message2+"\n"+message3+"\n"+message4+"\n\n"+message5);//+message2+"\n"+message3);

	    // Set the text view as the activity layout
	    setContentView(textView);
	
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}
}
