package com.example.a8x06_app2;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.CompoundButton;
import	android.widget.LinearLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentQuiz#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentQuiz extends Fragment 
	implements android.widget.CompoundButton.OnCheckedChangeListener {

    int m_curr;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentQuiz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentQuiz.
     */

    // TODO: Rename and change types and number of parameters
    public static FragmentQuiz newInstance(String param1, String param2) {
        FragmentQuiz fragment = new FragmentQuiz();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

				// set nav up cb
				// ----------
				// this callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
							@Override
							public void handleOnBackPressed() {
									// Handle the back button event
									// nav
									NavDirections action = FragmentQuizDirections.actionFragmentQuizToFragmentList();
									Navigation.findNavController(getView()).navigate(action);
              }
        };
				requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        // The callback can be enabled or disabled here or in handleOnBackPressed()

				return rootView;
    }

    // update ui
    public void update() {
      MainActivity a = (MainActivity)getHost();
      Survey s = a.g_survey;
      Qstn q = s.list.get(m_curr);

      // qstn title
			TextView tv_title = (TextView)getView().findViewById(R.id.tv_title);
      tv_title.setText(q.title);

			// XXX show qstn num/total
			TextView tv = (TextView)getView().findViewById(R.id.tv_num);
			tv.setText(m_curr+1+ "/" + s.list.size());

      // checkbox for multiple, radiobox oterwise
      if (q.b_mul) {
        // create checkbox
        // -----------
        LinearLayout layout = (LinearLayout)getView().findViewById(R.id.linear_layout);
        final CheckBox[] cb_list = new CheckBox[5];
        for(int i=0; i<q.opts.length; i++) {
           cb_list[i]  = new CheckBox(getContext());
           cb_list[i].setId(i + 100);
           cb_list[i].setTextSize(30);
           // checkbox onClick
           cb_list[i].setOnCheckedChangeListener(this);
           layout.addView(cb_list[i]);
           cb_list[i].setText(q.opts[i]);
        }
      } else {
        // create radio
        // -----------
        RadioGroup rg = (RadioGroup)getView().findViewById(R.id.rg);
        final RadioButton[] rb_list = new RadioButton[5];
        for(int i=0; i<q.opts.length; i++) {
           rb_list[i]  = new RadioButton(getContext());
           rb_list[i].setId(i + 100);
           rb_list[i].setText(q.opts[i]);
           rb_list[i].setTextSize(30);
           rg.addView(rb_list[i]);
        }
      } // radio

      // save answer to session

      // btns
      if (m_curr == 0) {
        // disable prev
      }
      if (m_curr >= s.list.size()) {
        // next -> finish
      }
    } // end of update

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
			// get arg
			String target_app_name = FragmentQuizArgs.fromBundle(getArguments()).getTargetName();
			m_curr = FragmentQuizArgs.fromBundle(getArguments()).getCurrPos();

      // radio group onClick
      RadioGroup rg = (RadioGroup)getView().findViewById(R.id.rg);
      rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            TextView tv = (TextView)getView().findViewById(R.id.tv_num);
            switch(checkedId) {
              // case R.id.rb_1:
              // 		// TODO Something
              // 		tv.setText("rb1");
              // 		break;
              // case R.id.rb_2:
                // 	// TODO Something
                // 	tv.setText("rb2");
              // 		break;
              }
          }
        });

			// btn prev
			Button btn_prev = (Button)getView().findViewById(R.id.btn_prev);
			btn_prev.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
            m_curr++;
            update();
					}
			});

      // update UI to show curr qstn
      update();
	} // onViewCreated

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		TextView tv = (TextView)getView().findViewById(R.id.tv_num);
		tv.setText("rb1"); // XXX
		// switch(checkedId) {
			// case R.id.rb_1:
			// 		// TODO Something
			// 		tv.setText("rb1");
			// 		break;
			// case R.id.rb_2:
				// 	// TODO Something
				// 	tv.setText("rb2");
			// 		break;
			// }
	}
}

