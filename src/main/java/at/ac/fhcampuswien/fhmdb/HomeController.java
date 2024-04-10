package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
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

    private final MovieAPI movieAPI = new MovieAPI();

    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize observableList and sort them asc.
        observableMovies.addAll(movieAPI.get());
        observableMovies = sortAscendingByTitle(observableMovies);

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data
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

    //TODO
    public String getMostPopularActor(List<Movie> movies) {
        return null;
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getTitle)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie != null && movie.getDirectors() != null && Arrays.asList(movie.getDirectors()).contains(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }


    public void filterMovieView() {
        StringBuilder url = new StringBuilder("https://prog2.fh-campuswien.ac.at/movies?");
        if (!searchField.getText().isBlank()) {
            url.append("&query=").append(searchField.getText());
        }
        if (!genreComboBox.getSelectionModel().isEmpty()) {
            url.append("&genre=").append(genreComboBox.getSelectionModel().getSelectedItem());
        }
        if (!releaseYearField.getSelectionModel().isEmpty()) {
            url.append("&releaseYear=").append(releaseYearField.getSelectionModel().getSelectedItem());
        }
        if (!ratingComboBox.getSelectionModel().isEmpty()) {
            url.append("&ratingFrom=").append(ratingComboBox.getSelectionModel().getSelectedItem());
        }
        // delete unnecessary & after base url
        if (url.length() > "https://prog2.fh-campuswien.ac.at/movies?".length()) {
            url.deleteCharAt("https://prog2.fh-campuswien.ac.at/movies?".length());
        }
        observableMovies.clear();
        observableMovies.addAll(movieAPI.get(url.toString()));
        observableMovies = sortAscendingByTitle(observableMovies);
    }

    public ObservableList<Movie> filterByGenre(ObservableList<Movie> movies, Genre genre) {
        return FXCollections.observableList(movies.stream().filter(movie -> movie.getGenres().contains(genre))
                .collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByQuery(ObservableList<Movie> movies, String query) {
        return FXCollections.observableList(movies.stream()
                .filter(movie -> movie.getDescription().toLowerCase().contains(query.toLowerCase())
                        || movie.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByQueryAndGenre(ObservableList<Movie> movies, String query, Genre genre) {
        return FXCollections.observableList(movies.stream()
                .filter(movie -> movie.getDescription().toLowerCase().contains(query.toLowerCase())
                        || movie.getTitle().toLowerCase().contains(query.toLowerCase()))
                .filter(movie -> movie.getGenres().contains(genre))
                .collect(Collectors.toList()));
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