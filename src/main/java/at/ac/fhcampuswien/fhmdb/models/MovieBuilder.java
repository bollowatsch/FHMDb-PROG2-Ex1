package at.ac.fhcampuswien.fhmdb.models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MovieBuilder {
    private String id;
    private String title;
    private List<Genre> genres;
    private Integer releaseYear;
    private String description;
    private String imgUrl;
    private Integer lengthInMinutes;
    private String[] directors;
    private String[] writers;
    private String[] mainCast;
    private Double rating;

    private void reset(){
        id = null;
        title = null;
        genres = null;
        releaseYear = null;
        description = null;
        imgUrl = null;
        lengthInMinutes = null;
        directors = null;
        writers = null;
        mainCast = null;
        rating = null;
    }

    public MovieBuilder(){
        reset();
    }
    public Movie build() {
        if (id == null) throw new IllegalStateException("No ID was set!");
        if (title == null) throw new IllegalStateException("No Title was set!");
        if (releaseYear == null) releaseYear = 0;
        if (lengthInMinutes == null) lengthInMinutes = 0;
        if (rating == null) rating = 0.0;
        Movie movie = new Movie(id, title, genres, releaseYear, description, imgUrl, lengthInMinutes, directors, writers, mainCast, rating);
        reset();
        return movie;
    }

    public MovieBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public MovieBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieBuilder setGenres(List<Genre> genres) {
        Collections.sort(genres);
        this.genres = genres;
        return this;
    }

    public MovieBuilder setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public MovieBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieBuilder setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public MovieBuilder setLengthInMinutes(Integer lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
        return this;
    }

    public MovieBuilder setDirectors(String[] directors) {
        this.directors = directors;
        return this;
    }

    public MovieBuilder setWriters(String[] writers) {
        this.writers = writers;
        return this;
    }

    public MovieBuilder setMainCast(String[] mainCast) {
        this.mainCast = mainCast;
        return this;
    }

    public MovieBuilder setRating(Double rating) {
        this.rating = rating;
        return this;
    }
}
