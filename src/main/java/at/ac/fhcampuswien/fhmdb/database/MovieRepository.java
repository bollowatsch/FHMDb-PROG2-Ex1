package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.exception.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;
    private static MovieRepository instance;

    private MovieRepository() throws DatabaseException {
        this.dao = Database.getDatabase().getMovieDao();
    }

    public static MovieRepository getMovieRepository() throws DatabaseException {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while reading movies from database", err);
        }
    }

    public void addMovie(MovieEntity movie) throws DatabaseException {
        try {
            dao.create(movie);
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while adding the movie with the apiId \"" + movie.apiId + "\" to the movie table", err);
        }
    }


    public void removeAll() throws DatabaseException {
        DeleteBuilder<MovieEntity, Long> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.delete();
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while deleting movies", err);
        }
    }


    public MovieEntity getMovie(long id) throws DatabaseException {
        try {
            return dao.queryForId(id);
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while reading the movie with the apiId \"" + id + "\"", err);
        }
    }


    public void addAllMovies(List<Movie> movies) throws DatabaseException {
        List<MovieEntity> movieEntities = MovieEntity.fromMovies(movies);
        try {
            dao.create(movieEntities);
        } catch (SQLException err) {
            throw new DatabaseException("Something went wrong while adding movies to the database", err);
        }
    }
}