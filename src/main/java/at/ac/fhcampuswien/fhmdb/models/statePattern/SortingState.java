package at.ac.fhcampuswien.fhmdb.models.statePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public interface SortingState {
    ObservableList<Movie> sort(ObservableList<Movie> movies);
    SortingState getNextState();
    String getText();
}