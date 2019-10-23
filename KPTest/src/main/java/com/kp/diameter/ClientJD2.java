package com.kp.diameter;

import com.kp.diameter.api.controller.impl.DiameterClient;
import com.kp.diameter.api.factory.NetworkFactory;
import com.kp.diameter.api.factory.impl.TcpNettyNetworkFactory;
import com.kp.diameter.api.message.IDMessage;
import com.kp.diameter.api.message.URI;
import com.kp.diameter.api.message.impl.ApplicationId;
import com.kp.diameter.api.message.impl.parser.Avp;
import com.kp.diameter.api.message.impl.parser.AvpSet;
import com.kp.diameter.config.LocalInfor;
import com.kp.diameter.config.RemotePeerInfor;
import com.kp.network.protocol.utilities.IPConverter;
import telsoft.gateway.util.perf.AvgCounter;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.kp.diameter.api.message.impl.parser.Avp.TERMINAL_INFORMATION;

public class ClientJD2 {
    public static void main(String[] args) throws Exception {
        LocalInfor localInfor = new LocalInfor();
        localInfor.setFirmware(1);
        ;
        localInfor.addCommonApplications(ApplicationId.createByAccAppId(193, 19302));
        localInfor.setProductName("khanhlv");
        localInfor.setTimeout(20000);
        localInfor.setUseUriAsFQDN(false);
        localInfor.setVendorId(193);
        localInfor.setRealmName("khanhlv");
        localInfor.addExtension(NetworkFactory.class.getSimpleName(), TcpNettyNetworkFactory.class);

        localInfor.addAddress(IPConverter.InetAddressByIPv6("10:0000:0000:0000:0000:0000:197:68"));
        localInfor.setUri(new URI("aaa://client:4868"));
        RemotePeerInfor remotePeerInfor = new RemotePeerInfor();
        remotePeerInfor.setRealmName("mobicents.org");
        remotePeerInfor.setUri(new URI("aaa://client:3868"));
        remotePeerInfor.setAddress(new InetSocketAddress("10:0000:0000:0000:0000:0000:197:69", 3868));

        DiameterClient diameterClient = new DiameterClient();
        diameterClient.setMetadata(localInfor);
        diameterClient.setQueueSize(122);
        diameterClient.setThreadCount(122);
        diameterClient.addPredefinedPeer(remotePeerInfor);

        diameterClient.init();
        AvgCounter responseTime = new AvgCounter();
        responseTime.setSampleSize(50000);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("reponsetime :" + responseTime.getValue());
            }
        }, 60000, 60000);

        int n = 2;
        for (int i = 0; i < n; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {

                            IDMessage request = diameterClient.createRequest(324, ApplicationId.createByAccAppId(193, 19302), "dproxyhcm.mobifone.vn", "sadamsndskand" + System.currentTimeMillis());
                            request.getAvps().addAvp(Avp.USER_NAME, "1212323123", true, false, true);
                            request.getAvps().removeAvp(293);

                            AvpSet a = request.getAvps().addGroupedAvp(TERMINAL_INFORMATION, 10415, true, false);
                            a.addAvp(Avp.TGPP_IMEI, "12321312312", 10415, true, false, true);
                            long timestart = System.currentTimeMillis();
                            diameterClient.sendMessage(request).get(100, TimeUnit.MILLISECONDS);
                            long timeEnd = System.currentTimeMillis();
                            responseTime.addValue(timeEnd - timestart);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }).start();


        }
    }
}
