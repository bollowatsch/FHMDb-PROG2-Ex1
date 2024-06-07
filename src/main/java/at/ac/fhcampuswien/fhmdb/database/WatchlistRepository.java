package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.observerPattern.Observable;
import at.ac.fhcampuswien.fhmdb.models.observerPattern.Observer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository implements Observable {
    List<Observer> observers;
    Dao<WatchlistMovieEntity, Long> dao;
    private static WatchlistRepository instance;

    private WatchlistRepository() throws DatabaseException {
        this.dao = Database.getDatabase().getWatchlistDao();
        this.observers = new ArrayList<>();
    }

    public static WatchlistRepository getWatchlistRepositoryInstance() throws DatabaseException {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
    }

    public List<WatchlistMovieEntity> getWatchlist() throws DatabaseException {
        try {
            List<WatchlistMovieEntity> list = dao.queryForAll();
            return list;
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while reading watchlist from database", err);
        }
    }

    public void addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        List<WatchlistMovieEntity> list = getWatchlist();
        //list.contains(movie);

        boolean found = false;
        for(WatchlistMovieEntity wme : list) {
            if (wme.apiId.equals(movie.getApiId())) {
                found = true;
                break;
            }
        }

        if (found) {
            notifyObservers("Movie is already in the watchlist");
        } else {
            try {
                dao.create(movie);
                notifyObservers("Movie added to watchlist");
            } catch (SQLException err) {
                throw new DatabaseException("Something went wrong while adding the movie with the apiId \"" + movie.apiId + "\" to the watchlist table", err);
            }
        }
    }

    public void removeFromWatchlist(String apiId) throws DatabaseException {
        DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("apiId", apiId);
            deleteBuilder.delete();
            notifyObservers("Movie removed from Watchlist");
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while deleting the movie with the apiId \"" + apiId + "\" from the watchlist table.", err);
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
}