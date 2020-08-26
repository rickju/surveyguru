package com.example.a8x06_app2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import java.util.ArrayList;

import java.util.ArrayList;

public class MyApp extends Application {
  // app globals
  public static SharedPreferences pref;
  public static Survey survey;
  public static ArrayList<Session> sssn_list;
  public static SessionAdapter sssn_adapter;

  // init app
  public static void init(Context _ctx) {
      // init questions
      // ----------------
      survey = new Survey();
      // q1
      {
        String t = "Q1: About how the product works, is it understandable or not understandable";
        String[] opts = {"A: Understandable", "B: Not Understandable"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 2
      {
        String t = "Q2: Is it easy to get familiar with the product and to learn how to use it?";
        String[] opts = {"A: Yes", "B: No"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 3
      {
        String t = "Q3: Can you solve your tasks without unnecessary effort?";
        String[] opts = {"A: Yes", "B: No"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 4
      {
        String t = "Q4: Do you feel in control of the interaction?";
        String[] opts = {"A: Yes", "B: No"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 5
      {
        String t = "Q5: The UI of the product, is it organized or cluttered?";
        String[] opts = {"A. Organized", "B. Cluttered"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 6
      {
        String t = "Q6: The reaction of the product, is it unpredictable? or predictable?";
        String[] opts = {"A: Predictable", "B: Unpredictable"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 7
      {
        String t = "Q7: Does the product react fast, or slow?";
        String[] opts = {"A: Fast", "B: Slow"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }

      // q 8
      {
        String t = "Q8: Is it easy to learn, or difficult to learn?";
        String[] opts = {"A: Easy", "B: Difficult"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 9
      {
        String t = "Q9: To finish a work, is it efficient or inefficient?";
        String[] opts = {"A: Efficient", "B: Inefficient"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 10
      {
        String t = "Q10: Your overall impression of the product UI. Do you like it or not?";
        String[] opts = {"A: Like it", "B: Do not like it"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }

      // simple unit test for session
      /*
      try {
        System.out.println("------------   new session: ----");
        Session s1 = new Session(pref, survey, "Twitter");
        s1 .print();

        System.out.println("------------   give aswr: q: 0  a: 1");
        s1.give_aswr(0, 1);
        s1.print();

        System.out.println("------------   give aswr: q: 1  a: 2");
        s1.give_aswr(1, 2);
        s1.print();

        System.out.println("------------   finish ");
        s1.finish();
        s1.print();

        System.out.println("------------   load session: Twitter");
        Session s2 = new Session(pref, survey, "Twitter");
        s2.print();
      } catch (Exception _e) {
        System.out.println(_e);
      }
      */

      try {
        // pref
        pref = _ctx.getSharedPreferences("MyPref2", 0);

        // construct the data source
        sssn_list = new ArrayList<Session>();
        // create the adapter to convert the array to views
        sssn_adapter = new SessionAdapter(_ctx, sssn_list);
       
        // load sssn from pref
        do_load_sssn();
        if (sssn_list.size() == 0) {
          // if 1st time start, add some example items
          Session what = new Session(pref, survey, "Whatsapp");
          Session tw = new Session(pref, survey, "Twitter");
          sssn_adapter.add(what);
          sssn_adapter.add(tw);
        }
      } catch (Exception _e) {
        System.out.println(_e);
      }
  }

  // load sssns from pref. e.g. string: "Taobao,Whatsapp, ..."
  private static void do_load_sssn() {
    String str = pref.getString("sssn_name_list", "");
    System.out.println("read sssn_name_list: "+str);

    String[] name_list = str.split(",", 0);
    for (String name : name_list) {
      get_sssn(name);
    } 
  }

  // get from list, or create a session when not exist
  public static Session get_sssn(String _name) {
    try {
      for (int i=0; i<sssn_list.size(); i++) {
        if (sssn_list.get(i).name == _name) 
          return sssn_list.get(i);
      }
      // create new one
      Session s = new Session(pref, MyApp.survey, _name);
      sssn_adapter.add(s);
      // gen a new sssn_name_list. e.g. string: "Taobao,Whatsapp, ..."
      String name_list = "";
      for (int i=0; i<sssn_list.size(); i++) {
        name_list += sssn_list.get(i).name + ",";
      }
      // save sssn_name_list
      SharedPreferences.Editor editor = pref.edit();
      System.out.println("write sssn_name_list: "+name_list);
      editor.putString("sssn_name_list", name_list);
      editor.commit();
      // done
      return s;
    } catch (Exception _e) {
      System.out.println(_e);
    }
    return null;
  }
}

