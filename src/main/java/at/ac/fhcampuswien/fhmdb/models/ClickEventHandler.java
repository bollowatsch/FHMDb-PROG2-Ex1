package at.ac.fhcampuswien.fhmdb.models;

@FunctionalInterface
public interface ClickEventHandler<T> {
    abstract void onClick(T t);
}