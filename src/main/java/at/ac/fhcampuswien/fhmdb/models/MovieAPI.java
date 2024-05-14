package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class MovieAPI {
    private final OkHttpClient client = new OkHttpClient();
    private final MovieRepository repository = new MovieRepository();

    public List<Movie> get() {
        URLBuilder urlBuilder = new URLBuilder();
        return get(urlBuilder.build());
    }

    public List<Movie> get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "http.agent")
                .build();

        try (Response res = client.newCall(request).execute()) {
            assert res.body() != null;

            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Movie>>() {}.getType();
            List<Movie> movies = gson.fromJson(res.body().string(), collectionType);
            repository.removeAll();
            repository.addAllMovies(movies);
            return movies;
        } catch (IOException | SQLException e) {
            //load movies from db
            return MovieEntity.toMovies(repository.getAllMovies());
        }
    }
}