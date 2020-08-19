package com.example.a8x06_app2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.EditText;

import android.content.Context;
import android.content.Intent;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

		// btnStart clicked 
		public void onStart(View view) {
    // create activity
    /*
      // Intent myIntent = new Intent(CurrentActivity.this, NextActivity.class);
			Intent intent = new Intent(this, QuizActivity.class);
			// String message = "hiworld";
			// intent.putExtra(EXTRA_MESSAGE, message);
			startActivity(intent);
      */

  // use nav to 
      /*
			String message = "hiworld";
      NavDirections action = StartActivityDirections.actionQuiz(message);
      Navigation.findNavController(view).navigate(action);
      */
		}
}
