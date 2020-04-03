package com.example.popularmovies;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private static final String TAG = "MoviesAdapter";

    private List<Movie> mMoviesList;
    final private ListItemClickListener mListItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Movie movie);
    }

    MoviesAdapter(List<Movie> mMoviesList, ListItemClickListener mListItemClickListener){
        this.mMoviesList = mMoviesList;
        this.mListItemClickListener = mListItemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie current = mMoviesList.get(position);

        holder.mTitleTextView.setText(current.getOriginalTitle());

        Picasso.get()
                .load(Uri.parse(current.getImagePath()))
                .into(holder.mThumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mThumbnailImageView;
        private TextView mTitleTextView;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mThumbnailImageView = itemView.findViewById(R.id.thumbnail_image_view);
            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListItemClickListener.onListItemClick(mMoviesList.get(position));
        }
    }
}
