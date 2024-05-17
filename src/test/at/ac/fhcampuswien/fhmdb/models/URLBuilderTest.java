package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class URLBuilderTest {
    @Test
    public void testBuildURLWithQuery() {
        //given
        URLBuilder urlBuilder = new URLBuilder();
        String expected = "https://prog2.fh-campuswien.ac.at/movies?query=Inception";
        //when
        String url = urlBuilder.setQuery("Inception").build();
        //then
        assertEquals(expected, url);
    }

    @Test
    public void testBuildURLWithGenre() {
        //given
        URLBuilder urlBuilder = new URLBuilder();
        String expected = "https://prog2.fh-campuswien.ac.at/movies?genre=ACTION";
        //when
        String url = urlBuilder.setGenre(Genre.ACTION).build();
        //then
        assertEquals(expected, url);
    }

    @Test
    public void testBuildURLWithReleaseYear() {
        //given
        URLBuilder urlBuilder = new URLBuilder();
        //when
        String url = urlBuilder.setReleaseYear(2010).build();
        //then
        assertEquals("https://prog2.fh-campuswien.ac.at/movies?releaseYear=2010", url);
    }

    @Test
    public void testBuildURLWithRatingFrom() {
        //given
        URLBuilder urlBuilder = new URLBuilder();
        //when
        String url = urlBuilder.setRatingFrom(8).build();
        //then
        assertEquals("https://prog2.fh-campuswien.ac.at/movies?ratingFrom=8", url);
    }

    @Test
    public void testBuildURLWithMultipleParameters() {
        //given
        URLBuilder urlBuilder = new URLBuilder();
        //when
        String url = urlBuilder.setQuery("Inception")
                .setGenre(Genre.ACTION)
                .setReleaseYear(2010)
                .setRatingFrom(8)
                .build();
        //then
        assertEquals("https://prog2.fh-campuswien.ac.at/movies?query=Inception&genre=ACTION&releaseYear=2010&ratingFrom=8", url);
    }

    @Test
    public void testBuildURLWithNoParameters() {
        //given
        URLBuilder urlBuilder = new URLBuilder();
        //when
        String url = urlBuilder.build();
        //then
        assertEquals("https://prog2.fh-campuswien.ac.at/movies?", url);
    }

    @Test
    public void testResetAfterBuild() {
        //given
        URLBuilder urlBuilder = new URLBuilder();
        //when
        urlBuilder.setQuery("Inception").setGenre(Genre.ACTION).build();
        String url = urlBuilder.build();
        //then
        assertEquals("https://prog2.fh-campuswien.ac.at/movies?", url);
    }
}