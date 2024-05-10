package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;

    public MovieRepository(){
        this.dao = Database.getDatabase().getMovieDao();
    }

    public List<MovieEntity> getAllMovies() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMovie(MovieEntity movie) throws SQLException {
        dao.create(movie);
    }


    public int removeAll() throws SQLException {
        DeleteBuilder<MovieEntity, Long> deleteBuilder = dao.deleteBuilder();
        int rowsDeleted = deleteBuilder.delete();
        return rowsDeleted;
    }


    public MovieEntity getMovie(long id) throws SQLException {
        return dao.queryForId(id);
    }


    public int addAllMovies(List<Movie> movies) throws SQLException {
        List<MovieEntity> movieEntities = MovieEntity.fromMovies(movies);
        dao.create(movieEntities);
        return movieEntities.size();
    }
}