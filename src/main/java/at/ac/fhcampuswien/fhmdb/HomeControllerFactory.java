package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.models.exception.DatabaseException;
import javafx.util.Callback;

public class HomeControllerFactory implements Callback<Class<?>, Object> {
    private static HomeController instance;
    public HomeController getHomeController(){
        if (instance == null) {
            instance = new HomeController();
        }
        return instance;
    }
    @Override
    public Object call(Class<?> aClass) {
        try {
            return getHomeController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}