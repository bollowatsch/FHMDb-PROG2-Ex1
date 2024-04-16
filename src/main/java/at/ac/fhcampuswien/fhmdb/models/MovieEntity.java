package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class MovieEntity {
    long id;
    String apiId;
    String title;
    String description;
    String genres;
    int releaseYear;
    String imgUrl;
    int lengthInMinutes;
    double rating;

    String genresToString(List<Genre> genres) {
        //TODO convert given list to string containing comma separated values
        return null;
    }

    List<MovieEntity> fromMovies(List<Movie> movies) {
        //TODO convert list of movie objects (used for UI) to movie entities (used for DB)
        return null;
    }

    List<Movie> toMovies(List<MovieEntity> movieEntities) {
        //TODO convert list of movie entities (used for DB) to movie list of movie objects (used for UI)
        return null;
    }
}
