package com.csci422.tutor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class AdditionActivity extends Activity {

	private enum ProblemType {ADDITION, SUBTRACTION, BOTH};
	
	TextView title;
	ProblemType problemType;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        
        Intent intent = getIntent();
        title = (TextView)findViewById(R.id.title);
        String newTitle = intent.getStringExtra("title");
        
        if(newTitle.equals("add")){
        	title.setText(R.string.addition);
        	problemType = ProblemType.ADDITION;
        }
        else if(newTitle.equals("sub")) {
        	title.setText(R.string.subtraction);
        	problemType = ProblemType.SUBTRACTION;
        }
        else if(newTitle.equals("both")) {
        	title.setText(R.string.both);
        	problemType = ProblemType.BOTH;
        }
        
        generateProblem();
    }
    
    private void generateProblem() {
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_addition, menu);
        return true;
    }
}