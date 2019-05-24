package com.skcnc.openmind.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Ui.JoinActivity;

public class FragmentGender extends Fragment {
    LinearLayout image1, image2, image_click1, image_click2;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender, container, false);

        image1 = (LinearLayout) view.findViewById(R.id.startButton1);  //선택할 레이아웃
        image_click1 = (LinearLayout) view.findViewById(R.id.image_click1);  //투명효과 레이아웃
        image2 = (LinearLayout) view.findViewById(R.id.startButton2);  //선택할 레이아웃
        image_click2 = (LinearLayout) view.findViewById(R.id.image_click2);  //투명효과 레이아웃

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_click1.setVisibility(View.VISIBLE);                                //투명효과
                ((JoinActivity)getActivity()).fragmentSwitch(2,"male");     //프래그먼트 교체
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_click2.setVisibility(View.VISIBLE);                               //투명효과
                ((JoinActivity)getActivity()).fragmentSwitch(2,"female");     //프래그먼트 교체
            }
        });

        return view;
    }
}
