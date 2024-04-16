package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class MovieEntity  extends WatchlistMovieEntity{
    long id;
    String apiId;
    String title;
    String description;
    String genres;
    int releaseYear;
    String imgUrl;
    int lengthInMinutes;
    double rating;

    public String genresToString(List<Genre> genres) {
        if(genres.isEmpty()){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(Genre genre : genres){
            sb.append(genre).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
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
