package com.csci422.tutor;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.View;

public class AdditionActivity extends Activity {

	private enum ProblemType {ADDITION, SUBTRACTION, BOTH};
	
	TextView title;
	TextView generatedNumber;
	TextView goalNumber;
	TextView userInput;
	
	final int max = 15;
	Integer goal;
	Integer generated;
	
	Random rand;
	
	ProblemType problemType;
	GestureDetector gestures;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        
        Intent intent = getIntent();
        title = (TextView)findViewById(R.id.title);
        generatedNumber = (TextView)findViewById(R.id.startingNumber);
        userInput = (TextView)findViewById(R.id.userInput);
        goalNumber = (TextView)findViewById(R.id.goalNumber);
        gestures = new GestureDetector(this, new GestureListener());
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
    	generated = goal - rand.nextInt(goal);
    	generatedNumber.setText(generated.toString());
    }
    
    private void subtractionProblem() {
    	generated = goal + rand.nextInt(max - goal);
    	generatedNumber.setText(generated.toString());
    }
    
    private void bothProblem() {
    	if(rand.nextInt()%2 == 0)
    		additionProblem();
    	else
    		subtractionProblem();
    }
    
    private boolean checkProblem(ProblemType addOrSub) {
    	int userNumber = Integer.parseInt((String) userInput.getText());
    	
    	if(addOrSub == ProblemType.ADDITION){
    		if( (generated + userNumber) == goal)
    			return true;
    	}
    	else
    		if( (generated - userNumber) == goal )
    			return true;
    	
    	return false;
    }
    
    private void testAnswer(ProblemType addOrSub) {
    	if(checkProblem(addOrSub)) {
    		//Give user input, maybe a toast?
    		//Generate new problem
    		generateProblem();
    	}
    	else {
    		//give user input that they are wrong
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_addition, menu);
        return true;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestures.onTouchEvent(e);
    }
    
    private class GestureListener implements GestureDetector.OnGestureListener{
    	
    	public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}

		public void onLongPress(MotionEvent e) {
			generateProblem();
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
    	
    }
}