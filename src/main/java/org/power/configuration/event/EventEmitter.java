package org.power.configuration.event;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import org.power.configuration.util.Observable;
import org.power.configuration.util.Observer;
import org.power.configuration.util.Subscription;

public class EventEmitter implements Observable<ConfigurationEvent> {

    private final ExecutorService executor = ForkJoinPool.commonPool();

    private List<EventSubscription> subscriptions = new LinkedList<>();

    public void emit(ConfigurationEvent event) {
        subscriptions.forEach(subscription -> subscription.next(event));
    }

    public void error(Throwable throwable) {
        subscriptions.forEach(subscription -> subscription.error(throwable));
    }

    @Override
    public Subscription subscribe(Observer<? super ConfigurationEvent> observer) {
        EventSubscription subscription = new EventSubscription(observer);
        subscriptions.add(subscription);
        return subscription;
    }

    class EventSubscription implements Subscription {

        private final Observer<? super ConfigurationEvent> observer;
        private Future<?> future;
        private boolean completed;

        EventSubscription(Observer<? super ConfigurationEvent> observer) {
            this.observer = observer;
        }

        @Override
        public synchronized void unsubscribe() {
            completed = true;
            subscriptions.remove(this);
            if (future != null) {
                future.cancel(false);
            }
        }

        synchronized void next(ConfigurationEvent event) {
            if (!completed) {
                future = executor.submit(() -> observer.onNext(event));
            }
        }

        synchronized void error(Throwable throwable) {
            executor.submit(() -> observer.onError(throwable));
        }

    }

}
