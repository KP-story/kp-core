package com.kp.taskmanager.network.client;

import com.kp.common.data.message.IMessageParser;
import com.kp.network.protocol.DefaultFutureManager;
import com.kp.network.protocol.FutureManager;
import com.kp.network.protocol.event.impl.IConnectionListenerManager;
import com.kp.network.protocol.netty.client.tcp.NettyTcpClient;
import com.kp.taskmanager.network.codec.VOMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.json.JsonObjectDecoder;

public class SocketClient extends NettyTcpClient<VOMessage> {
    public SocketClient(IConnectionListenerManager connectionListenerManager, IMessageParser<ByteBuf, VOMessage> messageParser) {
        super(connectionListenerManager, messageParser);
    }

    @Override
    public void applyOptions(Bootstrap b) {

    }

    @Override
    public void applyChannelHandler(Channel ch) {
        ch.pipeline().addLast(new JsonObjectDecoder());

    }

    @Override
    protected FutureManager<String, VOMessage> createFutureManager() {
        return new DefaultFutureManager<>();
    }
}
