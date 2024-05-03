package at.ac.fhcampuswien.fhmdb.models;

public interface ClickEventHandler<T> {
    abstract void onClick(T t);
}