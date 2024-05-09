package at.ac.fhcampuswien.fhmdb.models;

public class URLBuilder {
    final String URL = "https://prog2.fh-campuswien.ac.at/movies?";
    private String query;
    private Genre genre;
    private int releaseYear;
    private int ratingFrom;

    private void reset() {
        query = null;
        genre = null;
        releaseYear = 0;
        ratingFrom = 0;
    }

    public URLBuilder() {
        reset();
    }

    public String build() {
        StringBuilder url = new StringBuilder(URL);
        if (query != null) url.append("&query=").append(query);
        if (genre != null) url.append("&genre=").append(genre);
        if (releaseYear != 0) url.append("&releaseYear=").append(releaseYear);
        if (ratingFrom != 0) url.append("&ratingFrom=").append(ratingFrom);
        // delete unnecessary & after base url
        if (url.length() > URL.length()) url.deleteCharAt(URL.length());

        String requestURL = url.toString();
        reset();
        return requestURL;
    }


    public URLBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public URLBuilder setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public URLBuilder setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;

    }

    public URLBuilder setRatingFrom(int ratingFrom) {
        this.ratingFrom = ratingFrom;
        return this;
    }
}