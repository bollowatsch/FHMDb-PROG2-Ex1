package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;

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

    public int addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        return dao.create(movie);
        //TODO: change return statement?
        //return 1;
    }

    public int removeFromWatchlist(String apiId) throws SQLException {
        dao.deleteBuilder().where().eq("apiId",apiId);
        //TODO: change return statement?
        return 0;
    }
}