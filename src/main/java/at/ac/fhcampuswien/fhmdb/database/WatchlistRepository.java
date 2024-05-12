package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository() {
        this.dao = Database.getDatabase().getWatchlistDao();
    }

    public List<WatchlistMovieEntity> getWatchlist() throws SQLException {
        return dao.queryForAll();
    }

    public void addToWatchlist(WatchlistMovieEntity movie) throws SQLException{
        if(dao.create(movie) != 1)
            throw new SQLException("Something went wrong while adding the movie with the apiId \"" + movie.apiId + "\" to the watchlist table");
    }

    public void removeFromWatchlist(String apiId) throws SQLException {
        DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("apiId", apiId);
       if(deleteBuilder.delete() != 1)
            throw new SQLException("Something went wrong while deleting the movie with the apiId \"" + apiId + "\" from the watchlist table.");

    }

}