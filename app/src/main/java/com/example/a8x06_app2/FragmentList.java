package com.example.a8x06_app2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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

import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;

import android.content.Context;
import android.content.Intent;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

class Target {
    public String name;
    public String mark;

    public Target(String _name, String _mark) {
       this.name = _name;
       this.mark = _mark;
    }
}

class TargetAdapter extends ArrayAdapter<Target> {
    public TargetAdapter(Context context, ArrayList<Target> _target) {
       super(context, 0, _target);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       Target target = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
       }
       // Lookup view for data population
       TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
       TextView tvMark = (TextView) convertView.findViewById(R.id.tvMark);
       // Populate the data into the template view using the data object
       tvName.setText(target.name);
       tvMark.setText(target.mark);
       // Return the completed view to render on screen
       return convertView;
   }
}

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
				return rootView;
    }

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
      //
			// Target[] target_list = { ("Nathan", "San Diego") };
      //
			// Construct the data source
			ArrayList<Target> arrayOfTarget = new ArrayList<Target>();
			// Create the adapter to convert the array to views
			TargetAdapter adapter = new TargetAdapter(getContext(), arrayOfTarget);
			// Attach the adapter to a ListView
			ListView listView = (ListView) getView().findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			// Add item to adapter
			Target newTarget = new Target("WhatsApp", "80/100");
			adapter.add(newTarget);

			// btn new
			Button btn = (Button)getView().findViewById(R.id.btn_new);
			btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						// nav
						NavDirections action = FragmentListDirections.actionFragmentListToFragmentStart();
						Navigation.findNavController(_view).navigate(action);
          /*
						// nav ->quiz
						FragmentListDirections.ActionFragmentListToFragmentQuiz action = FragmentListDirections.actionFragmentListToFragmentQuiz();
						action.setTargetName("Taobao");
						action.setCurrPos(0);
						Navigation.findNavController(_view).navigate(action);
            */
					}
			});
    }
}
