package ru.itmo.lab.collection;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

public class TimedCollection<T> implements Serializable {
    @Getter
    private final T collection;
    @Getter
    private final Instant initializationTime;

    TimedCollection(T collection) {
        this.collection = collection;
        this.initializationTime = Instant.now();
    }

    @Override
    public String toString() {
        return "TimedCollection{" +
                "collection=" + collection +
                ", initializationTime=" + initializationTime +
                '}';
    }
}
