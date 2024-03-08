package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    @Test
    public void test_sort_empty_MovieList_returns_empty_List() {
        // given
        ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();
        HomeController hc = new HomeController();

        // when
        ObservableList<Movie> actual = hc.sortAscendingByTitle(movieObservableList);

        //then
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_multiple_values_MovieList_ascending() {
        // given
        ObservableList<Movie> actual = FXCollections.observableArrayList(new Movie("A"), new Movie("C"), new Movie("B"));

        // when
        HomeController hc = new HomeController();
        actual = hc.sortAscendingByTitle(actual);

        //then
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("A"), new Movie("B"), new Movie("C"));
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_multiple_values_MovieList_descending() {
        // given
        ObservableList<Movie> actual = FXCollections.observableArrayList(new Movie("A"), new Movie("C"), new Movie("B"));

        // when
        HomeController hc = new HomeController();
        actual = hc.sortDescendingByTitle(actual);

        //then
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("C"), new Movie("B"), new Movie("A"));
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_single_value_MovieList_ascending_returns_single_value_list() {
        // given
        ObservableList<Movie> actual = FXCollections.observableArrayList(new Movie("B"));

        // when
        HomeController hc = new HomeController();
        actual = hc.sortAscendingByTitle(actual);

        //then
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("B"));
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_single_value_MovieList_descending_returns_single_value_list() {
        // given
        ObservableList<Movie> actual = FXCollections.observableArrayList(new Movie("A"));

        // when
        HomeController hc = new HomeController();
        actual = hc.sortDescendingByTitle(actual);

        //then
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("A"));
        assertEquals(actual, expected);
    }

    @Test
    public void test_filter_by_genre() {
        //Arrange
        HomeController hc = new HomeController();
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));
        movies.add(new Movie("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        movies.add(new Movie("12th fail", "The real-life story of IPS Officer Manoj Kumar Sharma and IRS Officer Shraddha Joshi.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.BIOGRAPHY))));

        //Act
        ObservableList<Movie> filteredList = hc.filterByGenre(Genre.BIOGRAPHY);

        //Assert
        assertEquals(movies.get(2), filteredList.get(0));
    }

    @Test
    public void test_filter_by_multiple_genres() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableList(Movie.initializeMovies());
        HomeController hc = new HomeController();
        ArrayList<Movie> actual = new ArrayList<>();

        //Act
        actual.add(movies.get(5));
        actual.add(movies.get(9));
        actual.add(movies.get(10));
        actual.add(movies.get(13));
        actual.add(movies.get(14));

        ObservableList<Movie> filteredList = hc.filterByGenre(Genre.ADVENTURE); //should be 5 movies

        //Assert
        assertEquals(actual, filteredList);
    }

    @Test
    public void test_case_sensitive_query_matches_title_returns_single_value_list(){
        //Arrange
        HomeController hc = new HomeController();
        ObservableList<Movie> actual = FXCollections.observableArrayList();

        //Act
        actual.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));

        ObservableList<Movie> expected = hc.filterByQuery("Shaw");

        //Assert
        assertEquals(expected,actual);
    }

    @Test
    public void test_case_insensitive_query_matches_title_returns_single_value_list(){
        //Arrange
        HomeController hc = new HomeController();
        ObservableList<Movie> actual = FXCollections.observableArrayList();

        //Act
        actual.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));

        ObservableList<Movie> expected = hc.filterByQuery("shaw");

        //Assert
        assertEquals(expected,actual);
    }

    @Test
    public void test_case_sensitive_query_matches_title_returns_multiple_value_list(){
        //Arrange
        HomeController hc = new HomeController();
        ObservableList<Movie> actual = FXCollections.observableArrayList();

        //Act
        actual.add(new Movie("The Godfather",
                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        actual.add(new Movie("The Godfather Part II",
                "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.",
                new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));

        ObservableList<Movie> expected = hc.filterByQuery("God");

        //Assert
        assertEquals(expected,actual);
    }

    @Test
    public void test_filtering_by_genre_and_query () {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableList(Movie.initializeMovies());
        HomeController hc = new HomeController();
        ArrayList<Movie> actual = new ArrayList<>();

        //Act
        actual.add(movies.get(10));     //Inception
        ObservableList<Movie> filteredList = hc.filterByQueryAndGenre("pt", Genre.ADVENTURE);

        //Assert
        assertEquals(actual, filteredList);
    }

}