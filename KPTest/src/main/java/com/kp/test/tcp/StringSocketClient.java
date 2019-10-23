package com.kp.test.tcp;

import com.kp.common.data.message.IMessageParser;
import com.kp.network.protocol.DefaultFutureManager;
import com.kp.network.protocol.FutureManager;
import com.kp.network.protocol.event.impl.IConnectionListenerManager;
import com.kp.network.protocol.netty.client.tcp.NettyTcpClient;
import com.kp.test.StringMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class StringSocketClient extends NettyTcpClient<StringMessage> {


    public StringSocketClient(IConnectionListenerManager connectionListenerManager, IMessageParser<ByteBuf, StringMessage> messageParser) {
        super(connectionListenerManager, messageParser);
    }

    @Override
    public void applyOptions(Bootstrap b) {

    }

    @Override
    public void applyChannelHandler(Channel ch) {

    }


    @Override
    protected FutureManager<String, StringMessage> createFutureManager() {
        return new DefaultFutureManager<>();
    }
}
