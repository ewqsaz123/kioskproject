package com.skcnc.openmind.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skcnc.openmind.R;
import java.util.ArrayList;

public class RecyclerJoinAdapter extends RecyclerView.Adapter<RecyclerJoinAdapter.ItemViewHolder> {
    ArrayList<RecyclerJoinItem> mItems;

    public RecyclerJoinAdapter(ArrayList<RecyclerJoinItem> mItems) {
        this.mItems = mItems;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.mBrand.setText(mItems.get(position).getBrand());

        /** 수정 **/
        //holder.mCheck.setChecked(mItems.get(position).);

        holder.mCheck.setOnCheckedChangeListener(null);
        holder.mCheck.setChecked(mItems.get(position).isChecked());
        holder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItems.get(position).setSelected(isChecked);
            }
        });

        /** 수정 **/
        holder.mLogo.setImageResource(R.drawable.images_mcdonalds);

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
