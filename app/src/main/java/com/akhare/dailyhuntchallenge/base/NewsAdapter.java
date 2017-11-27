package com.akhare.dailyhuntchallenge.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhare.dailyhuntchallenge.Network.NewsData;
import com.akhare.dailyhuntchallenge.Network.VolleyNetwork;
import com.akhare.dailyhuntchallenge.R;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by akhare on 9/21/15.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsData> mDataset;
    private Context mActivity;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView;
        public NetworkImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.cell_description);
            mImageView = (NetworkImageView) v.findViewById(R.id.cell_imageView);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(NewsAdapter.this.mActivity, DetailNews.class);
            intent.putExtra("NewsData", mDataset.get(getAdapterPosition()));
            NewsAdapter.this.mActivity.startActivity(intent);
        }
    }

    public NewsAdapter(List<NewsData> myDataset, Activity activity) {
        mDataset = myDataset;
        mActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_cell, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(mDataset.get(position).getTitle());
        ImageLoader imageLoader = VolleyNetwork.getInstance(mActivity).getImageLoader();
        holder.mImageView.setImageUrl(mDataset.get(position).getImage(), imageLoader);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
