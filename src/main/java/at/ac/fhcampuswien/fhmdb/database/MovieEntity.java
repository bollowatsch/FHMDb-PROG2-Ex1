package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieBuilder;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    long id;
    @DatabaseField()
    String apiId;
    @DatabaseField()
    String title;
    @DatabaseField()
    String description;
    @DatabaseField()
    String genres;
    @DatabaseField()
    int releaseYear;
    @DatabaseField()
    String imgUrl;
    @DatabaseField()
    int lengthInMinutes;
    @DatabaseField()
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
        this.apiId = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.genres = this.genresToString(movie.getGenres());
        this.releaseYear = movie.getReleaseYear();
        this.imgUrl = movie.getImgUrl();
        this.lengthInMinutes = movie.getLengthInMinutes();
        this.rating = movie.getRating();

    }

    protected MovieEntity(long id, String apiId, String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
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

    public MovieEntity() {
        // ORMLite needs a no-arg constructor
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        return this.id == ((MovieEntity) o).id;
    }

    public String genresToString(List<Genre> genres) {
        if (genres.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Genre genre : genres) {
            sb.append(genre).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static List<MovieEntity> fromMovies(List<Movie> movies) {
        ArrayList<MovieEntity> movieEntityList = new ArrayList<>();
        for (Movie movie : movies) {
            movieEntityList.add(new MovieEntity(movie));
        }
        return movieEntityList;
    }

    public static List<Movie> toMovies(List<MovieEntity> movieEntities) {
        MovieBuilder mb = new MovieBuilder();
        ArrayList<Movie> movieList = new ArrayList<>();

        for (MovieEntity movieEntity : movieEntities) {
            List<Genre> genres = new ArrayList<>();
            //BUG: Multiple Genres separated by ", " leads to crash. (Cover this with TestCases)
            for (String genre : movieEntity.getGenres().split(",")) {
                if (!genre.isEmpty()) {
                    genres.add(Genre.valueOf(genre));
                }
            }

            movieList.add(mb.setId(String.valueOf(movieEntity.getId()))
                    .setTitle(movieEntity.getTitle())
                    .setGenres(genres)
                    .setReleaseYear(movieEntity.getReleaseYear())
                    .setDescription(movieEntity.getDescription())
                    .setImgUrl(movieEntity.getImgUrl())
                    .setLengthInMinutes(movieEntity.getLengthInMinutes())
                    .setDirectors(new String[]{})
                    .setWriters(new String[]{})
                    .setMainCast(new String[]{})
                    .setRating(movieEntity.getRating())
                    .build());
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