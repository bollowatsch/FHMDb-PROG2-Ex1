package at.ac.fhcampuswien.fhmdb.models.statePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class AscendingState implements SortingState{
    @Override
    public ObservableList<Movie> sort(ObservableList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle));
        return movies;
    }
}
