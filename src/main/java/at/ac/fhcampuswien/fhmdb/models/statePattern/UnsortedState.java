package at.ac.fhcampuswien.fhmdb.models.statePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class UnsortedState implements SortingState{
    @Override
    public ObservableList<Movie> sort(ObservableList<Movie> movies) {
        return movies;
    }

    @Override
    public SortingState getNextState() {
        return new AscendingState();
    }

    @Override
    public String getText() {
        return "Sort -";
    }
}