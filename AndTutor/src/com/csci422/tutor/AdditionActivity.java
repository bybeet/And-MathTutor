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
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdditionActivity extends Activity implements OnGesturePerformedListener {

	private enum ProblemType {ADDITION, SUBTRACTION, BOTH};

	TextView title;
	TextView generatedNumber;
	TextView goalNumber;
	TextView userInput;
	TextView uiCounter;
	View view;

	//Maybe add a setting option to change this.
	final int max = 15;
	Integer goal;
	Integer generated;
	Integer userNumber;
	int counter;
	int numberToSolve;

	Random rand;

	ProblemType problemType;
	GestureOverlayView gestures;
	GestureLibrary mLibrary;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addition);

		Intent intent = getIntent();
		view = findViewById(R.id.goalNumber);
		title = (TextView)findViewById(R.id.title);
		generatedNumber = (TextView)findViewById(R.id.startingNumber);
		userInput = (TextView)findViewById(R.id.userInput);
		goalNumber = (TextView)findViewById(R.id.goalNumber);
		uiCounter = (TextView)findViewById(R.id.uiCounter);
		String newTitle = intent.getStringExtra("title");
		rand = new Random();
		userNumber = 0;
		goal = 0;
		counter = 0;
		numberToSolve = 5;

		mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!mLibrary.load()) {
			finish();
		}

		GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		gestures.addOnGesturePerformedListener(this);

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
		
		uiCounter.setText("Solved " + counter + "/" + numberToSolve);
		
		if(counter == numberToSolve){
			this.finish();
		}
		
		userInput.setText(userNumber.toString());

		goal = rand.nextInt(max);
		while(goal <= 0) {
			goal = rand.nextInt(max);
		}
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
				generateProblem();
			}
			else {
				//give user input that they are wrong
				incorrectAnswer();
			}
		}
		else
			if(addOrSub == ProblemType.ADDITION){
				Toast.makeText(this, "This is a subtraction - problem.", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(this, "This is an addition + problem.", Toast.LENGTH_SHORT).show();
			}
	}

	private void correctAnswer( ){
		Toast.makeText( this, "That answer is correct, =D", Toast.LENGTH_LONG ).show();		
		counter++;
	}

	private void incorrectAnswer( ){
		Toast.makeText( this, "That answer is incorrect, =(", Toast.LENGTH_LONG ).show();
	}

	private void updateUserInput() {
		userInput.setText(userNumber.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_addition, menu);
		return true;
	}

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ArrayList<Prediction> predictions = new ArrayList<Prediction>();
		predictions = mLibrary.recognize(gesture);

		if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
			String result = predictions.get(0).name;

			if ("Plus".equalsIgnoreCase(result) || "Plus Sign 2".equalsIgnoreCase(result)) {
				testAnswer(ProblemType.ADDITION);
			}else if ("Minus".equalsIgnoreCase(result)) {
				testAnswer(ProblemType.SUBTRACTION);
			}else if ("Up Swipe".equalsIgnoreCase(result)) {
				userNumber++;
				updateUserInput();
			}else if ("Down Swipe".equalsIgnoreCase(result)) {
				userNumber--;
				updateUserInput();
			}
		}
	}
}