package at.ac.fhcampuswien.fhmdb.models.statePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;

public class DescendingState implements SortingState{
    @Override
    public ObservableList<Movie> sort(ObservableList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle));
        Collections.reverse(movies);
        return movies;
    }
}
