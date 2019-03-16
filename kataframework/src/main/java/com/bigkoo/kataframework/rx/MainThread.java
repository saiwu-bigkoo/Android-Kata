package com.bigkoo.kataframework.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sai on 2018/3/16.
 * Rx切换主线程
 */

public class MainThread {
    private static ObservableTransformer ioToMainThreadSchedulerTransformer;


    static {
        ioToMainThreadSchedulerTransformer = createIOToMainThreadScheduler();
    }


    @SuppressWarnings("unchecked")
    private static <T> ObservableTransformer<T, T> createIOToMainThreadScheduler() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> io() {
        return ioToMainThreadSchedulerTransformer;
    }
}
