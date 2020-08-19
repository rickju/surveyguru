package com.example.a8x06_app2;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;

import android.content.Context;
import android.content.Intent;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class ListActivity extends AppCompatActivity {
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_list);

// string list
/*
      String[] array = {"Whatsapp", "Facebook","Twitter","Tiktok"};
      ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_view, array);
      ListView listView = (ListView) findViewById(R.id.list_view);
      listView.setAdapter(adapter);
			*/

// complex list
			// Target[] target_list = { ("Nathan", "San Diego") };
			// Construct the data source
			ArrayList<Target> arrayOfTarget = new ArrayList<Target>();
			// Create the adapter to convert the array to views
			TargetAdapter adapter = new TargetAdapter(this, arrayOfTarget);
			// Attach the adapter to a ListView
			ListView listView = (ListView) findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			// Add item to adapter
			Target newTarget = new Target("WhatsApp", "80/100");
			adapter.add(newTarget);
    }

// btnNew clicked 
		public void onNew(View view) {
  // start an activity
  /*
      // Intent myIntent = new Intent(CurrentActivity.this, NextActivity.class);
			Intent intent = new Intent(this, StartActivity.class);
			// String message = "hiworld";
			// intent.putExtra(EXTRA_MESSAGE, message);
			startActivity(intent);
      */

  // use nav to 
      /*
      NavDirections action = ListActivityDirections.actionStart();
      Navigation.findNavController(view).navigate(action);
      */
		}
}
