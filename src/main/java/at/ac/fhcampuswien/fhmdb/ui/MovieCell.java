package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genres = new Label();
    private final Button addToWatchlist = new Button();
    private final Button removeFromWatchlist = new Button();
    private final HBox head = new HBox(title, addToWatchlist);
    private final VBox layout = new VBox(head, detail, genres);

    public MovieCell(ClickEventHandler<Movie> addToWatchlistClicked) {
        super();
        addToWatchlist.setOnAction(actionEvent -> {
            addToWatchlistClicked.onClick(getItem());
        });
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        setGraphic(null);

        if (empty || movie == null) {
            setText(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );
            genres.setText(
                    movie.getGenres() != null
                            ? movie.getGenresAsString()
                            : "No genres available"
            );

            addToWatchlist.setText("★");
            removeFromWatchlist.setText("-");

            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genres.getStyleClass().add("text-white");
            genres.getStyleClass().add("text-italic");
            addToWatchlist.getStyleClass().add("background-yellow");
            removeFromWatchlist.getStyleClass().add("background-yellow");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            HBox.setHgrow(title, Priority.ALWAYS);
            title.setMaxWidth(Double.MAX_VALUE);
            head.setPadding(new Insets(0, 10, 0, 0));
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
}