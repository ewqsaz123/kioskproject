package com.skcnc.openmind.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Ui.JoinActivity;

public class FragmentAge extends Fragment {

    EditText edit_age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_age, container, false);

        Button btn_skip = (Button) view.findViewById(R.id.btn_skip);
        Button btn_join = (Button) view.findViewById(R.id.btn_join);
        edit_age = (EditText) view.findViewById(R.id.edit_age);

        //건너뛰기 버튼 클릭
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((JoinActivity)getActivity()).fragmentSwitch(3, "skip");
            }
        });

        //확인 버튼 클릭
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((JoinActivity)getActivity()).fragmentSwitch(3, edit_age.getText().toString());
            }
        });


        return view;
    }
}
