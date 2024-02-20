package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HomeControllerTest {

    @Test
    public void sort_empty_MovieList_returns_empty_List() {
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
    public void sort_multiple_values_MovieList_ascending() {
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
    public void sort_multiple_values_MovieList_descending() {
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
    public void sort_single_value_MovieList_ascending_returns_single_value_list() {
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
    public void sort_single_value_MovieList_descending_returns_single_value_list() {
        // given
        ObservableList<Movie> actual = FXCollections.observableArrayList(new Movie("A"));

        // when
        HomeController hc = new HomeController();
        actual = hc.sortDescendingByTitle(actual);

        //then
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("A"));
        assertEquals(actual, expected);
    }
}