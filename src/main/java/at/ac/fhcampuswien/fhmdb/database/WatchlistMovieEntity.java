package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    long id;
    @DatabaseField(unique = true)
    String apiId;

    public WatchlistMovieEntity() {
        // ORMLite needs a no-arg constructor
    }

    public String getApiId (){
        return apiId;
    }

    public WatchlistMovieEntity(String apiId) {
        this.apiId = apiId;
    }
}