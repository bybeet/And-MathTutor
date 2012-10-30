/*
 * 	TODO
 * 
 * 	-Add gesture support for:
 * 			Up swipe
 * 			Down swipe
 * 			Plus sign
 * 			Minus sign
 * 			http://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
 * 
 * 	-Message for the user ( done in function testAnswer())
 * 			Correct answer
 * 			Incorrect answer
 * 
 * 
 * 	-Maybe....
 * 			Achievements
 * 			Counter( get to 10, then return to main menu)
 * 			Menu
 * 				Setting max number
 * 				Help dialog(or a help button)
 */

package com.csci422.tutor;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class AdditionActivity extends Activity implements OnGesturePerformedListener {

	private enum ProblemType {ADDITION, SUBTRACTION, BOTH};

	TextView title;
	TextView generatedNumber;
	TextView goalNumber;
	TextView userInput;
	
	// Handles gestures
	GestureLibrary mLibrary;

	// Maybe add a setting option to change this.
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
		
		// Init the gesture stuff
		 mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		 if (!mLibrary.load()) {
		   finish();
		 }

		 GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		 gestures.addOnGesturePerformedListener(this);

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

	//Called after + or - gesture is created by the user.
	//Plus should pass textAnswer ProblemType.ADDITION
	private void testAnswer(ProblemType addOrSub) {
		//Make sure that operation chosen matches the ProblemType
		//	If not, then display a message to the user.
		if(problemType == addOrSub || problemType == ProblemType.BOTH){
			if(checkProblem(addOrSub)) {
				//Give user input, maybe a toast?
				correctAnswer();
				//Generate new problem
				generateProblem();
			}
			else {
				//give user input that they are wrong
				incorrectAnswer();
			}
		}
		else
			if(addOrSub == ProblemType.ADDITION){
				Toast.makeText(this, "This is a subtraction - problem.", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(this, "This is an addition + problem.", Toast.LENGTH_LONG).show();
			}
	}

	private void correctAnswer( ){
		Toast.makeText( this, "That answer is correct.", Toast.LENGTH_LONG ).show();
	}
	
	private void incorrectAnswer( ){
		Toast.makeText( this, "That answer is incorrect.", Toast.LENGTH_LONG ).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_addition, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return true;
	}
	
	public void onGesturePerformed(GestureOverlayView arg0, Gesture gesture) {
		// TODO Auto-generated method stub
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		
		if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
		     String result = predictions.get(0).name;

		     if ("Plus".equalsIgnoreCase(result)) {
		    	 testAnswer(ProblemType.ADDITION);
		     }else if ("Minus".equalsIgnoreCase(result)) {
		    	 testAnswer(ProblemType.SUBTRACTION);
		     }else if ("Up Swipe".equalsIgnoreCase(result)) {
				 generated++;
		     }else if ("Down Swipe".equalsIgnoreCase(result)) {
		    	 generated--;
		     }
		   }
	}
}