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
    Session sssn;
    RadioGroup rg;

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
				// ---------------
				// this callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
							@Override
							public void handleOnBackPressed() { // Handle the back button event
									// nav -> list
									NavDirections action = FragmentQuizDirections.actionFragmentQuizToFragmentList();
									Navigation.findNavController(getView()).navigate(action);
              }
        };
				requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        // The callback can be enabled or disabled here or in handleOnBackPressed()

				return rootView;
    }

    // update ui
    private void do_update() {
      Qstn q = MyApp.survey.list.get(sssn.curr());

      // qstn title
			TextView tv_title = (TextView)getView().findViewById(R.id.tv_title);
      tv_title.setText(q.title);

			// show qstn num/total
			TextView tv = (TextView)getView().findViewById(R.id.tv_num);
			tv.setText(sssn.curr()+1+ "/" + MyApp.survey.list.size());

      /* TODO. no mul yet.
      // checkbox for multiple, radiobox oterwise
      */
      {
        // clear radio group
        int count = rg.getChildCount();
        if (count > 0) {
          for (int i=count-1; i>=0; i--) {
            rg.removeViewAt(i);
          }
        }
        //
        // System.out.println("Create radios: sssn.aswr["+sssn.curr()+"]: "+sssn.aswr[sssn.curr()]);
        // create radio
        rg = (RadioGroup)getView().findViewById(R.id.rg);
        for(int i=0; i<q.opts.length; i++) {
          int id = i+10000;
          // System.out.println("  new radio["+i+"]: "+ id+ ", str: "+q.opts[i]);
          RadioButton rb = new RadioButton(getContext());
          rb.setId(id);
          rb.setText(q.opts[i]);
          rb.setTextSize(30);
          // selected
          if (sssn.aswr[sssn.curr()] == i) {
             // System.out.println("  checked radio: "+i);
             rb.setChecked(true);
          }
          //
          rb.setOnCheckedChangeListener(this);
          rg.addView(rb);
        }
      } // radio


      // disable btn prev 
      Button btn_prev = (Button)getView().findViewById(R.id.btn_prev);
      if (sssn.curr() == 0) {
        // disable prev
        btn_prev.setClickable(false);
      }
      else
        btn_prev.setClickable(true);

      // btn next or finish
      Button btn_next = (Button)getView().findViewById(R.id.btn_next);
      if (sssn.curr() >= MyApp.survey.list.size()-1) {
        // next -> finish
        btn_next.setText("Finish");
      }
      else
        btn_next.setText("Next");
    } // end of do_update

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
			// get arg
			String target_app_name = FragmentQuizArgs.fromBundle(getArguments()).getTargetName();
			// curr = FragmentQuizArgs.fromBundle(getArguments()).getCurrPos();

      // set title bar
      MainActivity main = (MainActivity)getHost();
      sssn = MyApp.get_sssn(target_app_name);

      // change app_name/title bar
      // main.setTitle(getString(R.string.app_name)+ ": " +sssn.name);
      main.setTitle(sssn.name);
     
      // radio group
      rg = (RadioGroup)getView().findViewById(R.id.rg);
			// btn prev
			Button btn_prev = (Button)getView().findViewById(R.id.btn_prev);
			btn_prev.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
            // prev
            System.out.println("prev ---: old qstn: "+sssn.curr()+ ", new qstn: "+(sssn.curr()-1));
            sssn.prev();
            do_update();
					}
			});

			// btn next
			Button btn_next = (Button)getView().findViewById(R.id.btn_next);
			btn_next.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
            try {
              if (sssn.curr() < MyApp.survey.list.size()-1) {
                // next
                System.out.println("next ---: old qstn: "+sssn.curr()+ ", new qstn: "+(sssn.curr()+1));
                sssn.next();
                do_update();
              } else { 
                System.out.println("finish ---");
                // finish
                sssn.finish();
                // nav -> list
                NavDirections action = FragmentQuizDirections.actionFragmentQuizToFragmentList();
                Navigation.findNavController(getView()).navigate(action);
              }
            } catch (Exception _e) {
              System.out.println(_e);
            }
					}
			});

      // update UI to show curr qstn
      do_update();
	} // onViewCreated

	@Override
	public void onCheckedChanged(CompoundButton _button, boolean _isChecked) {
    if (!_isChecked)
      return;

    RadioButton rb = (RadioButton)_button;
    int id = rb.getId();
    String txt = rb.getText().toString();
    boolean b_chkd = rb.isChecked();
    // System.out.println("radio checked changed: " +id+ ", txt: "+txt+ ", checked: "+b_chkd);
     
    /* XXX somehow this returns wrong ID XXX
    int id = rg.getCheckedRadioButtonId();
    {
      RadioButton chkd_rb = (RadioButton) getView().findViewById(id);
      String txt = chkd_rb.getText().toString();
      boolean b_chkd = chkd_rb.isChecked();
      System.out.println("new checked radio: " +id+ ", txt: "+txt+ ", checked: "+b_chkd);
    }
    for (int i=0; i<3; i++) {
      RadioButton rb = (RadioButton) getView().findViewById(10000+i);
      String txt = rb.getText().toString();
      boolean b_chkd = rb.isChecked();
      System.out.println("rb[" +i+ "], txt: "+txt+ ", checked: "+b_chkd);
    }
    */

    int chkd = id-10000;
    if (_isChecked)
    try {
      // only if changed
      if (chkd>=0 && chkd<MyApp.survey.list.get(sssn.curr()).opts.length && sssn.aswr[sssn.curr()] != chkd) {
        // give aswr
        // System.out.println("give aswr to qstn: "+sssn.curr()+", old aswr: "+sssn.aswr[sssn.curr()]+ ", new aswr: "+chkd);
        sssn.give_aswr(sssn.curr(), chkd);
        // sssn.print();
      }
    } catch (Exception _e) {
      System.out.println(_e);
    }
  }
}

