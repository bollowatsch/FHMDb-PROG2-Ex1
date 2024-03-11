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

    private List<Movie> initializeMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));
        movies.add(new Movie("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        movies.add(new Movie("12th fail", "The real-life story of IPS Officer Manoj Kumar Sharma and IRS Officer Shraddha Joshi.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.BIOGRAPHY))));
        movies.add(new Movie("The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME, Genre.ACTION))));
        movies.add(new Movie("Schindler's List", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.HISTORY, Genre.BIOGRAPHY))));
        movies.add(new Movie("The Lord of the Rings: The Return of the King", "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE))));
        movies.add(new Movie("12 Angry Men", "The jury in a New York City murder trial is frustrated by a single member whose skeptical caution forces them to more carefully consider the evidence before jumping to a hasty verdict.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        movies.add(new Movie("The Godfather Part II", "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        movies.add(new Movie("Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        movies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring\n", "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE))));
        movies.add(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", new ArrayList<>(Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION))));
        movies.add(new Movie("Fight Club", "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.", new ArrayList<>(List.of(Genre.DRAMA))));
        movies.add(new Movie("Forrest Gump", "The history of the United States from the 1950s to the '70s unfolds from the perspective of an Alabama man with an IQ of 75, who yearns to be reunited with his childhood sweetheart.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ROMANCE))));
        movies.add(new Movie("The Lord of the Rings: The Two Towers", "While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE))));
        movies.add(new Movie("The Good, the Bad and the Ugly", "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery", new ArrayList<>(Arrays.asList(Genre.ADVENTURE, Genre.WESTERN))));
        return movies;
    }

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
        ObservableList<Movie> movies = FXCollections.observableArrayList(initializeMovies());
        ArrayList<Movie> actual = new ArrayList<>();

        //Act
        actual.add(movies.get(2));      //12th fail
        actual.add(movies.get(4));      //Schindlers List
        ObservableList<Movie> filteredList = hc.filterByGenre(Genre.BIOGRAPHY);

        //Assert
        assertEquals(actual, filteredList);
    }

    @Test
    public void test_filter_by_multiple_genres() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableList(initializeMovies());
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
    public void test_case_sensitive_query_matches_title_returns_single_movie() {
        //Arrange
        HomeController hc = new HomeController();
        ObservableList<Movie> actual = FXCollections.observableArrayList();

        //Act
        actual.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));

        ObservableList<Movie> expected = hc.filterByQuery("Shaw");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_case_insensitive_query_matches_title_returns_single_movie() {
        //Arrange
        HomeController hc = new HomeController();
        ObservableList<Movie> actual = FXCollections.observableArrayList();

        //Act
        actual.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));

        ObservableList<Movie> expected = hc.filterByQuery("shaw");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_case_sensitive_query_matches_title_returns_multiple_movies() {
        //Arrange
        HomeController hc = new HomeController();
        ObservableList<Movie> actual = FXCollections.observableArrayList();

        //Act
        actual.add(new Movie("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        actual.add(new Movie("The Godfather Part II", "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));

        ObservableList<Movie> expected = hc.filterByQuery("God");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_query_matches_title_and_description_returns_multiple_movies() {
        //Arrange
        HomeController hc = new HomeController();
        ObservableList<Movie> actual = FXCollections.observableArrayList();
        ObservableList<Movie> movies = FXCollections.observableList(initializeMovies());

        //Act
        actual.add(movies.get(0)); //The Shawshank Redemption
        actual.add(movies.get(8)); //Pulp Fiction
        actual.add(movies.get(13)); //The Lord of the Rings: The Two Towers
        actual.add(movies.get(14)); //The Good, the Bad and the Ugly

        ObservableList<Movie> expected = hc.filterByQuery("two");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_filtering_by_genre_and_query() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableList(initializeMovies());
        HomeController hc = new HomeController();
        ArrayList<Movie> actual = new ArrayList<>();

        //Act
        actual.add(movies.get(10));     //Inception
        ObservableList<Movie> filteredList = hc.filterByQueryAndGenre("pt", Genre.ADVENTURE);

        //Assert
        assertEquals(actual, filteredList);
    }

}