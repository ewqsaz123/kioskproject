package com.skcnc.openmind.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Util.U;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class RecyclerJoinAdapter extends RecyclerView.Adapter<RecyclerJoinAdapter.ItemViewHolder> {
    ArrayList<RecyclerJoinItem> mItems;
    Handler handler;
    Context context;

    public RecyclerJoinAdapter(Context context, ArrayList<RecyclerJoinItem> mItems) {
        this.mItems = mItems;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.mBrand.setText(mItems.get(position).getBrand());

        /** 수정 **/
        //holder.mCheck.setChecked(mItems.get(position).);
        //holder.setChecked(mItems.get(position).isChecked());
        holder.mCheck.setOnCheckedChangeListener(null);
        holder.mCheck.setChecked(mItems.get(position).isChecked());
        holder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItems.get(position).setSelected(isChecked);
                if(isChecked){
                    U.getUinstance().addBrand(mItems.get(position).getBrand());
                    U.getUinstance().log("tid added "+mItems.get(position).getBrand());
                }else{
                    U.getUinstance().removeBrand(mItems.get(position).getBrand());
                    U.getUinstance().log("tid removed "+mItems.get(position).getBrand());
                }
            }
        });

        //이미지뷰 변경
        AssetManager am = context.getResources().getAssets();
        InputStream is = null;
        try {
            is = am.open(mItems.get(position).getLogo());

            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.mLogo.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent,false);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    U.getUinstance().log("check");
                }
            });
            mCheck = (CheckBox) itemView.findViewById(R.id.checkBox);
            mLogo = (ImageView) itemView.findViewById(R.id.imageView);
            mBrand = (TextView) itemView.findViewById(R.id.nameTextView);
        }


    }
}
