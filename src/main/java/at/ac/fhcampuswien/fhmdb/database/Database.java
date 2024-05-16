package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.exception.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    private static ConnectionSource connectionSource;
    private static Database instance;

    private final Dao<MovieEntity, Long> movieDao;
    private final Dao<WatchlistMovieEntity, Long> watchlistDao;

    private Database() throws DatabaseException {
        createConnectionSource();
        try {
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException err) {
            throw new DatabaseException("Could not create database", err);
        }
        createTables();
    }

    public static Database getDatabase() throws DatabaseException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void createConnectionSource() throws DatabaseException {
        try {
            connectionSource = new JdbcConnectionSource(ConnectDatabase.DB_URL, ConnectDatabase.USER, ConnectDatabase.PASSWORD);
        } catch (SQLException err) {
            throw new DatabaseException("Error creating connection source", err);
        }
    }

    private void createTables() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException err) {
            throw new DatabaseException("Error creating tables", err);
        }
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return this.watchlistDao;
    }

    public Dao<MovieEntity, Long> getMovieDao() {
        return this.movieDao;
    }
}