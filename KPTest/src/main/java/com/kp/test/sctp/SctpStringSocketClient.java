package com.kp.test.sctp;

import com.kp.common.data.message.IMessageParser;
import com.kp.network.protocol.DefaultFutureManager;
import com.kp.network.protocol.FutureManager;
import com.kp.network.protocol.event.impl.IConnectionListenerManager;
import com.kp.network.protocol.netty.client.sctp.NettySctpClient;
import com.kp.test.StringMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.sctp.SctpMessage;

public class SctpStringSocketClient extends NettySctpClient<StringMessage> {


    public SctpStringSocketClient(IConnectionListenerManager connectionListenerManager, IMessageParser<SctpMessage, StringMessage> messageParser) {
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
