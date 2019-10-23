package com.kp.network.protocol.event.impl;

import com.kp.common.data.message.IMessage;
import com.kp.network.protocol.connection.IConnection;
import com.kp.network.protocol.event.EventListener;

public interface ConnectionListener<T extends IMessage> extends EventListener {
    void connectionOpened(IConnection connection);


    void connectionClosed(IConnection connection);


    void messageReceived(IConnection connection, T message);


    void internalError(IConnection connection, T message, Throwable cause);

}
