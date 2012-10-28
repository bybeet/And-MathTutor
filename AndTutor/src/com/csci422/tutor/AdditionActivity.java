package com.csci422.tutor;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class AdditionActivity extends Activity {

	private enum ProblemType {ADDITION, SUBTRACTION, BOTH};
	
	TextView title;
	TextView generatedNumber;
	TextView goalNumber;
	TextView userInput;
	
	final int max = 15;
	Integer goal;
	
	Random rand;
	
	ProblemType problemType;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        
        Intent intent = getIntent();
        title = (TextView)findViewById(R.id.title);
        generatedNumber = (TextView)findViewById(R.id.startingNumber);
        userInput = (TextView)findViewById(R.id.userInput);
        goalNumber = (TextView)findViewById(R.id.goalNumber);
        String newTitle = intent.getStringExtra("title");
        rand = new Random();
        
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
    	userInput.setText("0");
    	
    	goal = rand.nextInt(max);
    	goalNumber.setText(goal.toString());
    	
    	switch(problemType){
    	case ADDITION:
    		additionProblem();
    		break;
    	case SUBTRACTION:
    		subtractionProblem();
    		break;
    	case BOTH:
    		bothProblem();
    		break;
    	}
    }
    
    private void additionProblem() {
    	Integer generated = goal - rand.nextInt(goal);
    	generatedNumber.setText(generated.toString());
    }
    
    private void subtractionProblem() {
    	Integer generated = goal + rand.nextInt(max - goal);
    	generatedNumber.setText(generated.toString());
    }
    
    private void bothProblem() {
    	if(rand.nextInt()%2 == 0)
    		additionProblem();
    	else
    		subtractionProblem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_addition, menu);
        return true;
    }
}