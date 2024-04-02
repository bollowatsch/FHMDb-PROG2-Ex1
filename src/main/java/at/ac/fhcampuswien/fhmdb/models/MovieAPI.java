package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MovieAPI {
    private final String URL = "https://prog2.fh-campuswien.ac.at/movies?";
    private final OkHttpClient client = new OkHttpClient();

    public List<Movie> get() {
        return get(URL);
    }

    private List<Movie> get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "http.agent")
                .build();

        try (Response res = client.newCall(request).execute()){
            assert res.body() != null;

            Gson gson = new Gson();

            Type collectionType = new TypeToken<List<Movie>>(){}.getType();
            return gson.fromJson(res.body().string(), collectionType);
        } catch (IOException e) {
            return Movie.initializeMovies();
        }
    }
}