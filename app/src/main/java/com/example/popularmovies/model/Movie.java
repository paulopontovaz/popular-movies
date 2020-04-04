package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int id;
    private String originalTitle;
    private String imagePath;
    private String synopsis;
    private Double userRating;
    private String releaseDate;

    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";

    public Movie(int id, String originalTitle, String imagePath, String synopsis, Double userRating, String releaseDate) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.imagePath = imagePath;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.imagePath = in.readString();
        this.synopsis = in.readString();
        this.userRating = in.readDouble();
        this.releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() { return id; }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.imagePath);
        dest.writeString(this.synopsis);
        dest.writeDouble(this.userRating);
        dest.writeString(this.releaseDate);
    }
}
