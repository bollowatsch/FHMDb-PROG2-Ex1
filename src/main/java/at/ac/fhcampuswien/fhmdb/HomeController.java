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
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data
        movieListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                movieListView.setItems(sortAscendingByTitle(movieListView.getItems()));
            } else {
                movieListView.setItems(sortDescendingByTitle(movieListView.getItems()));
            }
        });

        searchBtn.setOnAction(actionEvent -> {
            //TODO: Correct display of list filtered by query

            if (!searchField.getText().isBlank()) {
                //TODO: Filter list by Query
            }

            if (!genreComboBox.getSelectionModel().isEmpty()) {
                movieListView.setItems(filterByGenre(observableMovies, Genre.valueOf(genreComboBox.getValue().toString())));
            }
        });
    }

    public ObservableList<Movie> filterByGenre(ObservableList<Movie> observableMovies, Genre genre) {
        return FXCollections.observableList(observableMovies.stream()
                .filter(movie -> movie.getGenres().contains(genre)).
                collect(Collectors.toList()));
    }

    Comparator<? super Movie> movieComparator = Comparator.comparing(Movie::getTitle);
    public ObservableList<Movie> sortAscendingByTitle(ObservableList<Movie> observableMovies) {
        observableMovies.sort(movieComparator);
        setSortBtnText("Sort (desc)");
        return observableMovies;
    }

    public ObservableList<Movie> sortDescendingByTitle(ObservableList<Movie> observableMovies) {
        observableMovies.sort(movieComparator);
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