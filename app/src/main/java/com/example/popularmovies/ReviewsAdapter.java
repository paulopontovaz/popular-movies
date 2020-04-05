package com.example.popularmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.model.Review;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private List<Review> mReviewsList;

    ReviewsAdapter(List<Review> mReviewsList){
        this.mReviewsList = mReviewsList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review current = mReviewsList.get(position);

        holder.mContentTextView.setText(current.getContent());
        holder.mAuthorTextView.setText(current.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView mContentTextView;
        private TextView mAuthorTextView;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mContentTextView = itemView.findViewById(R.id.tv_content);
            mAuthorTextView = itemView.findViewById(R.id.tv_author);
        }
    }
}
