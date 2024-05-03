package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.*;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXComboBox<Integer> ratingComboBox;

    @FXML
    public JFXComboBox<Integer> releaseYearField;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton clearBtn;

    @FXML
    public JFXButton switchView;

    private final MovieAPI movieAPI = new MovieAPI();
    private final String URL = "https://prog2.fh-campuswien.ac.at/movies?";

    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    private Database database = Database.getDatabase();
    private WatchlistRepository watchlistRepository = new WatchlistRepository();
    private MovieRepository movieRepository = new MovieRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize observableList and sort them asc.
        observableMovies.addAll(movieAPI.get());     // request movies from API
        observableMovies = sortAscendingByTitle(observableMovies);

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked)); // use custom cell factory to display data
        movieListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //FIXME: Hide horizontal scroll bar of listView

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9);

        releaseYearField.setPromptText("Filter by Release Year");
        releaseYearField.getItems().setAll(IntStream.rangeClosed(1940, 2024).boxed().sorted(Collections.reverseOrder()).collect(Collectors.toList()));

        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort ↓")) {
                observableMovies = sortAscendingByTitle(observableMovies);
            } else {
                observableMovies = sortDescendingByTitle(observableMovies);
            }
        });

        switchView.setOnAction(actionEvent -> {
            if (switchView.getText().equals("Watchlist")) {
                //TODO Display watchlist. (read from db using watchlistRepository)
                observableMovies.clear();


            } else {
                //TODO load from API.
                observableMovies.clear();
                observableMovies.addAll(movieAPI.get());
                observableMovies = sortAscendingByTitle(observableMovies);
            }
        });

        searchBtn.setOnAction(actionEvent -> filterMovieView());
        clearBtn.setOnAction(actionEvent -> {
            searchField.clear();
            genreComboBox.getSelectionModel().clearSelection();
            ratingComboBox.getSelectionModel().clearSelection();
            releaseYearField.getSelectionModel().clearSelection();
            filterMovieView();
        });
        searchField.setOnAction(actionEvent -> filterMovieView());
    }

    private final ClickEventHandler<Movie> onAddToWatchlistClicked = (clickedItem) -> {
        //TODO add movie to watchlist db


        System.out.printf("HomeController: Movie \"%s\" saved to watchlist.\n", clickedItem.getTitle());
    };

    public String getMostPopularActor(List<Movie> movies) {
        if (movies.stream().anyMatch(movie -> movie.getMainCast() == null)) {
            return null;
        }
        return movies.stream().flatMap(movie -> Arrays.stream(movie.getMainCast())).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream().map(Movie::getTitle).mapToInt(String::length).max().orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream().filter(movie -> movie != null && movie.getDirectors() != null && Arrays.asList(movie.getDirectors()).contains(director)).count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream().filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear).collect(Collectors.toList());
    }

    // TODO - add layer to separate UI from API & add tests
    public void filterMovieView() {
        URLBuilder urlBuilder = new URLBuilder();

        if (!searchField.getText().isBlank()) {
            urlBuilder.setQuery(searchField.getText());
        }
        if (!genreComboBox.getSelectionModel().isEmpty()) {
            urlBuilder.setGenre(genreComboBox.getSelectionModel().getSelectedItem().toString());
        }
        if (!releaseYearField.getSelectionModel().isEmpty()) {
            urlBuilder.setReleaseYear(releaseYearField.getSelectionModel().getSelectedItem().toString());
        }
        if (!ratingComboBox.getSelectionModel().isEmpty()) {
            urlBuilder.setRatingFrom(ratingComboBox.getSelectionModel().getSelectedItem().toString());
        }

        observableMovies.clear();
        observableMovies.addAll(movieAPI.get(urlBuilder.build()));
        observableMovies = sortAscendingByTitle(observableMovies);
    }

    public ObservableList<Movie> filterByGenre(ObservableList<Movie> movies, Genre genre) {
        return FXCollections.observableList(movies.stream().filter(movie -> movie.getGenres().contains(genre)).collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByQuery(ObservableList<Movie> movies, String query) {
        return FXCollections.observableList(movies.stream().filter(movie -> movie.getDescription().toLowerCase().contains(query.toLowerCase()) || movie.getTitle().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByQueryAndGenre(ObservableList<Movie> movies, String query, Genre genre) {
        return FXCollections.observableList(movies.stream().filter(movie -> movie.getDescription().toLowerCase().contains(query.toLowerCase()) || movie.getTitle().toLowerCase().contains(query.toLowerCase())).filter(movie -> movie.getGenres().contains(genre)).collect(Collectors.toList()));
    }

    public ObservableList<Movie> sortAscendingByTitle(ObservableList<Movie> observableMovies) {
        observableMovies.sort(Comparator.comparing(Movie::getTitle));
        setSortBtnText("Sort ↑");
        return observableMovies;
    }

    public ObservableList<Movie> sortDescendingByTitle(ObservableList<Movie> observableMovies) {
        observableMovies.sort(Comparator.comparing(Movie::getTitle));
        Collections.reverse(observableMovies);
        setSortBtnText("Sort ↓");
        return observableMovies;
    }

    public void setSortBtnText(String text) {
        if (sortBtn != null) {
            sortBtn.setText(text);
        }
    }
}