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

        try (Response res = client.newCall(request).execute()){
            assert res.body() != null;

            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Movie>>(){}.getType();
            System.out.println("NOTE: Movies loaded from API.");
            return gson.fromJson(res.body().string(), collectionType);
        } catch (IOException e) {
            System.out.println("NOTE: Movies loaded from database.");
            return MovieEntity.toMovies(repository.getAllMovies());
        }
    }
}