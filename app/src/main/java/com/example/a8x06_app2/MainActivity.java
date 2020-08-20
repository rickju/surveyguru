package com.example.a8x06_app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // globals
    private SharedPreferences pref;
    public Survey survey;
    public ArrayList<Session> sssn_list;
    public SessionAdapter sssn_adapter;

    // load sssns from pref. string: "Taobao,Whatsapp, ..."
    public void do_load_sssn() {
      String str = pref.getString("sssn_name_list", "");
      String[] name_list = str.split(",", 0);
      for (String name : name_list) {
        get_sssn(name);
      } 
    }

    // get from list, or create a session when not exist
    public Session get_sssn(String _name) {
      try {
        for (int i=0; i<sssn_list.size(); i++) {
          if (sssn_list.get(i).name == _name) 
            return sssn_list.get(i);
        }
        // create new one
        Session s = new Session(pref, survey, _name);
        sssn_adapter.add(s);
        // gen new sssn name list
        String name_list = "";
        for (int i=0; i<sssn_list.size(); i++) {
          name_list += sssn_list.get(i).name + ",";
        }
        // update pref 
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sssn_name_list", "string value");
        // done
        return s;
      } catch (Exception _e) {
        System.out.println(_e);
      }
      return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      // pref
      pref = getSharedPreferences("MyPref", 0);

      // init questions
      // ----------------
      survey = new Survey();
      // q 1
      {
        String t = "Q: Hi, are u alice?";
        String[] opts = {"A: Yes", "B: No", "C: Maybe"};
        int aswr = 0;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }

      // q 2
      {
        String t = "Q: Hi, are u bob? "; 
        String[] opts = {"A: Yes", "B: No", "C: Maybe"};
        int aswr = 1;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }
      // q 3
      {
        String t = "Q: Hi, are u rick? "; 
        String[] opts = {"A: Yes", "B: No", "C: Maybe"};
        int aswr = 2;
        boolean b_mul = false;
        Qstn q = new Qstn(t, opts, b_mul, aswr);
        survey.add(q);
      }

      // simple unit test for session
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

      try {
        // construct the data source
        sssn_list = new ArrayList<Session>();
        // create the adapter to convert the array to views
        sssn_adapter = new SessionAdapter(this, sssn_list);
        // add some example items
        Session what = new Session(pref, survey, "Whatsapp");
        Session tw = new Session(pref, survey, "Twitter");
        sssn_adapter.add(what);
        sssn_adapter.add(tw);
        // load from pref
      } catch (Exception _e) {
        System.out.println(_e);
      }
    } // end of onCreate
}
