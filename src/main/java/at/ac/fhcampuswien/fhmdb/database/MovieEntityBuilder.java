package at.ac.fhcampuswien.fhmdb.database;

public class MovieEntityBuilder {
    long id;
    String apiId;
    String title;
    String description;
    String genres;
    int releaseYear;
    String imgUrl;
    int lengthInMinutes;
    double rating;

    private void reset() {
        id = 0;
        apiId = "";
        title = "";
        description = "";
        genres = "";
        releaseYear = 0;
        imgUrl = "";
        lengthInMinutes = 0;
        rating = 0.0;
    }

    public MovieEntityBuilder() {
        reset();
    }

    public MovieEntity build() {
        //if (id == 0) throw new IllegalStateException("No ID was set!");
        if (title.isBlank()) throw new IllegalStateException("No title was set!");
        MovieEntity movieEntity = new MovieEntity(id, apiId, title, description, genres, releaseYear, imgUrl, lengthInMinutes, rating);
        reset();
        return movieEntity;
    }

    public MovieEntityBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public MovieEntityBuilder setApiId(String apiId) {
        this.apiId = apiId;
        return this;
    }

    public MovieEntityBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieEntityBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieEntityBuilder setGenres(String genres) {
        this.genres = genres;
        return this;
    }

    public MovieEntityBuilder setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public MovieEntityBuilder setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public MovieEntityBuilder setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
        return this;
    }

    public MovieEntityBuilder setRating(double rating) {
        this.rating = rating;
        return this;
    }
}
