package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.models.exception.DatabaseException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Popup;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.h2.expression.function.table.LinkSchemaFunction;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class MovieAPI {
    private static OkHttpClient client;
    private static MovieRepository repository;
    private static MovieAPI instance;

    private MovieAPI(){}

    public static MovieAPI getMovieAPI () throws DatabaseException {
        if (instance == null) {
            client = new OkHttpClient();
            repository = new MovieRepository();
            instance = new MovieAPI();
        }
        return instance;
    }

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
            return gson.fromJson(res.body().string(), collectionType);
        } catch (IOException e) {
            //load movies from db
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No network, movies are loaded from cache!");
            alert.show();
            return MovieEntity.toMovies(repository.getAllMovies());
        }
    }
}