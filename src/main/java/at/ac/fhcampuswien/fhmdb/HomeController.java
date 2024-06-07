package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.*;
import at.ac.fhcampuswien.fhmdb.models.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.exception.MovieAPIException;
import at.ac.fhcampuswien.fhmdb.models.observerPattern.Observer;
import at.ac.fhcampuswien.fhmdb.models.statePattern.StateContext;
import at.ac.fhcampuswien.fhmdb.models.statePattern.UnsortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static at.ac.fhcampuswien.fhmdb.ui.PopUp.createPopup;

public class HomeController implements Initializable, Observer {
    @FXML
    public JFXButton filterBtn;

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

    private MovieAPI movieAPI;
    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    private final ObservableList<Movie> watchlist = FXCollections.observableArrayList();

    private ViewState state = ViewState.ALL;

    private WatchlistRepository watchlistRepository;
    private MovieRepository movieRepository;
    private StateContext stateContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize movie DB

        try {
            movieAPI = MovieAPI.getMovieAPI();
            movieRepository = MovieRepository.getMovieRepository();
        } catch (DatabaseException | MovieAPIException e) {
            createPopup("Following error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        stateContext = new StateContext();
        getMovies();                            //initialize observableList
        cacheDB(observableMovies);              //cache movies from API call in DB
        updateListView(observableMovies);       // set data of observable list to list view
        initializeComponents();

        //initialize Watchlist
        try {
            watchlistRepository = WatchlistRepository.getWatchlistRepositoryInstance();
            watchlistRepository.addObserver(this);
        } catch (DatabaseException e) {
            createPopup(e.getMessage(), Alert.AlertType.ERROR);
        }
        // add database values to observableList
        try {
            List<String> apiIds = watchlistRepository.getWatchlist().stream().map(WatchlistMovieEntity::getApiId).toList();
            watchlist.addAll(observableMovies.stream().filter(m -> apiIds.contains(m.getId())).toList());
        } catch (DatabaseException e) {
            createPopup("Watchlist couldn't be fetched from the database. " + e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void initializeComponents() {
        movieListView.setCellFactory(movieListView1 -> new MovieCell(onAddToWatchlistClicked)); // use custom cell factory to display data
        movieListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9);

        releaseYearField.setPromptText("Filter by Release Year");
        releaseYearField.getItems().setAll(IntStream.rangeClosed(1940, 2024).boxed().sorted(Collections.reverseOrder()).collect(Collectors.toList()));

        switchView.setOnAction(actionEvent -> switchViewState());
        searchField.setOnAction(actionEvent -> filter());

        filterBtn.setOnAction(actionEvent -> filter());
        setSortBtnText(stateContext.getCurrentState().getText());
        sortBtn.setOnAction(actionEvent -> {

            observableMovies = sortByTitle(observableMovies);
            /*
            if (sortBtn.getText().equals("Sort ↓")) {
                observableMovies = sortAscendingByTitle(observableMovies);
            } else {
                observableMovies = sortDescendingByTitle(observableMovies);
            }

             */
        });
        clearBtn.setOnAction(actionEvent -> {
            clearFields();

            if (state == ViewState.ALL) {
                getMovies();
            } else updateObservableList(watchlist);
        });
    }

    public ObservableList<Movie> sortByTitle(ObservableList<Movie> observableMovies) {
        ObservableList<Movie> moviesSorted = stateContext.sort(observableMovies);

        stateContext.setState(stateContext.getCurrentState().getNextState());
        setSortBtnText(stateContext.getCurrentState().getText());

        return moviesSorted;
    }

    private void getMovies() {
        try {
            updateObservableList(FXCollections.observableList(movieAPI.get()));
            stateContext.setState(new UnsortedState());
            stateContext.sort(observableMovies);            //in beginning movies are not sorted
            return;
        } catch (MovieAPIException e) {
            createPopup(e.getMessage() + System.lineSeparator() + "Movies are loaded from local database.", Alert.AlertType.WARNING);
        }
        try {
            updateObservableList(FXCollections.observableList(MovieEntity.toMovies(movieRepository.getAllMovies())));
        } catch (DatabaseException e) {
            createPopup("Movies couldn't be fetched from the database. " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private final ClickEventHandler<MovieCell> onAddToWatchlistClicked = (clickedItem) -> {
        Movie movie = clickedItem.getItem();
        WatchlistMovieEntity watchlistMovie = new WatchlistMovieEntity(movie.getId());
        if (state == ViewState.ALL) {
            if (!watchlist.contains(movie)) {
                watchlist.add(movie);
            }

            try {
                watchlistRepository.addToWatchlist(watchlistMovie);
            } catch (DatabaseException e) {
                createPopup("An error occurred while trying to add the movie " + movie.getTitle() + "to the watchlist. PLease try again later!", Alert.AlertType.ERROR);
            }
        } else {
            watchlist.remove(clickedItem.getItem());
            try {
                watchlistRepository.removeFromWatchlist(movie.getId());
            } catch (DatabaseException e) {
                createPopup("An error occurred while trying to delete the movie " + movie.getTitle() + "from the watchlist. PLease try again later!", Alert.AlertType.ERROR);
            }

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
            getMovies();
            state = ViewState.ALL;
            switchView.setText("Watchlist");
        }
    }

    private void cacheDB(List<Movie> movies) {
        try {
            movieRepository.removeAll();
            movieRepository.addAllMovies(movies);
        } catch (DatabaseException e) {
            createPopup("Error caching movies in the database: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void filter() {
        String query = searchField.getText().isBlank() ? null : searchField.getText();
        Genre genre = genreComboBox.getSelectionModel().isEmpty() ? null : genreComboBox.getSelectionModel().getSelectedItem();
        int releaseYear = releaseYearField.getSelectionModel().isEmpty() ? 0 : releaseYearField.getSelectionModel().getSelectedItem();
        int rating = ratingComboBox.getSelectionModel().isEmpty() ? 0 : ratingComboBox.getSelectionModel().getSelectedItem();

        if (state == ViewState.ALL) {
            filterByAPI(query, genre, releaseYear, rating);     //filter using API
        } else {
            filterLocally(query, genre, releaseYear, rating);   //filter locally
        }
    }

    private void filterByAPI(String query, Genre genre, int releaseYear, int ratingFrom) {
        URLBuilder urlBuilder = new URLBuilder();
        urlBuilder.setQuery(query).setGenre(genre).setReleaseYear(releaseYear).setRatingFrom(ratingFrom);

        try {
            updateObservableList(FXCollections.observableList(movieAPI.get(urlBuilder.build())));
        } catch (MovieAPIException e) {
            createPopup("Something went wrong while filtering: " + e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void filterLocally(String query, Genre genre, int releaseYear, int ratingFrom) {
        ObservableList<Movie> filteredWatchlist = watchlist;

        if (query != null) filteredWatchlist = filterByQuery(filteredWatchlist, query);
        if (genre != null) filteredWatchlist = filterByGenre(filteredWatchlist, genre);
        if (releaseYear > 0) filteredWatchlist = filterByReleaseYear(filteredWatchlist, releaseYear);
        if (ratingFrom > 0) filteredWatchlist = filterByRatingFrom(filteredWatchlist, ratingFrom);

        updateObservableList(filteredWatchlist);
    }

    private void clearFields() {
        searchField.clear();
        genreComboBox.getSelectionModel().clearSelection();
        ratingComboBox.getSelectionModel().clearSelection();
        releaseYearField.getSelectionModel().clearSelection();
    }

    private void updateObservableList(ObservableList<Movie> movies) {
        observableMovies.clear();
        observableMovies.addAll(movies);
        observableMovies = sortByTitle(observableMovies);
    }

    private void updateListView(ObservableList<Movie> movies) {
        movieListView.setItems(movies);
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

    public void setSortBtnText(String text) {
        if (sortBtn != null) {sortBtn.setText(text);}
    }

    @Override
    public void update(String message) {
        createPopup(message, Alert.AlertType.INFORMATION);
    }
}