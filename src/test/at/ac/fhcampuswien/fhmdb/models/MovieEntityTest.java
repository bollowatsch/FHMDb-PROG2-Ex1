package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieEntityBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieEntityTest {
    private static final MovieEntityBuilder movieEntityBuilder = new MovieEntityBuilder();
    private static final MovieBuilder movieBuilder = new MovieBuilder();
    private static final MovieEntity movieEntity = movieEntityBuilder.setId(99).setTitle("Dummy Movie for static methods").build();
    private static final Movie movieA = movieBuilder.setId("").setTitle("Movie 1").setReleaseYear(2020).setDescription("Description 1").setLengthInMinutes(120).setGenres(new ArrayList<Genre>()).setRating(7.5).build();
    private static final Movie movieB = movieBuilder.setId("").setTitle("Movie 2").setReleaseYear(2021).setDescription("Description 2").setLengthInMinutes(110).setGenres(new ArrayList<Genre>()).setRating(8.0).build();
    private static final Movie movieC = movieBuilder.setId("").setTitle("Movie 3").setReleaseYear(2029).setDescription("Description 3").setLengthInMinutes(100).setGenres(new ArrayList<Genre>()).setRating(6.5).build();
    private static final MovieEntity movieEntityA = movieEntityBuilder.setTitle("Movie 1").setDescription("Description 1").setReleaseYear(2020).setLengthInMinutes(120).setRating(7.5).build();
    private static final MovieEntity movieEntityB = movieEntityBuilder.setTitle("Movie 2").setDescription("Description 2").setReleaseYear(2021).setLengthInMinutes(110).setRating(8.0).build();
    private static final MovieEntity movieEntityC = movieEntityBuilder.setTitle("Movie 3").setDescription("Description 3").setReleaseYear(2019).setLengthInMinutes(100).setRating(6.5).build();

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
        assertEquals(expected, actual);
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

    @Test
    public void test_toMovies_empty_movieEntityList_returns_empty_movieList() {
        //given
        List<MovieEntity> movieEntityList = new ArrayList<>();

        //when
        List<Movie> actual = movieEntity.toMovies(movieEntityList);
        List<Movie> expected = new ArrayList<Movie>();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void test_toMovies_single_movie_movieEntityList_returns_single_movie_movieList() {
        //given
        List<MovieEntity> movieEntityList = new ArrayList<>();
        movieEntityList.add(movieEntityA);

        //when
        List<Movie> actual = movieEntity.toMovies(movieEntityList);
        List<Movie> expected = new ArrayList<Movie>();
        expected.add(movieA);

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void test_toMovies_multiple_movies_movieEntityList_returns_multiple_movies_movieList() {
        //given
        List<MovieEntity> movieEntityList = new ArrayList<>();
        movieEntityList.add(movieEntityA);
        movieEntityList.add(movieEntityB);
        movieEntityList.add(movieEntityC);

        //when
        List<Movie> actual = movieEntity.toMovies(movieEntityList);
        List<Movie> expected = new ArrayList<Movie>();
        expected.add(movieA);
        expected.add(movieB);
        expected.add(movieC);

        //then
        assertEquals(actual, expected);
    }
}
