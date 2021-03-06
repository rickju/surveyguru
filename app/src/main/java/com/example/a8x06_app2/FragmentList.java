package com.example.a8x06_app2;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;

import android.content.Context;
import android.content.Intent;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentList extends Fragment 
    implements android.widget.AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView listView;

    public FragmentList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentList newInstance(String param1, String param2) {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // set title
        MainActivity main = (MainActivity)getHost();
        main.setTitle(getString(R.string.app_name));

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
      MainActivity a = (MainActivity)getHost();
      // adapter -> ListView
      listView = (ListView) getView().findViewById(R.id.list_view);
      listView.setAdapter(MyApp.sssn_adapter);
      listView.setOnItemClickListener(this);

      // btn new
      Button btn = (Button)getView().findViewById(R.id.btn_new);
      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View _view) {
            // nav to start
            NavDirections action = FragmentListDirections.actionFragmentListToFragmentStart();
            Navigation.findNavController(_view).navigate(action);
          }
      });
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _pos, long _id) {
      // get selected
      Session sssn = (Session)listView.getItemAtPosition(_pos);
      
      // nav to quiz
      FragmentListDirections.ActionFragmentListToFragmentQuiz action = FragmentListDirections.actionFragmentListToFragmentQuiz();
      action.setTargetName(sssn.name);
      action.setCurrPos(0);
      Navigation.findNavController(_view).navigate(action);
    }
}
