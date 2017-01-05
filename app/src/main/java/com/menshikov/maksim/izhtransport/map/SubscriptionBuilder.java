package com.menshikov.maksim.izhtransport.map;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Maksim on 27.11.2016.
 */

public final class SubscriptionBuilder<T>
{
    public final Subscription Build(Observable.OnSubscribe<T> onCreate, Scheduler subscribeOn, Scheduler observeOn, Subscriber<T> onSubscribe)
    {
       return Observable.create(onCreate).subscribeOn(subscribeOn).observeOn(observeOn).subscribe(onSubscribe);
    }
}
