package com.skcnc.openmind.List;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Util.U;

import java.util.ArrayList;

public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.ItemViewHolder> {
    ArrayList<RecyclerCategoryItem> mItems;
    Bitmap bitmap;

    public RecyclerCategoryAdapter(ArrayList<RecyclerCategoryItem> mItems) {
        this.mItems = mItems;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.mBrand.setText(mItems.get(position).getBrand());
        holder.mLogo.setImageResource(mItems.get(position).getLogo());
        //이미지 uri로 비트맵 가져오기
        /*Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(mItems.get(position).getLogo());
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    holder.mLogo.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };*/
        //thread.start();
        try {

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_category, parent,false);
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

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    U.getUinstance().log("cardView Selected");
                }
            });
            /*mBrand = (TextView) itemView.findViewById(R.id.brand);
            mLogo = (ImageView) itemView.findViewById(R.id.logo);*/
            mLogo = (ImageView) itemView.findViewById(R.id.imageView);
            mBrand = (TextView) itemView.findViewById(R.id.nameTextView);
        }

/*        @Override
        public void onClick(View v) {
            U.getUinstance().log("check");

            boolean b = mItems.get(getAdapterPosition()).isChecked();
            mItems.get(getAdapterPosition()).setSelected(b);
        }*/

        /*@Override
        public void setChecked(boolean checked) {
            U.getUinstance().log("check");
            mCheck.setChecked(checked);
        }

        @Override
        public boolean isChecked() {
            return mCheck.isChecked();
        }

        @Override
        public void toggle() {
            setChecked(!isChecked());
        }*/
    }
}
