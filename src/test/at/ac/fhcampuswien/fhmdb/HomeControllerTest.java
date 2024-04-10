package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {
    private static final HomeController hc = new HomeController();


    @Test
    public void test_sort_empty_MovieList_returns_empty_List() {
        // Arrange
        ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();
        ObservableList<Movie> expected = FXCollections.observableArrayList();

        // Act
        ObservableList<Movie> actual = hc.sortAscendingByTitle(movieObservableList);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_multiple_values_MovieList_ascending() {
        // Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList(new Movie("A"), new Movie("C"), new Movie("B"));
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("A"), new Movie("B"), new Movie("C"));

        // Act
        ObservableList<Movie> actual = hc.sortAscendingByTitle(movies);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_multiple_values_MovieList_descending() {
        // Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList(new Movie("A"), new Movie("C"), new Movie("B"));
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("C"), new Movie("B"), new Movie("A"));

        // Act
        ObservableList<Movie> actual = hc.sortDescendingByTitle(movies);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_single_value_MovieList_ascending_returns_single_value_list() {
        // Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList(new Movie("B"));
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("B"));

        // Act
        ObservableList<Movie> actual = hc.sortAscendingByTitle(movies);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_single_value_MovieList_descending_returns_single_value_list() {
        // Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList(new Movie("A"));
        ObservableList<Movie> expected = FXCollections.observableArrayList(new Movie("A"));

        // Act
        ObservableList<Movie> actual = hc.sortDescendingByTitle(movies);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_filter_by_genre() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(new Movie("12th fail", "The real-life story of IPS Officer Manoj Kumar Sharma and IRS Officer Shraddha Joshi.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.BIOGRAPHY))));
        expected.add(new Movie("Schindler's List", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.HISTORY, Genre.BIOGRAPHY))));

        //Act
        ObservableList<Movie> actual = hc.filterByGenre(expected, Genre.BIOGRAPHY);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_filter_by_multiple_genres() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(new Movie("The Lord of the Rings: The Return of the King", "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE))));
        expected.add(new Movie("The Lord of the Rings: The Fellowship of the Ring\n", "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE))));
        expected.add(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", new ArrayList<>(Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION))));
        expected.add(new Movie("The Lord of the Rings: The Two Towers", "While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE))));
        expected.add(new Movie("The Good, the Bad and the Ugly", "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery", new ArrayList<>(Arrays.asList(Genre.ADVENTURE, Genre.WESTERN))));

        //Act
        ObservableList<Movie> actual = hc.filterByGenre(expected, Genre.ADVENTURE); //should be 5 movies

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_case_sensitive_query_matches_title_returns_single_movie() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));

        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "Shaw");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_case_insensitive_query_matches_title_returns_single_movie() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));

        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "shaw");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_case_sensitive_query_matches_title_returns_multiple_movies() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(new Movie("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        expected.add(new Movie("The Godfather Part II", "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));

        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "God");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_query_matches_title_and_description_returns_multiple_movies() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(new Movie("The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", new ArrayList<>(List.of(Genre.DRAMA))));
        expected.add(new Movie("Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME))));
        expected.add(new Movie("The Lord of the Rings: The Two Towers", "While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE))));
        expected.add(new Movie("The Good, the Bad and the Ugly", "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery", new ArrayList<>(Arrays.asList(Genre.ADVENTURE, Genre.WESTERN))));

        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "two");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_filtering_by_genre_and_query() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", new ArrayList<>(Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION))));

        //Act
        ObservableList<Movie> actual = hc.filterByQueryAndGenre(expected, "pt", Genre.ADVENTURE);

        //Assert
        assertEquals(expected, actual);
    }

    /*  Unnecessary because of redesign -> no Genre.ALL anymore

    @Test
    public void test_filter_by_genre_ALL() {
        // Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(new Movie("Dummy1", "Dummy movie", new ArrayList<>(Arrays.asList(Genre.HISTORY, Genre.SCIENCE_FICTION))));
        expected.add(new Movie("Dummy2", "Dummy movie", new ArrayList<>(Arrays.asList(Genre.CRIME, Genre.THRILLER))));
        expected.add(new Movie("Dummy3", "Dummy movie", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.WESTERN))));

        //Act
        ObservableList<Movie> actual = hc.filterByGenre(expected, Genre.ALL);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getLongestMovieTitle_with_given_set_of_movies_returns_zero_when_no_titles_found() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(new Movie("", "Dummy movie", new ArrayList<>(List.of(Genre.ALL))));

        //Act
        int actual = hc.getLongestMovieTitle(movies);
        int expected = movies.get(0).getTitle().length();

        //Assert
        assertEquals(expected, actual);
    }
     */

    @Test
    public void test_getLongestMovieTitle_returns_correct_length_of_longest_title() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.THRILLER))));
        movies.add(new Movie("Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", new ArrayList<>(Arrays.asList(Genre.ADVENTURE, Genre.COMEDY))));
        movies.add(new Movie("Puss in Boots", "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.", new ArrayList<>(Arrays.asList(Genre.COMEDY, Genre.FAMILY))));

        //Act
        int actual = hc.getLongestMovieTitle(movies);
        int expected = movies.get(2).getTitle().length(); //Puss in Boots

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getLongestMovieTitle_with_same_length_titles() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.THRILLER))));
        movies.add(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.THRILLER))));

        //Act
        int actual = hc.getLongestMovieTitle(movies);
        int expected = movies.get(0).getTitle().length();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_countMoviesFrom_director_present_in_all_movies() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(new Movie("1", "Movie 1", new ArrayList<>(), 2020, "Description 1", "", 120, new String[]{"Director A"}, new String[]{"Writer A"}, new String[]{"Actor A"}, 7.5));
        movies.add(new Movie("2", "Movie 2", new ArrayList<>(), 2021, "Description 2", "", 110, new String[]{"Director A"}, new String[]{"Writer B"}, new String[]{"Actor B"}, 8.0));
        movies.add(new Movie("3", "Movie 3", new ArrayList<>(), 2019, "Description 3", "", 100, new String[]{"Director A"}, new String[]{"Writer C"}, new String[]{"Actor C"}, 6.5));

        //Act
        long actual = hc.countMoviesFrom(movies, "Director A");
        long expected = 3;

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_countMoviesFrom_director_present_in_some_movies() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(new Movie("1", "Movie 1", new ArrayList<>(), 2020, "Description 1", "", 120, new String[]{"Director A"}, new String[]{"Writer A"}, new String[]{"Actor A"}, 7.5));
        movies.add(new Movie("2", "Movie 2", new ArrayList<>(), 2021, "Description 2", "", 110, new String[]{"Director B"}, new String[]{"Writer B"}, new String[]{"Actor B"}, 8.0));
        movies.add(new Movie("3", "Movie 3", new ArrayList<>(), 2019, "Description 3", "", 100, new String[]{"Director A"}, new String[]{"Writer C"}, new String[]{"Actor C"}, 6.5));

        //Act
        long actual = hc.countMoviesFrom(movies, "Director A");
        long expected = 2;

        //Assert
        assertEquals(expected, actual);
        //assert hc.countMoviesFrom(movies, "Director A") == 2 : "Failed";
    }

    @Test
    public void test_countMoviesFrom_director_not_present_in_any_movies() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(new Movie("4", "Movie 4", new ArrayList<>(), 2018, "Description 4", "", 90, new String[]{"Director A"}, new String[]{"Writer D"}, new String[]{"Actor D"}, 6.0));
        movies.add(new Movie("5", "Movie 5", new ArrayList<>(), 2017, "Description 5", "", 80, new String[]{"Director B"}, new String[]{"Writer E"}, new String[]{"Actor E"}, 5.5));

        //Act
        long actual = hc.countMoviesFrom(movies, "Director C");
        long expected = 0;

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_countMoviesFrom_empty_movie_list_returns_0() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        //Act
        long actual = hc.countMoviesFrom(movies, "Director A");
        long expected = 0;

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getMoviesBetweenYears_movies_exists_between_years() {
        //Arrange
        Movie a = new Movie("1", "Movie 1", new ArrayList<>(), 2020, "Description 1", "", 120, new String[]{"Director A"}, new String[]{"Writer A"}, new String[]{"Actor A"}, 7.5);
        Movie b = new Movie("2", "Movie 2", new ArrayList<>(), 2019, "Description 2", "", 110, new String[]{"Director B"}, new String[]{"Writer B"}, new String[]{"Actor B"}, 8.0);
        Movie c = new Movie("3", "Movie 3", new ArrayList<>(), 2018, "Description 3", "", 100, new String[]{"Director C"}, new String[]{"Writer C"}, new String[]{"Actor C"}, 6.5);

        List<Movie> movies = new ArrayList<>();
        movies.add(a);
        movies.add(b);
        movies.add(c);

        List<Movie> expected = new ArrayList<>(2);
        expected.add(a);
        expected.add(b);

        //Act
        List<Movie> actual = hc.getMoviesBetweenYears(movies, 2019, 2020);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getMoviesBetweenYears_no_movies_between_years() {
        //Arrange
        Movie a = new Movie("1", "Movie 1", new ArrayList<>(), 2020, "Description 1", "", 120, new String[]{"Director A"}, new String[]{"Writer A"}, new String[]{"Actor A"}, 7.5);
        Movie b = new Movie("2", "Movie 2", new ArrayList<>(), 2019, "Description 2", "", 110, new String[]{"Director B"}, new String[]{"Writer B"}, new String[]{"Actor B"}, 8.0);
        Movie c = new Movie("3", "Movie 3", new ArrayList<>(), 2018, "Description 3", "", 100, new String[]{"Director C"}, new String[]{"Writer C"}, new String[]{"Actor C"}, 6.5);

        List<Movie> movies = new ArrayList<>();
        movies.add(a);
        movies.add(b);
        movies.add(c);

        //Act
        List<Movie> expected = new ArrayList<>();
        List<Movie> actual = hc.getMoviesBetweenYears(movies, 2000, 2001);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getMoviesBetweenYears_start_year_grater_then_end_year_returns_empty_list() {
        //Arrange
        Movie a = new Movie("1", "Movie 1", new ArrayList<>(), 2020, "Description 1", "", 120, new String[]{"Director A"}, new String[]{"Writer A"}, new String[]{"Actor A"}, 7.5);
        Movie b = new Movie("2", "Movie 2", new ArrayList<>(), 2019, "Description 2", "", 110, new String[]{"Director B"}, new String[]{"Writer B"}, new String[]{"Actor B"}, 8.0);
        Movie c = new Movie("3", "Movie 3", new ArrayList<>(), 2018, "Description 3", "", 100, new String[]{"Director C"}, new String[]{"Writer C"}, new String[]{"Actor C"}, 6.5);

        List<Movie> movies = new ArrayList<>();
        movies.add(a);
        movies.add(b);
        movies.add(c);

        List<Movie> expected = new ArrayList<>();

        //Act
        List<Movie> actual = hc.getMoviesBetweenYears(movies, 2020, 2019);

        //Assert
        assertEquals(expected, actual);
    }
}