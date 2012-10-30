package com.csci422.tutor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MathTutor extends Activity {

	Button addition;
	Button subtraction;
	Button both;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        
        addition = (Button)findViewById(R.id.addition);
        addition.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(MathTutor.this, com.csci422.tutor.AdditionActivity.class);
				intent.putExtra("title", "add");
				startActivity(intent);
			}
		});
        
        subtraction = (Button)findViewById(R.id.subtraction);
        subtraction.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(MathTutor.this, com.csci422.tutor.AdditionActivity.class);
				intent.putExtra("title", "sub");
				startActivity(intent);
			}
		});
        
        both = (Button)findViewById(R.id.both);
        both.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(MathTutor.this, com.csci422.tutor.AdditionActivity.class);
				intent.putExtra("title", "both");
				startActivity(intent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_screen, menu);
        return true;
    }
}