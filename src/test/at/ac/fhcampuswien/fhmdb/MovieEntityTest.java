package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieEntityTest {
    private static MovieEntity movieEntity;
    private static final Movie movieA = new Movie("1", "Movie 1", new ArrayList<>(), 2020, "Description 1", "", 120, new String[]{"Director A"}, new String[]{"Writer A"}, new String[]{"Actor A"}, 7.5);
    private static final Movie movieB = new Movie("2", "Movie 2", new ArrayList<>(), 2021, "Description 2", "", 110, new String[]{"Director B"}, new String[]{"Writer B"}, new String[]{"Actor B"}, 8.0);
    private static final Movie movieC = new Movie("3", "Movie 3", new ArrayList<>(), 2019, "Description 3", "", 100, new String[]{"Director A"}, new String[]{"Writer C"}, new String[]{"Actor C"}, 6.5);

    private static final MovieEntity movieEntityA = new MovieEntity(1, "", "Movie 1", "Description 1", "", 2020, "", 120, 7.5);
    private static final MovieEntity movieEntityB = new MovieEntity(2,"","Movie 2", "Description 2", "", 2021, "", 110,8.0);
    private static final MovieEntity movieEntityC = new MovieEntity(3,"","Movie 3", "Description 3", "", 2019, "", 100,6.5);
    @BeforeAll
    public static void init() {
        movieEntity = new MovieEntity();
    }

    @Test
    public void test_genresToString_empty_genre_list_returns_empty_string() {
        //Arrange
        List<Genre> genreList = new ArrayList<Genre>();

        //Act
        String expected = "";
        String actual = movieEntity.genresToString(genreList);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_genresToString_single_genre_returns_genre_as_string() {
        //Arrange
        List<Genre> genreList = new ArrayList<Genre>();
        genreList.add(Genre.ADVENTURE);
        //Act
        String expected = "ADVENTURE";
        String actual = movieEntity.genresToString(genreList);
        //Assert
        assertEquals(expected,actual);
    }

    @Test
    public void test_genresToString_multiple_genres_return_comma_separated_string() {
        //Arrange
        List<Genre> genreList = new ArrayList<Genre>();
        genreList.add(Genre.MUSICAL);
        genreList.add(Genre.ACTION);

        //Act
        String expected = "MUSICAL,ACTION";
        String actual = movieEntity.genresToString(genreList);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_fromMovies_empty_movieList_returns_empty_movieEntityList() {
        //given
        List<Movie> movieList = new ArrayList<Movie>();

        //when
        List<MovieEntity> actual = movieEntity.fromMovies(movieList);
        List<MovieEntity> expected = new ArrayList<MovieEntity>();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void test_fromMovies_single_movie_movieList_returns_single_movie_movieEntityList() {
        //given
        List<Movie> movieList = new ArrayList<Movie>();
        movieList.add(movieA);

        //when
        List<MovieEntity> actual = movieEntity.fromMovies(movieList);
        List<MovieEntity> expected = new ArrayList<MovieEntity>();
        expected.add(movieEntityA);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void test_fromMovies_multiple_movies_movieList_returns_multiple_movies_movieEntityList() {
        //given
        List<Movie> movieList = new ArrayList<Movie>();
        movieList.add(movieA);
        movieList.add(movieB);
        movieList.add(movieC);

        //when
        List<MovieEntity> actual = movieEntity.fromMovies(movieList);
        List<MovieEntity> expected = new ArrayList<MovieEntity>();
        expected.add(movieEntityA);
        expected.add(movieEntityB);
        expected.add(movieEntityC);

        //then
        assertEquals(actual, expected);
    }
}