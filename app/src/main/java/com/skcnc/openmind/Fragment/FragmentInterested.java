package com.skcnc.openmind.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skcnc.openmind.List.RecyclerJoinAdapter;
import com.skcnc.openmind.List.RecyclerJoinItem;
import com.skcnc.openmind.R;
import com.skcnc.openmind.Sqlite.MyDBHandler;
import com.skcnc.openmind.Sqlite.TableBrand;
import com.skcnc.openmind.Ui.JoinActivity;
import com.skcnc.openmind.Util.U;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FragmentInterested extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerJoinAdapter adapter;
    ArrayList<RecyclerJoinItem> mItems = new ArrayList<>();
    MyDBHandler myDBHandler;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_interested, container, false);
        myDBHandler =  new MyDBHandler(getActivity(), "kiosk.db", null, 1);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        //layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerJoinAdapter(getActivity(), mItems);
        recyclerView.setAdapter(adapter);
        setData();

        Button btn_join_2 = (Button) view.findViewById(R.id.btn_join_2);
        Button btn_skip_2 = (Button) view.findViewById(R.id.btn_skip_2);

        btn_join_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((JoinActivity)getActivity()).fragmentSwitch(4, "join");
            }
        });

        btn_skip_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((JoinActivity)getActivity()).fragmentSwitch(4, "skip");
            }
        });
        return view;
    }
    //리사이클러뷰에 데이터 리스트 넣기
    private void setData(){
        int[][] arr = {{1,8}, {9,16}, {17,32},{33,38},{39,43},{44,47},{48,53},{54,65},{66,69}};

        Set<Integer> set = new HashSet<>();

        for(int i=0; i<arr.length;i++){
            int n1= arr[i][0];//하한값
            int n2 = arr[i][1];//상한값
            for(int j=0; j<3; j++){
                int value = 0;
                while(set.contains(value = (int)(Math.random()*(n2 - n1 + 1))+n1)){ }
                set.add(value);

                TableBrand tb = myDBHandler.getBrand(value);
                mItems.add(new RecyclerJoinItem(tb.getName(), tb.getImage()));
            }
        }

       /* mItems.add(new RecyclerJoinItem("공차",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
        mItems.add(new RecyclerJoinItem("셀렉토 커피",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
        mItems.add(new RecyclerJoinItem("맥도날드",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
        mItems.add(new RecyclerJoinItem("KFC",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
        mItems.add(new RecyclerJoinItem("스타벅스",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
        mItems.add(new RecyclerJoinItem("투썸플레이스",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
        mItems.add(new RecyclerJoinItem("미스사이공",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
        mItems.add(new RecyclerJoinItem("인천국제공항",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
        mItems.add(new RecyclerJoinItem("버거킹",  "https://s3.amazonaws.com/jsp-here.com/1.png"));
*/
        adapter.notifyDataSetChanged();
    }
}
