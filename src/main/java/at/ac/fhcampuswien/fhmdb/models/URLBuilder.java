package at.ac.fhcampuswien.fhmdb.models;

public class URLBuilder {
    private final String URL = "https://prog2.fh-campuswien.ac.at/movies?";
    private String query;
    private String genre;
    private String releaseYear;
    private String ratingFrom;

    private void reset() {
        query = null;
        genre = null;
        releaseYear = null;
        ratingFrom = null;
    }

    public URLBuilder() {reset();}

    public String build() {
        StringBuilder url = new StringBuilder(URL);
        if (query != null) url.append("&query=").append(query);
        if (genre != null) url.append("&genre=").append(genre);
        if (releaseYear != null) url.append("&releaseYear=").append(releaseYear);
        if (ratingFrom != null) url.append("&ratingFrom=").append(ratingFrom);
        // delete unnecessary & after base url
        if (url.length() > URL.length()) url.deleteCharAt(URL.length());

        String requestURL = url.toString();
        reset();
        return requestURL;
    }


    public void setQuery(String query) {
        this.query = query;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRatingFrom(String ratingFrom) {
        this.ratingFrom = ratingFrom;
    }
}