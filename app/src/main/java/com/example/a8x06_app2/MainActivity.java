package com.example.a8x06_app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      MyApp.init(this);

      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
    }
}
