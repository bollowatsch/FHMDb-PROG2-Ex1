package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {
    @Test
    public void test_movie_without_genres_returns_empty_string_for_UI() {
        Movie movieWithoutGenres = new Movie("", "", new ArrayList<>());

        String expected = "";
        String actual = movieWithoutGenres.getGenresAsString();

        assertEquals(expected, actual);
    }

    @Test
    public void test_movie_with_one_genre_returns_genre_as_string_for_UI() {
        ArrayList<Genre> singleGenreList = new ArrayList<>();
        singleGenreList.add(Genre.ADVENTURE);
        Movie movieWithSingleGenre = new Movie("", "", singleGenreList);

        String expected = "ADVENTURE";
        String actual = movieWithSingleGenre.getGenresAsString();

        assertEquals(expected, actual);
    }

    @Test
    public void test_movie_with_multiple_genres_returns_comma_and_space_separated_string_for_UI() {
        ArrayList<Genre> singleGenreList = new ArrayList<>();
        singleGenreList.add(Genre.ADVENTURE);
        singleGenreList.add(Genre.DRAMA);
        singleGenreList.add(Genre.BIOGRAPHY);
        Movie movieWithSingleGenre = new Movie("", "", singleGenreList);

        String expected = "ADVENTURE, BIOGRAPHY, DRAMA";
        String actual = movieWithSingleGenre.getGenresAsString();

        assertEquals(expected, actual);
    }
}