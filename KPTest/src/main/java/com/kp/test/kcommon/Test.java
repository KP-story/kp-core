package com.kp.test.kcommon;

import java.util.concurrent.CompletableFuture;

public class Test {
    public static void main(String[] args) throws Exception {
//         VObject vObject= new VObject();
//        vObject.put(APP_ID,"app1");
//        vObject.put(SERVER_ID,"serverId1");
//        VObject network= new VObject();
//        network.put(IP,"0.0.0.0");
//        network.put(PORT,"9093");
//        VObject property= new VObject();
//        property.put(NBOOT,12);
//        property.put(NWORKER,12);
//        property.put(TIMEOUT,12000);
//        network.put(PROPERTY,property);
//
//        vObject.put(NETWORK,network);
//
//
//
//
//          ProcessorManager processorManager= new ProcessorManager("com.kp.taskmanager.manager.processor");
//        processorManager.init(null);
        CompletableFuture<String> stringCompletableFuture = new CompletableFuture<>();
        stringCompletableFuture.complete("");
        stringCompletableFuture.complete("");


//        System.out.println(vObject.toJSON());
//        FutureManager<String, StringMessage> futureManager = new DefaultFutureManager<>();
//        futureManager.init();
//        IMessageParser<ByteBuf, VOMessage> messageParser = new JsonMessageParser();
//        IConnectionListenerManager<VOMessage> connectionListenerManager = new DefaultConnectionListenerManager<>();
//        SocketClient stringSocketClient = new SocketClient(connectionListenerManager, messageParser);
//        stringSocketClient.setTimeout(20000);
//        stringSocketClient.addRemoteAddress(new InetSocketAddress("localhost", 9093));
////        stringSocketClient.addLocalAddress(new InetSocketAddress("localhost",9022));
//        connectionListenerManager.init();
//        stringSocketClient.setNWorker(12);
//
//        stringSocketClient.addConnectionListener("khanhlv", new ConnectionListener<VOMessage>() {
//            @Override
//            public void connectionOpened(IConnection connection) {
//                System.out.println("open");
//            }
//
//            @Override
//            public void connectionClosed(IConnection connection) {
//                System.out.println("close");
//
//            }
//
//            @Override
//            public void messageReceived(IConnection connection, VOMessage message) {
//
//            }
//
//            @Override
//            public void internalError(IConnection connection, VOMessage message, Throwable cause) {
//
//            }
//
//        });
//        stringSocketClient.connect().get();
//
//        AtomicLong atomicLong= new AtomicLong();
//        while (true) {
//            try {
//                VOMessage voMessage= new VOMessage();
//                voMessage.put("command","command");
//                voMessage.put("transId","transId"+atomicLong.incrementAndGet());
//                Future future = stringSocketClient.send( voMessage);
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//         }

    }
}
