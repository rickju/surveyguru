package com.example.a8x06_app2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import android.os.Bundle;

import android.content.res.ColorStateList;
import android.graphics.Color;

import java.util.ArrayList;

// Question
class Qstn {
     public String title;
   public String[] opts;  // Max allowed: 6
    public boolean b_mul; // choose 1 or multiple
      public int[] aswr;  // correct answer. example {0, 3} for A/D, {2} for C

   // constructor
   public Qstn(String _t, String[] _o, boolean _b_mul, int[] _aswr) {
     title = _t;
     opts = _o;
     b_mul = _b_mul;
     aswr = _aswr;
   }
};

// Survey: define a list of questions
class Survey {
  // question list
  public ArrayList<Qstn> list;

  // constructor
  public Survey() {
    list = new ArrayList<Qstn>();
  }
  // add
  public void add(Qstn _q)  {
    list.add(_q);
  }
};

// a test session
class Session {
  String target_app_name;

  boolean b_done;
   int mark;
   int curr;  // curr question id

  //
  int[] answer_list;

};

public class MainActivity extends AppCompatActivity {

    private int num;
    // global: survey def
    public static Survey g_survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      // init questions
      // ----------------
      g_survey = new Survey();
      // q 1
      {
        String t = "Q: Hi, are u alice?";
        String[] opts = {"A: Yes", "B: No", "C: Maybe"};
        int[] aswrs = { 0 };
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswrs);
        g_survey.add(q);
      }

      // q 2
      {
        String t = "Q: Hi, are u bob? "; 
        String[] opts = {"A: Yes", "B: No", "C: Maybe"};
        int[] aswrs = { 0 };
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswrs);
        g_survey.add(q);
      }
      // q 3
      {
        String t = "Q: Hi, are u bob? "; 
        String[] opts = {"A: Yes", "B: No", "C: Maybe"};
        int[] aswrs = { 0 };
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswrs);
        g_survey.add(q);
      }
    }
}
