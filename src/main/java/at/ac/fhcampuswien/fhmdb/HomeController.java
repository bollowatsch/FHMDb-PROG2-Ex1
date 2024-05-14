package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton filterBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;
    //NOTE: Either load watchlist or standard movies

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
    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    private ObservableList<Movie> watchlist = FXCollections.observableArrayList();

    private ViewState state = ViewState.ALL;

    WatchlistRepository watchlistRepository;
    MovieRepository movieRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize movie DB
        movieRepository = new MovieRepository();
        //initialize observableList and sort them asc.
        updateObservableList(FXCollections.observableList(movieAPI.get()));   // request movies from API

        //cache movies from API call in DB
        cacheDB(observableMovies);
        // initialize UI stuff
        updateListView(observableMovies);           // set data of observable list to list view
        movieListView.setCellFactory(movieListView1 -> new MovieCell(onAddToWatchlistClicked)); // use custom cell factory to display data
        movieListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

        switchView.setOnAction(actionEvent -> switchViewState());

        filterBtn.setOnAction(actionEvent -> filter());

        clearBtn.setOnAction(actionEvent -> {
            clearFields();

            if (state == ViewState.ALL) {
                updateObservableList(FXCollections.observableList(movieAPI.get()));
            } else updateObservableList(watchlist);
        });

        searchField.setOnAction(actionEvent -> filter());

        //initialize Watchlist
        watchlistRepository = new WatchlistRepository();
        // add database values to observableList
        try {
            List<String> apiIds = watchlistRepository.getWatchlist().stream().map(WatchlistMovieEntity::getApiId).toList();
            watchlist.addAll(observableMovies.stream().filter(m -> apiIds.contains(m.getId())).toList());
        } catch (SQLException e) {
            //TODO exception handling
            // what happens, if apiId is not available in MovieAPI? -> edge case
            throw new RuntimeException(e);
        }
    }


    private void filter() {
        String query = searchField.getText().isBlank() ? null : searchField.getText();
        Genre genre = genreComboBox.getSelectionModel().isEmpty() ? null : genreComboBox.getSelectionModel().getSelectedItem();
        int releaseYear = releaseYearField.getSelectionModel().isEmpty() ? 0 : releaseYearField.getSelectionModel().getSelectedItem();
        int rating = ratingComboBox.getSelectionModel().isEmpty() ? 0 : ratingComboBox.getSelectionModel().getSelectedItem();

        if (state == ViewState.ALL) {
            //filter using API
            filterByAPI(query, genre, releaseYear, rating);
        } else {
            //filter locally
            filterLocally(query, genre, releaseYear, rating);
        }
    }

    private void cacheDB(List<Movie> movies) {
        try {
            movieRepository.removeAll();
            movieRepository.addAllMovies(movies);
        } catch (SQLException e) {
            String errorMessage = "Error caching movies in the database: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            //TODO better exception handling needed?
            //throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        searchField.clear();
        genreComboBox.getSelectionModel().clearSelection();
        ratingComboBox.getSelectionModel().clearSelection();
        releaseYearField.getSelectionModel().clearSelection();
    }

    private final ClickEventHandler<MovieCell> onAddToWatchlistClicked = (clickedItem) -> {
        Movie movie = clickedItem.getItem();
        WatchlistMovieEntity watchlistMovie = new WatchlistMovieEntity(movie.getId());
        if (state == ViewState.ALL) {
            if(watchlist.contains(clickedItem.getItem()))
                return;
            watchlist.add(clickedItem.getItem());
            try {
                watchlistRepository.addToWatchlist(watchlistMovie);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while trying to add the movie " + movie.getTitle() + "to the watchlist. PLease try again later!");
                alert.showAndWait();
                e.printStackTrace();
                //TODO more exception handling?
            }
            //TODO delete print statements or use logging
            //System.out.printf("HomeController: Added Movie \"%s\" to watchlist.\n", clickedItem.getItem().getTitle());
        } else {
            watchlist.remove(clickedItem.getItem());
            try {
                watchlistRepository.removeFromWatchlist(movie.getId());
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while trying to delete the movie " + movie.getTitle() + "to the watchlist. PLease try again later!");
                alert.showAndWait();
                e.printStackTrace();
                //TODO more exception handling?
            }
            //TODO delete print statements or use logging
            //System.out.printf("HomeController: Removed Movie \"%s\" to watchlist.\n", clickedItem.getItem().getTitle());

            //return to overview if last element of watchlist is removed.
            if (watchlist.isEmpty()) {
                state = ViewState.WATCHLIST;
                switchViewState();
            } else updateObservableList(watchlist);
        }
    };

    private void switchViewState() {
        clearFields();

        if (state == ViewState.ALL) {
            //Switch to Watchlist View
            updateObservableList(watchlist);
            state = ViewState.WATCHLIST;
            switchView.setText("Home");

        } else {
            //Switch to overview.
            updateObservableList(FXCollections.observableList(movieAPI.get()));
            state = ViewState.ALL;
            switchView.setText("Watchlist");
        }

    }

    private void updateObservableList(ObservableList<Movie> movies) {
        observableMovies.clear();
        observableMovies.addAll(movies);
        observableMovies = sortAscendingByTitle(observableMovies);
    }

    private void updateListView(ObservableList<Movie> movies) {
        movieListView.setItems(movies);
    }

    private void filterByAPI(String query, Genre genre, int releaseYear, int ratingFrom) {
        URLBuilder urlBuilder = new URLBuilder();
        urlBuilder.setQuery(query).setGenre(genre).setReleaseYear(releaseYear).setRatingFrom(ratingFrom);

        updateObservableList(FXCollections.observableList(movieAPI.get(urlBuilder.build())));
    }

    private void filterLocally(String query, Genre genre, int releaseYear, int ratingFrom) {
        ObservableList<Movie> filteredWatchlist = watchlist;

        if (query != null) filteredWatchlist = filterByQuery(filteredWatchlist, query);
        if (genre != null) filteredWatchlist = filterByGenre(filteredWatchlist, genre);
        if (releaseYear > 0) filteredWatchlist = filterByReleaseYear(filteredWatchlist, releaseYear);
        if (ratingFrom > 0) filteredWatchlist = filterByRatingFrom(filteredWatchlist, ratingFrom);

        updateObservableList(filteredWatchlist);
    }


    public String getMostPopularActor(List<Movie> movies) {
        if (movies.stream().anyMatch(movie -> movie.getMainCast() == null)) return null;
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

    public ObservableList<Movie> filterByQuery(ObservableList<Movie> movies, String query) {
        return FXCollections.observableList(movies.stream().filter(movie -> movie.getDescription().toLowerCase().contains(query.toLowerCase()) || movie.getTitle().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByGenre(ObservableList<Movie> movies, Genre genre) {
        return FXCollections.observableList(movies.stream().filter(movie -> movie.getGenres().contains(genre)).collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByReleaseYear(ObservableList<Movie> movies, int releaseYear) {
        return FXCollections.observableList(movies.stream().filter(movie -> movie.getReleaseYear() == releaseYear).collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByRatingFrom(ObservableList<Movie> movies, int ratingFrom) {
        return FXCollections.observableList(movies.stream().filter(movie -> movie.getRating() >= ratingFrom).collect(Collectors.toList()));
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