package com.kp.diameter;

import com.kp.common.data.message.IMessageParser;
import com.kp.diameter.api.message.Answer;
import com.kp.diameter.api.message.IDMessage;
import com.kp.diameter.api.message.Request;
import com.kp.diameter.api.message.ResultCode;
import com.kp.diameter.api.message.impl.parser.IDMessageParser;
import com.kp.diameter.api.message.impl.parser.impl.MessageParser;
import com.kp.diameter.network.client.netty.tcp.DiameterTcpClient;
import com.kp.diameter.network.codec.NettyTcpMessageParser;
import com.kp.diameter.network.server.netty.tcp.DiameterTcpServer;
import com.kp.network.protocol.DefaultFutureManager;
import com.kp.network.protocol.FutureManager;
import com.kp.network.protocol.connection.DefaultConnectionManager;
import com.kp.network.protocol.connection.IConnection;
import com.kp.network.protocol.connection.IConnectionManager;
import com.kp.network.protocol.event.impl.ConnectionListener;
import com.kp.network.protocol.event.impl.DefaultConnectionListenerManager;
import com.kp.network.protocol.event.impl.IConnectionListenerManager;
import com.kp.network.protocol.utilities.IPConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import static com.kp.diameter.api.message.impl.parser.Avp.*;

public class TCPTest {
    public static void main(String[] args) throws Exception {
        createServer();
        createClient();

        MessageParser messageParser = new MessageParser();
        IDMessage message = messageParser.createEmptyMessage(IDMessage.DEVICE_WATCHDOG_REQUEST, 0);
        message.setRequest(true);
        message.setHopByHopIdentifier(message.getEndToEndIdentifier());
        // Set content
        message.getAvps().addAvp(ORIGIN_HOST, "khanhlv", true, false, true);
        message.getAvps().addAvp(ORIGIN_REALM, "hehehe", true, false, true);
        message.getAvps().addAvp(ORIGIN_STATE_ID, 1, true, false, true);
        // Remove trash avp
        message.getAvps().removeAvp(DESTINATION_HOST);
        message.getAvps().removeAvp(DESTINATION_REALM);
        // Send
        IDMessage answer = ((Request) message).createAnswer(ResultCode.SUCCESS);

        System.out.println(answer);
    }


    public static void createClient() throws Exception {

        FutureManager<String, IDMessage> futureManager = new DefaultFutureManager<>();
        IMessageParser<ByteBuf, IDMessage> messageParser = new NettyTcpMessageParser();
        IConnectionListenerManager<IDMessage> connectionListenerManager = new DefaultConnectionListenerManager<>();
        DiameterTcpClient stringSocketClient = new DiameterTcpClient(connectionListenerManager, messageParser);
        stringSocketClient.setTimeout(20000);
        stringSocketClient.addRemoteAddress(new InetSocketAddress("fe80::9b8b:caf8:4fc1:ca7c", 3868));
//        stringSocketClient.addLocalAddress(new InetSocketAddress("localhost",9022));

        stringSocketClient.setNWorker(12);
        connectionListenerManager.init();
        stringSocketClient.connect();
        stringSocketClient.addConnectionListener("khanhlv", new ConnectionListener<IDMessage>() {
            @Override
            public void connectionOpened(IConnection connection) {
                System.out.println("open");
            }

            @Override
            public void connectionClosed(IConnection connection) {
                System.out.println("close");

            }

            @Override
            public void messageReceived(IConnection connection, IDMessage message) {
                System.out.println("message den {}" + message.toString());

            }

            @Override
            public void internalError(IConnection connection, IDMessage message, Throwable cause) {

            }

        });
        while (true) {
            stringSocketClient.send(sendDwrMessage(messageParser));
            Thread.sleep(1000);
        }

    }


    public static IDMessage sendDwrMessage(IMessageParser parser) {
        IDMessageParser idMessageParser = (IDMessageParser) parser;
        IDMessage message = idMessageParser.createEmptyMessage(IDMessage.DEVICE_WATCHDOG_REQUEST, 0);
        message.setRequest(true);
        message.setHopByHopIdentifier(message.getEndToEndIdentifier());
        // Set content
        message.getAvps().addAvp(ORIGIN_HOST, "khanhlv", true, false, true);
        message.getAvps().addAvp(ORIGIN_REALM, "hehehe", true, false, true);
        message.getAvps().addAvp(ORIGIN_STATE_ID, 1, true, false, true);
        // Remove trash avp
        message.getAvps().removeAvp(DESTINATION_HOST);
        message.getAvps().removeAvp(DESTINATION_REALM);
        // Send
        return message;
    }

    public static IDMessage sendDwaMessage(IDMessage dwr, int resultCode, String errorMessage) {

        // Send
        Request request = (Request) dwr;

        Answer answer = request.createAnswer(ResultCode.SUCCESS);
        answer.getAvps().addAvp(ORIGIN_HOST, "khanhlv", true, false, true);
        answer.getAvps().addAvp(ORIGIN_REALM, "hehehe", true, false, true);

        return answer;
    }


    public static void createServer() throws Exception {


        IConnectionManager<IDMessage, Channel> connectionManager = new DefaultConnectionManager<>();
        IConnectionListenerManager<IDMessage> connectionListenerManager = new DefaultConnectionListenerManager<>();
        IMessageParser<ByteBuf, IDMessage> messageParser = new NettyTcpMessageParser();


        DiameterTcpServer stringSocketServer = new DiameterTcpServer(connectionManager, connectionListenerManager, messageParser);
        InetAddress inetAddress = IPConverter.InetAddressByIPv6("fe80::9b8b:caf8:4fc1:ca7c");
        stringSocketServer.addLocalAddress(inetAddress);
        stringSocketServer.addLocalPort(3868);
        stringSocketServer.setNboot(1);
        stringSocketServer.setNworker(1);
        stringSocketServer.setRcvbuf(10);
        stringSocketServer.setTimeout(12000);
        stringSocketServer.addConnectionListener("khanhlv", new ConnectionListener<IDMessage>() {
            @Override
            public void connectionOpened(IConnection connection) {
                System.out.println("connect" + connection);
            }

            @Override
            public void connectionClosed(IConnection connection) {
                System.out.println("disconnect" + connection);

            }

            @Override
            public void messageReceived(IConnection connection, IDMessage message) {
                System.out.println("message vao {}" + message.toString());

                try {
                    IDMessage idMessage = sendDwaMessage(message, 0, null);
                    connection.send(idMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void internalError(IConnection connection, IDMessage message, Throwable cause) {


            }
        });

        stringSocketServer.init();

    }


}
