package com.example.xc;

import gtna.graph.Graph;
import gtna.io.graphReader.GtnaGraphReader;
import gtna.metrics.trust.BasicPathLengthThreshold;
import gtna.metrics.trust.DisjunctPaths;
import gtna.metrics.trust.TrustMetric;

import java.util.Random;

//import com.example.test.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity {

	  private static final TrustMetric NULL = null;
	  private Spinner spinner1;
	
	 private RadioButton basicButton;
	 private RadioButton disjunctButton;
	 
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// EditText editText = (EditText) findViewById(R.id.node);
		//    String nodes = editText.getText().toString();
	//	  GtnaGraphReader reader = new GtnaGraphReader();
		//	Graph wotGraph2 = reader.read("./sdcard/Download/files/wot-"+nodes+".gtna");
		
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

	/** Called when the user clicks the "calculate trust metric" button */
	public void calculateTrust(View view) {
	    // Do something in response to button
		 Intent intent = new Intent(this, DisplayMessageActivity.class);
		    
		 
	//	 EditText editText = (EditText) findViewById(R.id.node);
	//	    String nodes = editText.getText().toString();
		
		 spinner1 = (Spinner) findViewById(R.id.spinner1);
		 String s1=String.valueOf(spinner1.getSelectedItem());
		 
		//    EditText editText1 = (EditText) findViewById(R.id.sample);
		  //  String samplesize = editText1.getText().toString();

		    EditText editText2 = (EditText) findViewById(R.id.path);
		    String mpath = editText2.getText().toString();
		    
		    EditText editText3 = (EditText) findViewById(R.id.minpath);
		    String mipath = editText3.getText().toString();
	
		    basicButton = (RadioButton) findViewById(R.id.basic);
		    disjunctButton = (RadioButton) findViewById(R.id.disjunct);
		   
		 //   int sample = Integer.parseInt(samplesize);
		    int maxpath  = Integer.parseInt(mpath);
		    int minpath=2; 
		    if(disjunctButton.isChecked())
		   {
		    minpath  = Integer.parseInt(mipath);
		   }
		    //////////////////////GTNA CODE////////////////////////////
		    long t2 = System.currentTimeMillis();
		    System.out.print("Graph Loading ...");
		    GtnaGraphReader reader = new GtnaGraphReader();
			Graph wotGraph2 = reader.read("./sdcard/Download/files/wot-"+s1+".gtna");
			long t3 = System.currentTimeMillis();
			System.out.print("Graph loaded, took this much time: "+ t3);
			
			
			TrustMetric tm=NULL;
			if(disjunctButton.isChecked()){
			tm = new DisjunctPaths(100, false, false,maxpath, minpath);
			}
			else if(basicButton.isChecked()){
			tm = new BasicPathLengthThreshold(100, false, false, maxpath);
			}
			else
			{
				System.out.println("Oye mara Select the button");
			}
			
			if(disjunctButton.isChecked() || basicButton.isChecked())
			{
				tm.prepareGraph(wotGraph2);
			}
			
			
			// Draw random nodes
			Random rnd = new Random(System.currentTimeMillis());
			
			int node1 = rnd.nextInt(wotGraph2.getNodeCount());
			int node2 = rnd.nextInt(wotGraph2.getNodeCount());
			
			
			// Calculate Trust
			long t0 = System.currentTimeMillis();
			boolean trust = tm.computeTrust(wotGraph2.getNode(node1), wotGraph2.getNode(node2));
			long t1 = System.currentTimeMillis();
			
			
			// Print results
			System.out.println("Node A: " + node1);
			System.out.println("Node B: " + node2);
			
			System.out.println("Runtime: " + (t1-t0) + "ms");
		    
		    /////////////////SENDING INTENT TO OTHER Activity////////////////////////////
		    
			
			Bundle extras = new Bundle();
			extras.putString("NODEA","NODE A:"+node1);
			extras.putString("NODEB","NODE B:"+node2);
			if (trust)
			{
				//System.out.println("Trust: TRUE");
				extras.putString("TRUST","Trust: TRUE");
			}
			else
			{
				//System.out.println(TRUST,"Trust: FALSE");
				extras.putString("TRUST","Trust: FALSE");
			}
			
			extras.putString("TIME","Runtime :"+(t1-t0) + "ms");
			extras.putString("GRAPHTIME","Graph loadtime :"+(t3-t2) + "ms");
			System.out.print("Graph load time :"+(t3-t2) + "ms");
			
			
			if (trust && disjunctButton.isChecked())
			{
				//System.out.println("Trust: TRUE");
				extras.putString("exptrue","Yes "+minpath +" disjunct paths with max path length of "+maxpath+" found from A("+node1+") -> B("+node2+").");
			}
			else if (trust && basicButton.isChecked())
			{
				//System.out.println(TRUST,"Trust: FALSE");
				extras.putString("exptrue","Yes path found from A("+node1+")->B("+node2+") with max path length of "+maxpath+".");
			}
			else if (disjunctButton.isChecked())
			{
				extras.putString("exptrue","No disjunct paths("+minpath +") with max path length of "+maxpath+" found from A("+node1+") -> B("+node2+").");
			}
			else if(basicButton.isChecked())
			{
				extras.putString("exptrue","No path of length <="+maxpath+" found from A("+node1+")->B("+node2+").");

			}
			intent.putExtras(extras);
			startActivity(intent);

	}

	}



