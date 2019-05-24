package com.skcnc.openmind.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Ui.TutorialActivity;
import com.skcnc.openmind.Util.U;

import java.io.InputStream;
import java.util.ArrayList;

public class RecyclerRecommAdapter extends RecyclerView.Adapter<RecyclerRecommAdapter.ItemViewHolder> {
    ArrayList<RecyclerRecommItem> mItems;
    Bitmap bitmap;
    Context context;

    public RecyclerRecommAdapter(Context context, ArrayList<RecyclerRecommItem> mItems) {
        this.mItems = mItems;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.mBrand.setText(mItems.get(position).getBrand());

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_recom, parent,false);
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

        public ItemViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    U.getUinstance().log("cardView Selected");
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), TutorialActivity.class));
                }
            });
            mLogo = (ImageView) itemView.findViewById(R.id.imageView);
            mBrand = (TextView) itemView.findViewById(R.id.nameTextView);
        }

    }
}
