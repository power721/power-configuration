package org.power.configuration.util;

public interface Observable<T> {

    Subscription subscribe(Observer<? super T> observer);

}
