package com.kp.diameter;

import com.kp.diameter.api.controller.IPeer;
import com.kp.diameter.api.controller.MessageProvider;
import com.kp.diameter.api.controller.impl.DiameterServer;
import com.kp.diameter.api.factory.NetworkFactory;
import com.kp.diameter.api.factory.impl.TcpNettyNetworkFactory;
import com.kp.diameter.api.message.IDMessage;
import com.kp.diameter.api.message.Request;
import com.kp.diameter.api.message.ResultCode;
import com.kp.diameter.api.message.URI;
import com.kp.diameter.api.message.impl.ApplicationId;
import com.kp.diameter.config.LocalInfor;
import com.kp.diameter.config.RemotePeerInfor;
import com.kp.network.protocol.utilities.IPConverter;
import telsoft.gateway.util.perf.AvgCallsPerTimeCounter;
import telsoft.gateway.util.perf.PerfUtils;

import java.util.Timer;
import java.util.TimerTask;

public class ServerJd {
    public static void main(String[] args) throws Exception {
        LocalInfor localInfor = new LocalInfor();
        localInfor.setFirmware(1);
        ;
        localInfor.addCommonApplications(ApplicationId.createByAccAppId(193, 19302));
        localInfor.setProductName("khanhlv");
        localInfor.setTimeout(20000);
        localInfor.setUseUriAsFQDN(true);
        localInfor.setVendorId(193);
        localInfor.setRealmName("khanhlv");
        localInfor.addAddress(IPConverter.InetAddressByIPv6("fe80:0000:0000:0000:9b8b:caf8:4fc1:ca7c"));
        localInfor.setUri(new URI("aaa://server:3868"));

        localInfor.addExtension(NetworkFactory.class.getSimpleName(), TcpNettyNetworkFactory.class);

        RemotePeerInfor remotePeerInfor = new RemotePeerInfor();
        remotePeerInfor.setRealmName("khanhlv");
        remotePeerInfor.setUri(new URI("aaa://client:4868"));
        AvgCallsPerTimeCounter rateSent = new AvgCallsPerTimeCounter();
        rateSent.setSampleSize(50000);
        rateSent.setTimePrecision(PerfUtils.TimePrecision.Sec);
        rateSent.setSampleDuration(60);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("tps :" + rateSent.getValue());
            }
        }, 60000, 60000);

        DiameterServer sctpServer = new DiameterServer();
        sctpServer.setMetadata(localInfor);
        sctpServer.setQueueSize(122);
        sctpServer.setThreadCount(64);
        sctpServer.setNboot(10);

        sctpServer.addPredefinedPeer(remotePeerInfor);
        sctpServer.setMessageProvider(new MessageProvider() {
            @Override
            public void messageReceived(IDMessage idMessage, IPeer iPeer) {
                Request idMessagerq = (Request) idMessage;
                rateSent.incrementCall();

//                System.out.println(idMessage.getId());
                try {
                    iPeer.notifyMessage(((Request) idMessage).createAnswer(ResultCode.SUCCESS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sctpServer.init();
    }
}
