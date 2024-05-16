package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.exception.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository() throws DatabaseException {
        this.dao = Database.getDatabase().getWatchlistDao();
    }

    public List<WatchlistMovieEntity> getWatchlist() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while reading watchlist from database", err);
        }
    }

    public void addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try {
            dao.create(movie);
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while adding the movie with the apiId \"" + movie.apiId + "\" to the watchlist table", err);
        }
    }

    public void removeFromWatchlist(String apiId) throws DatabaseException {
        DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("apiId", apiId);
            deleteBuilder.delete();
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while deleting the movie with the apiId \"" + apiId + "\" from the watchlist table.", err);
        }
    }
}