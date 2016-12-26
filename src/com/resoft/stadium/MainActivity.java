package com.resoft.stadium;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	String url="http://www.cricbuzz.com/live-cricket-scorecard/17438/";
	String lru="";
	
	ProgressDialog pd;
	Button tb,db,all;
	TextView tt,dt;
	String Title, Desc;
	String temp=" ";
	int mode=1, lc=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tb=(Button)findViewById(R.id.t_b);
		tb.setOnClickListener(this);
		db=(Button)findViewById(R.id.d_b);
		db.setOnClickListener(this);
		all=(Button)findViewById(R.id.all);
		all.setOnClickListener(this);
		tt=(TextView)findViewById(R.id.t_t);
		dt=(TextView)findViewById(R.id.d_t);
		lru=getIntent().getExtras().getString("sc");
		url=lru.replace("scores", "scorecard");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View a) {
		// TODO Auto-generated method stub
		if(a.getId()==R.id.t_b)
		{
			new Title().execute();
		}
		else if(a.getId()==R.id.d_b)
		{
			Desc="\n";
			new Description().execute();
		}
		else if(a.getId()==R.id.all)
		{
			Intent i=new Intent(MainActivity.this,All_scores.class);
			startActivity(i);
		}
	}
	
	
	public class Title extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd=new ProgressDialog(MainActivity.this);
			pd.setTitle("Title");
			pd.setMessage("Loading...");
			pd.setIndeterminate(false);
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				org.jsoup.nodes.Document doc=Jsoup.connect(lru).get();
				Elements mash = doc.select("div.cb-scrs-wrp");
				Title=mash.text();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
			
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(Title.length()<5)
				tt.setText("This match has not started yet");
				else
					tt.setText(Title);
			pd.dismiss();
		}
		
	}
	
	
	public class Description extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd=new ProgressDialog(MainActivity.this);
			pd.setTitle("description");
			pd.setMessage("Loading...");
			pd.setIndeterminate(false);
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				org.jsoup.nodes.Document doc=Jsoup.connect(url).get();
				
				for( Element e : doc.select("div.cb-scrd-itms") )
				{   int j=0;
					String tmpo=" ";
					String combo=" ";
					temp=e.text();
					if(temp.regionMatches(0, "Extras", 0, 6))
					{
						mode=2;
					}
					else if((lc==2&&!(temp.regionMatches(0, "Yet", 0, 3)))||lc==3)
					{
						mode=3; lc=0;
					}
					
					
					
					else if(mode==3)
					{
						int c=1,d=0;
						for(int i=0;i<=temp.length();i++)
						{
							if(temp.regionMatches(i, " ", 0, 1))
							{
								tmpo=temp.substring(j,i+1);
								if(c<2 || (c==2 && temp.regionMatches(i+1, "(c)", 0, 3)))
									{combo=combo+tmpo;
									j=i;
									}
								
								else if((c==2 && !(temp.regionMatches(i+1, "1", 0, 1) || temp.regionMatches(i+1, "2", 0, 1)|| temp.regionMatches(i+1, "3", 0, 1) || temp.regionMatches(i+1, "4", 0, 1) || temp.regionMatches(i+1, "5", 0, 1) || temp.regionMatches(i+1, "6", 0, 1) || temp.regionMatches(i+1, "7", 0, 1) || temp.regionMatches(i+1, "8", 0, 1) || temp.regionMatches(i+1, "9", 0, 1)  || temp.regionMatches(i+1, "0", 0, 1))))
								{	
									mode=1;
									Desc=Desc+"\n"+"\n";
									break;
								}
								else{
								{   d=d+1;
									if(d==1 )
										{combo=combo+" "+tmpo+" "+" ovrs:";
										j=i;
										}
									else if(d==2)
										{combo=combo+tmpo+" maidens:";
										j=i;
										}
									else if(d==3)
										{combo=combo+tmpo+" runs:";
										j=i;
										}
									else if(d==4)
										{combo=combo+tmpo+" wkts:";
										j=i;
										}
									else if(d==5)
									{combo=combo+tmpo+" no balls:";
									j=i;
									}
									else if(d==6)
									{combo=combo+tmpo+" wides:";
									j=i;
									}
									else if(d==7)
										{combo=combo+" "+tmpo+" econo:";
										tmpo=temp.substring(i+1,temp.length());
										combo=combo+tmpo;
										j=i;
										break;
										}
									
								}
								}
									c=c+1;
							}
						}
						c=1;
						d=0;
					}
					
					
					if(mode==2)
					{
						combo=temp;
						lc=lc+1;
					}
					
					

					if(mode==1)
					{int c=1,d=0;
					for(int i=0;i<=temp.length();i++)
					{
						if(temp.regionMatches(i, " ", 0, 1))
						{
							tmpo=temp.substring(j,i+1);
							if(c<2)
								{combo=combo+tmpo;
								j=i;
								}
							else if(c==2)
							{combo=combo+tmpo+" ";
							j=i;}
							else if(c>2)
							{if(temp.regionMatches(i+1, "1", 0, 1) || temp.regionMatches(i+1, "2", 0, 1)|| temp.regionMatches(i+1, "3", 0, 1) || temp.regionMatches(i+1, "4", 0, 1) || temp.regionMatches(i+1, "5", 0, 1) || temp.regionMatches(i+1, "6", 0, 1) || temp.regionMatches(i+1, "7", 0, 1) || temp.regionMatches(i+1, "8", 0, 1) || temp.regionMatches(i+1, "9", 0, 1)  || temp.regionMatches(i+1, "0", 0, 1))
							{   d=d+1;
								if(d==1 )
									{combo=combo+" "+tmpo+" "+" runs:";
									j=i;
									}
								else if(d==2)
									{combo=combo+tmpo+" balls:";
									j=i;
									}
								else if(d==3)
									{combo=combo+tmpo+" 4's:";
									j=i;
									}
								else if(d==4)
									{combo=combo+tmpo+" 6's:";
									j=i;
									}
								else if(d==5)
									{combo=combo+tmpo+" s/r:";
									tmpo=temp.substring(i+1,temp.length());
									combo=combo+tmpo;
									j=i;
									break;
									}
								
							}
							}
								c=c+1;
						}
					}
					c=1;
					d=0;
					}
					
					
					Desc=Desc+combo+'\n'+'\n';
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
			if(Desc.length()<5)
			dt.setText("This match has not started yet");
			else
			dt.setText(Desc);
			pd.dismiss();
		}
		
	}
	
}
