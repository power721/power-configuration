package org.power.configuration.util;

public interface Observer<T> {

    void onNext(T item);

    void onError(Throwable throwable);

    void onComplete();

}
