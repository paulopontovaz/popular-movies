package com.example.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.model.Trailer;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
    private static final String TAG = "TrailersAdapter";

    private Context mContext;
    private List<Trailer> mTrailersList;

    TrailersAdapter(Context mContext, List<Trailer> mTrailersList){
        this.mContext = mContext;
        this.mTrailersList = mTrailersList;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer current = mTrailersList.get(position);

        holder.mTypeTextView.setText(current.getType());
        holder.mNameTextView.setText(current.getName());
        holder.mWatchTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trailerKey = current.getKey();

                if (!TextUtils.isEmpty(trailerKey)) {
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailerKey));

                    try {
                        mContext.startActivity(appIntent);
                    } catch (ActivityNotFoundException e) {
                        mContext.startActivity(webIntent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailersList.size();
    }

    static class TrailerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTypeTextView;
        private TextView mNameTextView;
        private Button mWatchTrailerButton;

        TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTypeTextView = itemView.findViewById(R.id.tv_type);
            mNameTextView = itemView.findViewById(R.id.tv_name);
            mWatchTrailerButton = itemView.findViewById(R.id.btn_watch_trailer);
        }
    }
}
