package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
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

    public long getId() {
        return id;
    }

    public String getApiId() {
        return apiId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }
    protected MovieEntity(Movie movie) {
        this.id = Long.parseLong(movie.getId());
        this.apiId = "";
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.genres = this.genresToString(movie.getGenres());
        this.releaseYear = movie.getReleaseYear();
        this.imgUrl = movie.getImgUrl();
        this.lengthInMinutes = movie.getLengthInMinutes();
        this.rating = movie.getRating();

    }

    protected MovieEntity(long id, String apiId, String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating){
        this.id = id;
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != this.getClass()){
            return false;
        }
        return this.id == ((MovieEntity) o).id;
    }
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

    public List<MovieEntity> fromMovies(List<Movie> movies) {
        ArrayList<MovieEntity> movieEntityList = new ArrayList<MovieEntity>();
        for(Movie movie : movies){
            movieEntityList.add(new MovieEntity(movie));
        }
        return movieEntityList;
    }

    public List<Movie> toMovies(List<MovieEntity> movieEntities) {
        ArrayList<Movie> movieList = new ArrayList<>();
        for(MovieEntity movieEntity : movieEntities){
            movieList.add(new Movie(movieEntity));
        }
        return movieList;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", apiId='" + apiId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genres='" + genres + '\'' +
                ", releaseYear=" + releaseYear +
                ", imgUrl='" + imgUrl + '\'' +
                ", lengthInMinutes=" + lengthInMinutes +
                ", rating=" + rating +
                '}';
    }
}
