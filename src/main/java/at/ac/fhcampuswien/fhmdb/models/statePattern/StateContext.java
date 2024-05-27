package at.ac.fhcampuswien.fhmdb.models.statePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class StateContext {

    private SortingState currentState;

    public StateContext() {
        this.currentState = new UnsortedState();
    }

    public ObservableList<Movie> sort(ObservableList<Movie> movies) {
        return currentState.sort(movies);
    }

    public void setState(SortingState newState) {
        this.currentState = newState;
    }

    public SortingState getCurrentState() {
        return this.currentState;
    }

}
