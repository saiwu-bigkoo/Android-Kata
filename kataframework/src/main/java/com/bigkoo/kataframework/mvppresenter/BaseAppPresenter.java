package com.bigkoo.kataframework.mvppresenter;

import com.bigkoo.kataframework.http.observer.HttpResultObserver;
import com.bigkoo.kataframework.rx.MainThread;
import com.bigkoo.kataframework.rx.RxBus;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Sai on 2018/4/16.
 */

public abstract class BaseAppPresenter<V> extends BasePresenter<V> {
    private final BehaviorSubject lifecycleSubject = BehaviorSubject.create();

    public void onCallHttpRequest(Observable observable, HttpResultObserver callBack){
        observable
                .compose(MainThread.io())
                .compose(RxLifecycle.bindUntilEvent(lifecycleSubject, ActivityEvent.DESTROY))
                .subscribe(callBack);
    }

    public <T> void toObservable(final Class<T> eventType,Consumer<? super T> onNext) {
        RxBus.getInstance().toObservable(eventType)
                .compose(MainThread.io())
                .compose(RxLifecycle.bindUntilEvent(this.lifecycleSubject,ActivityEvent.DESTROY))
                .subscribe(onNext);
    }

    public void post(Object event){
        RxBus.getInstance().post(event);
    }
}
