package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest {
    @Test
    public void test_get_genres_as_string_no_genres(){
        Movie movieWithoutGenres = new Movie("", "", new ArrayList<Movie.Genre>());

        String expected = "";
        String actual = movieWithoutGenres.getGenresAsString();

        assertEquals(expected,actual);
    }

    @Test
    public void test_get_genres_as_string_single_genre(){
        ArrayList<Movie.Genre> singleGenreList = new ArrayList<>();
        singleGenreList.add(Movie.Genre.ADVENTURE);
        Movie movieWithSingleGenre = new Movie("","", singleGenreList);

        String expected = "ADVENTURE";
        String actual = movieWithSingleGenre.getGenresAsString();

        assertEquals(expected,actual);
    }

    @Test
    public void test_get_genres_as_string_multiple_genres(){
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