package com.resoft.stadium;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Show_Scores extends Activity implements OnItemClickListener{

String[] cricTitle;
String[] cricLinks;
ListView list;
ArrayList<String>aeng;
ArrayAdapter<String>adeng;
int size;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show__scores);
		cricTitle=getIntent().getStringArrayExtra("s");
		cricLinks=getIntent().getStringArrayExtra("d");
		size=cricTitle.length;
		list=(ListView)findViewById(R.id.listView1);
		aeng=new ArrayList<String>();
		adeng=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, aeng);
		list.setAdapter(adeng);
		list.setOnItemClickListener(this);
		addItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show__scores, menu);
		return true;
	}
	public void addItems()
	{
		for(int i=0;i<size;i++)
		{
			aeng.add(cricTitle[i]+"");
			adeng=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, aeng);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i=new Intent(Show_Scores.this,MainActivity.class);
		i.putExtra("sc", cricLinks[arg2]);
		startActivity(i);
	}

}
