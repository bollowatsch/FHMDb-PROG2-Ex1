package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class Database {
    public static final String DB_URL = "jdbc:h2:file:./db/fhmdb";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private static ConnectionSource connectionSource;
    private static Database instance;

    private Dao<MovieEntity, Long> movieDao;
    private Dao<WatchlistMovieEntity, Long> watchlistDao;

    //TODO: Exception Handling in Methodensignatur?
    private Database() {
        try {
            createConnectionSource();
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        } catch (SQLException e) {
            System.out.println("Error creating database");
            System.out.println(e.getMessage());
        }
    }

    public static Database getDatabase() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private static void createConnectionSource() throws SQLException {
        connectionSource = new  JdbcConnectionSource(DB_URL, USER, PASSWORD);
    }

    public void testDB() throws SQLException {
        MovieEntityBuilder movieEntityBuilder = new MovieEntityBuilder();
        MovieEntity movie = movieEntityBuilder.setTitle("Test").build();
        movieDao.create(movie);
        movie = movieEntityBuilder.setTitle("best title").setDescription("best description").setGenres("ACTION, DRAMA").build();
        movieDao.create(movie);
    }

    private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return this.watchlistDao;
    }

    public Dao<MovieEntity, Long> getMovieDao() {
        return this.movieDao;
    }
}