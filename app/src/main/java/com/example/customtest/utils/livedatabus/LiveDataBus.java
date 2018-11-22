package com.example.customtest.utils.livedatabus;


import com.example.customtest.utils.livedatabus.liveevent.LiveEvent;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import io.reactivex.annotations.NonNull;

/**
 * Created by ZQiong on 2018/11/5.
 */

public final class LiveDataBus {


    private final Map<String, BusLiveEvent<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    public synchronized <T> Observable<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusLiveEvent<>());
        }
        return (Observable<T>) bus.get(key);
    }

    public Observable<Object> with(String key) {
        return with(key, Object.class);
    }

    public interface Observable<T> {
        void setValue(T value);

        void postValue(T value);

        void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer);

        void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer);

        void observeForever(@NonNull Observer<T> observer);

        void observeStickyForever(@NonNull Observer<T> observer);

        void removeObserver(@NonNull Observer<T> observer);
    }

    private static class BusLiveEvent<T> extends LiveEvent<T> implements Observable<T> {
        @Override
        protected Lifecycle.State observerActiveLevel() {
            return super.observerActiveLevel();
        }
    }
}