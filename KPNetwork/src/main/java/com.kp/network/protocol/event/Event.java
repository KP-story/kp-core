package com.kp.network.protocol.event;

public interface Event<T> {
    int getType();

    T getValue();

    long getTime();

}
