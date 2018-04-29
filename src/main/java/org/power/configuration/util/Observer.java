package org.power.configuration.util;

public interface Observer<T> {

    void onSubscribe(Subscription subscription);

    void onNext(T item);

    void onError(Throwable throwable);

    void onComplete();

}
