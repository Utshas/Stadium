package com.resoft.stadium;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;

public class All_scores extends Activity{
	String[] cricLinks;
	String[] cricTitle;
	String url="http://www.cricbuzz.com/cricket-match/live-scores";
	ProgressDialog pd;
	String Score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_scores);
		cricLinks = new String[15];
		cricTitle = new String[15];
		new Scores().execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_scores, menu);
		return true;
	}
	
	public class Scores extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd=new ProgressDialog(All_scores.this);
			pd.setTitle("List of Scores");
			pd.setMessage("Loading...");
			pd.setIndeterminate(false);
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			// TODO Auto-generated method stub
			try {
                org.jsoup.nodes.Document doc=Jsoup.connect(url).get();
				int i=0;
				for( Element e : doc.select("a.text-hvr-underline") )
				{
					cricLinks[i]="http://www.cricbuzz.com"+e.attr("href");
					cricTitle[i]=e.text();
					i++;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return null;
			
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//Toast.makeText(getApplicationContext(), tail, Toast.LENGTH_LONG).show();
			Intent i=new Intent(All_scores.this,Show_Scores.class);
			i.putExtra("s", cricTitle);
			i.putExtra("d", cricLinks);
			startActivity(i);
			pd.dismiss();
		}
		
	}
	
}