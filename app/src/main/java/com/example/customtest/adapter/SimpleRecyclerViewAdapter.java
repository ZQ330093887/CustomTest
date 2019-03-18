package com.example.customtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.customtest.R;
import com.example.customtest.vo.SimpleData;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 列表适配器
 * Created by ZQiong on 2017/9/7 13:55.
 */

public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> {

    private List<SimpleData.Song> mSongs;

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;
        final TextView mTextView;

        ViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.avatar);
            mTextView = view.findViewById(android.R.id.text1);
        }
    }

    public SimpleRecyclerViewAdapter() {
        if (null == mSongs) {
            mSongs = new ArrayList<>();
        }
    }

    public void loadMore() {
        mSongs.addAll(SimpleData.getRandomSonList(10));
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(mSongs.get(position).songName);
        holder.mTextView.setOnClickListener(v -> {
            //
            callBackListener.onItemClick(v);
        });
        Glide.with(holder.mImageView.getContext())
                .load(mSongs.get(position).drawableResID)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }




    /**********************接口部分*************************/
    private OnClickCallBackListener callBackListener;

    public void setCallBackListener(OnClickCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public interface OnClickCallBackListener {
        void onItemClick(View v);
    }


}
