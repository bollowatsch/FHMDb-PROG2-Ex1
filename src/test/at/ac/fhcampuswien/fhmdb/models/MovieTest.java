package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {
    private static final MovieBuilder mb = new MovieBuilder();

    @Test
    public void test_movie_without_genres_returns_empty_string_for_UI() {
        Movie movieWithoutGenres = mb.setId("").setTitle("").setGenres(new ArrayList<>()).build();

        String expected = "";
        String actual = movieWithoutGenres.getGenresAsString();

        assertEquals(expected, actual);
    }

    @Test
    public void test_movie_with_one_genre_returns_genre_as_string_for_UI() {
        ArrayList<Genre> singleGenreList = new ArrayList<>();
        singleGenreList.add(Genre.ADVENTURE);
        Movie movieWithSingleGenre = mb.setId("").setTitle("").setGenres(singleGenreList).build();

        String expected = "ADVENTURE";
        String actual = movieWithSingleGenre.getGenresAsString();

        assertEquals(expected, actual);
    }

    @Test
    public void test_movie_with_multiple_genres_returns_comma_and_space_separated_string_for_UI() {
        ArrayList<Genre> multipleGenreList = new ArrayList<>();
        multipleGenreList.add(Genre.ADVENTURE);
        multipleGenreList.add(Genre.DRAMA);
        multipleGenreList.add(Genre.BIOGRAPHY);
        Movie movieWithSingleGenre = mb.setId("").setTitle("").setGenres(multipleGenreList).build();

        String expected = "ADVENTURE, BIOGRAPHY, DRAMA";
        String actual = movieWithSingleGenre.getGenresAsString();

        assertEquals(expected, actual);
    }
}