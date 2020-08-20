package com.example.a8x06_app2;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;

import android.os.Bundle;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import java.util.ArrayList;

// Question
class Qstn {
     public String title;
   public String[] opts;  // max 6. otherwise too many for a phone height.
        public int aswr;  // correct answer
    public boolean b_mul; // only 1 answer or multiple. TODO. must be false for now.

   // constructor
   public Qstn(String _t, String[] _o, boolean _b_mul, int _aswr) {
     title = _t;
     opts = _o;
     b_mul = _b_mul;
     aswr = _aswr;
   }
};

// Survey: define a list of questions
class Survey {
  // qstn list
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

// for a testing session of a target app
class Session {
  public String name;  // target_app_name;
  public String mark;

  boolean b_done;
     int curr;  // curr step/qstn. e.g. 3 for 3/10 
   int[] aswr;  // aswr[i] is for qstn_i(survey.list[i])

  Survey s;
  SharedPreferences pref;
  SharedPreferences.Editor editor;

  // contructor
  public Session(SharedPreferences _pref, Survey _s, String _name) throws Exception  {
    if (_s == null || _name.length() == 0)
      throw new Exception("Session constructor: invalid arg");
    s = _s;
    pref = _pref;
    name = _name;
    aswr = new int[s.list.size()];
    pref = _pref;
    editor = pref.edit();
    // load from pref
    do_load();
  }

  // print for unit test
  public void print() {
    System.out.println("Session: "+name + ", curr: "+curr +", done: "+b_done);
    for (int i=0; i<aswr.length; i++)
      System.out.println("   "+i+": "+aswr[i]);
  }

  // check if all questions answered
  public boolean is_done() {
    return b_done;
  }

  //
  public void finish() {
    b_done = true;
    editor.putBoolean(name+"#done", b_done);
    editor.commit();
  }

  // calc marks out of 100.  
  public void do_calc_mark() {
    double db_mark = 0;
    double each = 100 / (double)s.list.size();
    for (int i=0; i<s.list.size(); i++) {
      if (s.list.get(i).aswr == aswr[i])
        db_mark += each;
    }
    int i_mark = (int)db_mark;
    mark = Integer.toString(i_mark);
  }

  // when user gave an answer
  public void give_aswr(int _qstn_id, int _aswr) throws Exception  {
    int i = _qstn_id;
    // check qstn id bound
    if (i < 0 || i >= s.list.size())
      throw new Exception("Session::give_aswr : invalid arg: _qstn_id: "+i);
    // check aswr bound
    if (_aswr >= s.list.get(i).opts.length)
      throw new Exception("Session::give_aswr : invalid arg: _aswr: "+_aswr);
    
    aswr[i] = _aswr;
    curr = _qstn_id+1;
    // store
    editor.putInt(name+"#curr", i);
    editor.putInt(name+"#aswr#"+i, aswr[i]);
    // commit
    editor.commit();
  }

  // load from pref
  private void do_load() {
    b_done = pref.getBoolean(name+"#done", false);
    curr = pref.getInt(name+"#curr", 0);
    // read aswr
    int len = s.list.size();
    for (int i=0; i<len; i++) {
      aswr[i] = pref.getInt(name+"#aswr#"+i, -1);
    }

    if (b_done)
      do_calc_mark();
    else
      mark="In Progress";
  }
};

class SessionAdapter extends ArrayAdapter<Session> {
    public SessionAdapter(Context context, ArrayList<Session> _session) {
       super(context, 0, _session);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // get the data item for this position
       Session session = getItem(position);    
       // check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
       }
       // lookup view for data population
       TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
       TextView tvMark = (TextView) convertView.findViewById(R.id.tvMark);
       // populate the data into the template view using the data object
       tvName.setText(session.name);
       tvName.setTextSize(25);
       tvMark.setText(session.mark);
       // return the completed view to render on screen
       return convertView;
   }
}

