package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class MovieBuilder {
    private String id = null;
    private String title = null;
    private List<Genre> genres = null;
    private Integer releaseYear = null;
    private String description = null;
    private String imgUrl = null;
    private Integer lengthInMinutes = null;
    private String[] directors = null;
    private String[] writers = null;
    private String[] mainCast = null;
    private Double rating = null;


    public Movie build() {
        if (id == null) throw new IllegalStateException("No ID was set!");
        if (title == null) throw new IllegalStateException("No Title was set!");
        if (releaseYear == null) releaseYear = 0;
        if (lengthInMinutes == null) lengthInMinutes = 0;
        if (rating == null) rating = 0.0;
        return new Movie(id, title, genres, releaseYear, description, imgUrl, lengthInMinutes, directors, writers, mainCast, rating);
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
