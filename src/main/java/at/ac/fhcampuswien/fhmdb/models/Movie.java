package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {


    public enum Genre{
        ACTION, ADVENTURE, ANIMATION, BIOGRAPHY, COMEDY, CRIME, DRAMA, DOCUMENTARY, FAMILY, FANTASY, HISTORY, HORROR, MUSICAL, MYSTERY, ROMANCE, SCIENCE_FICTION, SPORT, THRILLER, WAR,WESTERN
    }
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public List<Genre> getGenres() { return genres; }

    public String getGenresAsString() {
        StringBuilder sb = new StringBuilder();
        for(Genre genre : getGenres()){
            sb.append(genre.toString().toUpperCase());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length() - 1);
        return sb.toString();
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here -> Maybe CSV import from IMDB_TOP100
        movies.add(new Movie("The Shawshank Redemption",
                "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA))));
        movies.add(new Movie("The Godfather",
                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.CRIME))));
        movies.add(new Movie("12th fail",
                "The real-life story of IPS Officer Manoj Kumar Sharma and IRS Officer Shraddha Joshi.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.BIOGRAPHY))));
        movies.add(new Movie("The Dark Knight",
                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.CRIME,Genre.ACTION))));
        movies.add(new Movie("Schindler's List",
                "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.HISTORY,Genre.BIOGRAPHY))));
        movies.add(new Movie("The Lord of the Rings: The Return of the King",
                "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.ACTION,Genre.ADVENTURE))));
        movies.add(new Movie("12 Angry Men",
                "The jury in a New York City murder trial is frustrated by a single member whose skeptical caution forces them to more carefully consider the evidence before jumping to a hasty verdict.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.CRIME))));
        movies.add(new Movie("The Godfather Part II",
                "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.CRIME))));
        movies.add(new Movie("Pulp Fiction",
                "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.CRIME))));
        movies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring\n",
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron." ,
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.ACTION,Genre.ADVENTURE))));
        movies.add(new Movie("Inception",
                "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.",
                new ArrayList<Genre>(Arrays.asList(Genre.ACTION,Genre.ADVENTURE,Genre.SCIENCE_FICTION))));
        movies.add(new Movie("Fight Club",
                "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA))));
        movies.add(new Movie("Forrest Gump",
                "The history of the United States from the 1950s to the '70s unfolds from the perspective of an Alabama man with an IQ of 75, who yearns to be reunited with his childhood sweetheart.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.ROMANCE))));
        movies.add(new Movie("The Lord of the Rings: The Two Towers",
                "While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.",
                new ArrayList<Genre>(Arrays.asList(Genre.DRAMA,Genre.ACTION,Genre.ADVENTURE))));
        movies.add(new Movie("The Good, the Bad and the Ugly",
                "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery",
                new ArrayList<Genre>(Arrays.asList(Genre.ADVENTURE,Genre.WESTERN))));
        return movies;
    }
}
