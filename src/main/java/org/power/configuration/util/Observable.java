package org.power.configuration.util;

public interface Observable<T> {

    void subscribe(Observer<? super T> observer);

}
