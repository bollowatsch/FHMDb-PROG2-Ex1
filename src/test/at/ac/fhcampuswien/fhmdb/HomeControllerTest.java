package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {
    private static final HomeControllerFactory homeControllerFactory = new HomeControllerFactory();
    private static final HomeController hc = homeControllerFactory.getHomeController();
    private static final MovieBuilder mb = new MovieBuilder();

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
        ObservableList<Movie> movies = FXCollections.observableArrayList(
                mb.setId("0").setTitle("A").build(),
                mb.setId("2").setTitle("C").build(),
                mb.setId("1").setTitle("B").build());
        ObservableList<Movie> expected = FXCollections.observableArrayList(
                mb.setId("0").setTitle("A").build(),
                mb.setId("1").setTitle("B").build(),
                mb.setId("2").setTitle("C").build());

        // Act
        ObservableList<Movie> actual = hc.sortAscendingByTitle(movies);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_multiple_values_MovieList_descending() {
        // Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList(
                mb.setId("0").setTitle("A").build(),
                mb.setId("2").setTitle("C").build(),
                mb.setId("1").setTitle("B").build());
        ObservableList<Movie> expected = FXCollections.observableArrayList(
                mb.setId("2").setTitle("C").build(),
                mb.setId("1").setTitle("B").build(),
                mb.setId("0").setTitle("A").build());

        // Act
        ObservableList<Movie> actual = hc.sortDescendingByTitle(movies);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_single_value_MovieList_ascending_returns_single_value_list() {
        // Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList(mb.setId("0").setTitle("B").build());
        ObservableList<Movie> expected = FXCollections.observableArrayList(mb.setId("0").setTitle("B").build());

        // Act
        ObservableList<Movie> actual = hc.sortAscendingByTitle(movies);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_sort_single_value_MovieList_descending_returns_single_value_list() {
        // Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList(mb.setId("0").setTitle("A").build());
        ObservableList<Movie> expected = FXCollections.observableArrayList(mb.setId("0").setTitle("A").build());

        // Act
        ObservableList<Movie> actual = hc.sortDescendingByTitle(movies);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void test_filter_by_genre() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(mb.setId("")
                .setTitle("12th fail")
                .setDescription("The real-life story of IPS Officer Manoj Kumar Sharma and IRS Officer Shraddha Joshi.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.BIOGRAPHY)))
                .build());
        expected.add(mb.setId("")
                .setTitle("Schindler's List")
                .setDescription("In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.HISTORY, Genre.BIOGRAPHY)))
                .build());
        //Act
        ObservableList<Movie> actual = hc.filterByGenre(expected, Genre.BIOGRAPHY);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_filter_by_multiple_genres() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(mb.setId("0")
                .setTitle("The Lord of the Rings: The Return of the King")
                .setDescription("Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE)))
                .build());
        expected.add(mb.setId("1")
                .setTitle("The Lord of the Rings: The Fellowship of the Ring\n")
                .setDescription("A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE)))
                .build());
        expected.add(mb.setId("2")
                .setTitle("Inception")
                .setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION)))
                .build());
        expected.add(mb.setId("3")
                .setTitle("The Lord of the Rings: The Two Towers")
                .setDescription("While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE)))
                .build());
        expected.add(mb.setId("4")
                .setTitle("The Good, the Bad and the Ugly")
                .setDescription("A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.ADVENTURE, Genre.WESTERN)))
                .build());

        //Act
        ObservableList<Movie> actual = hc.filterByGenre(expected, Genre.ADVENTURE); //should be 5 movies

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_case_sensitive_query_matches_title_returns_single_movie() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(mb.setId("0")
                .setTitle("The Shawshank Redemption")
                .setDescription("Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.")
                .setGenres(new ArrayList<>(List.of(Genre.DRAMA)))
                .build());

        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "Shaw");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_case_insensitive_query_matches_title_returns_single_movie() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(mb.setId("0")
                .setTitle("The Shawshank Redemption")
                .setDescription("Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.")
                .setGenres(new ArrayList<>(List.of(Genre.DRAMA)))
                .build());
        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "shaw");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_case_sensitive_query_matches_title_returns_multiple_movies() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(mb.setId("")
                .setTitle("The Godfather")
                .setDescription("The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME)))
                .build());
        expected.add(mb.setId("")
                .setTitle("The Godfather Part II")
                .setDescription("The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME)))
                .build());
        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "God");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_query_matches_title_and_description_returns_multiple_movies() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(mb.setId("0")
                .setTitle("The Shawshank Redemption")
                .setDescription("Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.")
                .setGenres(new ArrayList<>(List.of(Genre.DRAMA)))
                .build());
        expected.add(mb.setId("")
                .setTitle("Pulp Fiction")
                .setDescription("The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME)))
                .build());
        expected.add(mb.setId("")
                .setTitle("The Lord of the Rings: The Two Towers")
                .setDescription("While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.ACTION, Genre.ADVENTURE)))
                .build());
        expected.add(mb.setId("")
                .setTitle("The Good, the Bad and the Ugly")
                .setDescription("A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.ADVENTURE, Genre.WESTERN)))
                .build());

        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "two");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_filtering_by_query_with_empty_list_returning_empty_list() {
        //Arrange
        ObservableList<Movie> expected = FXCollections.observableArrayList();

        //Act
        ObservableList<Movie> actual = hc.filterByQuery(expected, "pt");

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getMostPopularActor_returns_null_when_no_actors_found() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(mb.setId("")
                .setTitle("Inception")
                .setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION)))
                .build());
        movies.add(mb.setId("")
                .setTitle("Pulp Fiction")
                .setDescription("The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME)))
                .build());
        movies.add(mb.setId("")
                .setTitle("Puss in Boots")
                .setDescription("An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.COMEDY, Genre.FAMILY)))
                .build());

        //Act
        String actual = hc.getMostPopularActor(movies);
        String expected = null;

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getMostPopularActor_returns_most_popular_actor_when_actor_found() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(mb.setId("0")
                .setTitle("Inception")
                .setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.THRILLER)))
                .setMainCast(new String[]{"Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"})
                .build());

        movies.add(mb.setId("1")
                .setTitle("Once Upon a Time in Hollywood")
                .setDescription("A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywood's Golden Age in 1969 Los Angeles.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.COMEDY)))
                .setMainCast(new String[]{"Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"})
                .build());

        //Act
        String actual = hc.getMostPopularActor(movies);
        String expected = "Leonardo DiCaprio";

        //Assert
        assertEquals(expected, actual);

    }

    @Test
    public void test_getLongestMovieTitle_with_given_set_of_movies_returns_zero_when_no_titles_found() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(mb.setId("").setTitle("").build());

        //Act
        int actual = hc.getLongestMovieTitle(movies);
        int expected = movies.get(0).getTitle().length();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getLongestMovieTitle_returns_correct_length_of_longest_title() {
        //Arrange
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.add(mb.setId("")
                .setTitle("Inception")
                .setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION)))
                .build());
        movies.add(mb.setId("")
                .setTitle("Pulp Fiction")
                .setDescription("The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.DRAMA, Genre.CRIME)))
                .build());
        movies.add(mb.setId("")
                .setTitle("Puss in Boots")
                .setDescription("An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.COMEDY, Genre.FAMILY)))
                .build());
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
        movies.add(mb.setId("")
                .setTitle("Inception")
                .setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION)))
                .build());
        movies.add(mb.setId("")
                .setTitle("Inception")
                .setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .setGenres(new ArrayList<>(Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION)))
                .build());

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
        movies.add(mb.setId("1").setTitle("Movie 1").setDirectors(new String[]{"Director A"}).build());
        movies.add(mb.setId("2").setTitle("Movie 2").setDirectors(new String[]{"Director A"}).build());
        movies.add(mb.setId("3").setTitle("Movie 3").setDirectors(new String[]{"Director A"}).build());

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
        movies.add(mb.setId("1").setTitle("Movie 1").setDirectors(new String[]{"Director A"}).build());
        movies.add(mb.setId("2").setTitle("Movie 2").setDirectors(new String[]{"Director A"}).build());
        movies.add(mb.setId("3").setTitle("Movie 3").setDirectors(new String[]{"Director B"}).build());

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
        movies.add(mb.setId("1").setTitle("Movie 1").setDirectors(new String[]{"Director A"}).build());
        movies.add(mb.setId("2").setTitle("Movie 2").setDirectors(new String[]{"Director B"}).build());

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
        Movie a = mb.setId("1").setTitle("Movie 1").setReleaseYear(2020).build();
        Movie b = mb.setId("2").setTitle("Movie 2").setReleaseYear(2019).build();
        Movie c = mb.setId("3").setTitle("Movie 3").setReleaseYear(2018).build();

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
        Movie a = mb.setId("1").setTitle("Movie 1").setReleaseYear(2020).build();
        Movie b = mb.setId("2").setTitle("Movie 2").setReleaseYear(2019).build();
        Movie c = mb.setId("3").setTitle("Movie 3").setReleaseYear(2018).build();

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
        Movie a = mb.setId("1").setTitle("Movie 1").setReleaseYear(2020).build();
        Movie b = mb.setId("2").setTitle("Movie 2").setReleaseYear(2019).build();
        Movie c = mb.setId("3").setTitle("Movie 3").setReleaseYear(2018).build();

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