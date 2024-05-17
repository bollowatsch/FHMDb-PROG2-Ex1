package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.models.exception.MovieAPIException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MovieAPI {
    private final OkHttpClient client;
    private static MovieAPI instance;

    private MovieAPI() {
        client = new OkHttpClient();
    }

    public static MovieAPI getMovieAPI() throws MovieAPIException {
        if (instance == null) {
            instance = new MovieAPI();
        }
        return instance;
    }

    public List<Movie> get() throws MovieAPIException {
        URLBuilder urlBuilder = new URLBuilder();
        return get(urlBuilder.build());
    }

    public List<Movie> get(String url) throws MovieAPIException {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "http.agent")
                .build();

        try (Response res = client.newCall(request).execute()) {
            assert res.body() != null;

            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Movie>>() {
            }.getType();
            return gson.fromJson(res.body().string(), collectionType);
        } catch (IOException err) {
            throw new MovieAPIException("Couldn't connect to API endpoint. Check network connection!", err);
        }
    }
}