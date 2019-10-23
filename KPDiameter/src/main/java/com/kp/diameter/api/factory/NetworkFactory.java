package com.kp.diameter.api.factory;

import com.kp.diameter.api.message.IDMessage;
import com.kp.network.protocol.SocketServer;
import com.kp.network.protocol.connection.IConnection;


public interface NetworkFactory<Y> {
    SocketServer<IDMessage> createServer() throws Exception;

    IConnection createClient() throws Exception;

}
