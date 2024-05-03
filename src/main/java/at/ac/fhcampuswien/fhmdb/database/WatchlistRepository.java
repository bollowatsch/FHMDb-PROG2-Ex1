package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository() {
        this.dao = Database.getDatabase().getWatchlistDao();
    }

    //TODO HANDLE EXCEPTIONS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<WatchlistMovieEntity> getWatchlist() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addToWatchlist(WatchlistMovieEntity movie)  {
        try {
            dao.create(movie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //TODO: change return statement?
        return 1;
    }

    public int removeFromWatchlist(String apiId)  {
        try {
            dao.deleteById(Long.valueOf(apiId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //TODO: change return statement?
        return 0;
    }
}