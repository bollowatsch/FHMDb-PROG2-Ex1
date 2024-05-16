package at.ac.fhcampuswien.fhmdb.models.exception;

public class MovieAPIException extends Exception {
    public MovieAPIException(String message) {
        super(message);
    }

    public MovieAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}