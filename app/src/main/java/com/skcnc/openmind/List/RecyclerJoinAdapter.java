package com.skcnc.openmind.List;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Util.U;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class RecyclerJoinAdapter extends RecyclerView.Adapter<RecyclerJoinAdapter.ItemViewHolder> {
    ArrayList<RecyclerJoinItem> mItems;
    Bitmap bitmap;

    public RecyclerJoinAdapter(ArrayList<RecyclerJoinItem> mItems) {
        this.mItems = mItems;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.mBrand.setText(mItems.get(position).getBrand());

        /** 수정 **/
        //holder.mCheck.setChecked(mItems.get(position).);

        holder.mCheck.setOnCheckedChangeListener(null);
        holder.mCheck.setChecked(mItems.get(position).isChecked());
        holder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItems.get(position).setSelected(isChecked);
                if(isChecked){
                    U.getUinstance().addBrand(mItems.get(position).getBrand());
                }else{
                    U.getUinstance().removeBrand(mItems.get(position).getBrand());
                }
            }
        });

        //이미지 uri로 비트맵 가져오기
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(mItems.get(position).getLogo());
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
            holder.mLogo.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //커스텀 뷰홀더
    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView mBrand;
        private ImageView mLogo;
        private CheckBox mCheck;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mBrand = (TextView) itemView.findViewById(R.id.brand);
            mLogo = (ImageView) itemView.findViewById(R.id.logo);
            mCheck = (CheckBox) itemView.findViewById(R.id.check);
        }
    }
}
