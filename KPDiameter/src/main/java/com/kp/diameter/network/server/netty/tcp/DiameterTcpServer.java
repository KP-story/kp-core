package com.kp.diameter.network.server.netty.tcp;

import com.kp.common.data.message.IMessageParser;
import com.kp.diameter.api.message.IDMessage;
import com.kp.network.protocol.DefaultFutureManager;
import com.kp.network.protocol.FutureManager;
import com.kp.network.protocol.connection.IConnectionManager;
import com.kp.network.protocol.event.impl.DefaultConnectionListenerManager;
import com.kp.network.protocol.event.impl.IConnectionListenerManager;
import com.kp.network.protocol.netty.server.tcp.NettyTcpServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class DiameterTcpServer extends NettyTcpServer<IDMessage> {
    public DiameterTcpServer(IConnectionManager<IDMessage, Channel> connectionManager, IConnectionListenerManager<IDMessage> connectionListenerManager, IMessageParser<ByteBuf, IDMessage> messageParser) throws Exception {
        super(connectionManager, connectionListenerManager, messageParser);
    }

    @Override
    protected IConnectionListenerManager<IDMessage> createEntryConnectionListenerManager() {
        return new DefaultConnectionListenerManager<>();
    }

    @Override
    public void applyOptions(ServerBootstrap b) {

    }

    @Override
    protected FutureManager<String, IDMessage> newFutureManager() {
        return new DefaultFutureManager<IDMessage>();
    }
}
