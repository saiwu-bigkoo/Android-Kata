package com.bigkoo.kataframework.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Sai on 2018/3/15.
 */

public class RxBus {
    private final Subject<Object> bus;

    private RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    private static class RxBusInstance {
        private static final RxBus INSTANCE = new RxBus();
    }

    public static RxBus getInstance() {
        return RxBusInstance.INSTANCE;
    }

    /**
     * 发送事件
     */
    public void post(Object event) {
        bus.onNext(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(final Class<T> eventType) {
        Observable<T> observable = bus.ofType(eventType);
        return observable;
    }

}
