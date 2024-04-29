package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    public static final String DB_URL = "jdbc:h2:file ./db/fhmdb";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private static ConnectionSource connectionSource;

    Dao<MovieEntity, Long> movieDao;

    private static Database instance;

    //TODO: Exception Handling in Methodensignatur?
    private Database() {
        try {
            createConnectionSource();
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
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
        MovieEntity movie = movieEntityBuilder.setId(1).setTitle("Test").build();
        movieDao.create(movie);
    }



}
