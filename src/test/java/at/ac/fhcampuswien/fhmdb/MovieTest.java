package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {
    @Test
    public void movie_without_genres_returns_empty_string_for_UI(){
        Movie movieWithoutGenres = new Movie("", "", new ArrayList<Movie.Genre>());

        String expected = "";
        String actual = movieWithoutGenres.getGenresAsString();

        assertEquals(expected,actual);
    }

    @Test
    public void movie_with_one_genre_returns_genre_as_string_for_UI(){
        ArrayList<Movie.Genre> singleGenreList = new ArrayList<>();
        singleGenreList.add(Movie.Genre.ADVENTURE);
        Movie movieWithSingleGenre = new Movie("","", singleGenreList);

        String expected = "ADVENTURE";
        String actual = movieWithSingleGenre.getGenresAsString();

        assertEquals(expected,actual);
    }

    @Test
    public void movie_with_multiple_genres_returns_comma_and_space_separated_string_for_UI(){
        ArrayList<Movie.Genre> singleGenreList = new ArrayList<>();
        singleGenreList.add(Movie.Genre.ADVENTURE);
        singleGenreList.add(Movie.Genre.DRAMA);
        singleGenreList.add(Movie.Genre.BIOGRAPHY);
        Movie movieWithSingleGenre = new Movie("","", singleGenreList);

        String expected = "ADVENTURE, DRAMA, BIOGRAPHY";
        String actual = movieWithSingleGenre.getGenresAsString();

        assertEquals(expected,actual);
    }
}