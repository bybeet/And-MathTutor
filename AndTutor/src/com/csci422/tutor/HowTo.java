package com.csci422.tutor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HowTo extends Activity{
	TextView title;
	View view;
	
	@Override public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.how_to);

		title = (TextView)findViewById(R.id.title);
		//title.setText(R.string.title_how_to);
		view = findViewById(R.id.goalNumber);
	}
}
