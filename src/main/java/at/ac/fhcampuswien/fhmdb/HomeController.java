package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();
    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize observableList and sort them asc.
        observableMovies.addAll(allMovies);
        observableMovies = sortAscendingByTitle(observableMovies);

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data
        movieListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                observableMovies = sortAscendingByTitle(observableMovies);
            } else {
                observableMovies = sortDescendingByTitle(observableMovies);
            }
        });

        searchBtn.setOnAction(actionEvent -> filterMovieView());
        searchField.setOnAction(actionEvent -> filterMovieView());
    }

    public void filterMovieView(){
        observableMovies.clear();

        if (!searchField.getText().isBlank() && !genreComboBox.getSelectionModel().isEmpty() && !genreComboBox.getValue().equals(Genre.ALL)) {
            observableMovies.addAll(filterByQueryAndGenre(searchField.getText(), Genre.valueOf(genreComboBox.getValue().toString())));
        } else if (!searchField.getText().isBlank()) {
            observableMovies.addAll(filterByQuery(searchField.getText()));
        } else if (!genreComboBox.getSelectionModel().isEmpty()) {
            Genre genre = genreComboBox.getValue();
            if (genre == Genre.ALL) {
                observableMovies.addAll(allMovies);
            } else {
                observableMovies.addAll(filterByGenre(genre));
            }
        } else {
            observableMovies.addAll(allMovies);
        }

        observableMovies = sortAscendingByTitle(observableMovies);
    }

    public ObservableList<Movie> filterByGenre(Genre genre) {
        return FXCollections.observableList(allMovies.stream()
                .filter(movie -> movie.getGenres().contains(genre))
                .collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByQuery(String query) {
        return FXCollections.observableList(allMovies.stream()
                .filter(movie -> movie.getDescription().toLowerCase().contains(query.toLowerCase())
                        || movie.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList()));
    }

    public ObservableList<Movie> filterByQueryAndGenre(String query, Genre genre) {
        return FXCollections.observableList(allMovies.stream()
                .filter(movie -> movie.getDescription().toLowerCase().contains(query.toLowerCase())
                        || movie.getTitle().toLowerCase().contains(query.toLowerCase()))
                .filter(movie -> movie.getGenres().contains(genre))
                .collect(Collectors.toList()));
    }

    public ObservableList<Movie> sortAscendingByTitle(ObservableList<Movie> observableMovies) {
        observableMovies.sort(Comparator.comparing(Movie::getTitle));
        setSortBtnText("Sort (desc)");
        return observableMovies;
    }

    public ObservableList<Movie> sortDescendingByTitle(ObservableList<Movie> observableMovies) {
        observableMovies.sort(Comparator.comparing(Movie::getTitle));
        Collections.reverse(observableMovies);
        setSortBtnText("Sort (asc)");
        return observableMovies;
    }

    public void setSortBtnText(String text) {
        if (sortBtn != null) {
            sortBtn.setText(text);
        }
    }
}