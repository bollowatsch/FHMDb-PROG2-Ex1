package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieEntityTest {
    private static MovieEntity movieEntity;
    @BeforeAll
    public static void init(){
        movieEntity = new MovieEntity();
    }

    @Test
    public void test_genresToString_empty_genre_list_returns_empty_string(){
        //Arrange
        List<Genre> genreList = new ArrayList<Genre>();

        //Act
        String expected = "";
        String actual = movieEntity.genresToString(genreList);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_genresToString_single_genre_returns_genre_as_string(){
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
    public void test_genresToString_multiple_genres_return_comma_separated_string(){
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
}
