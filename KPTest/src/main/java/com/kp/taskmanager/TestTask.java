package com.kp.taskmanager;

import com.kp.common.data.vo.VObject;
import com.kp.network.protocol.connection.IConnection;
import com.kp.taskmanager.network.codec.VOMessage;
import com.kp.taskmanager.thread.ManageableThread;

import java.util.concurrent.Future;

public class TestTask extends ManageableThread {
    String id;
    String name;

    @Override
    public void reloadConfig() {

    }

    @Override
    public void messageReceived(VOMessage voMessage, IConnection iConnection) {

    }

    @Override
    public Future<ManageableThread> init() throws Exception {
        return null;
    }

    @Override
    public Future<ManageableThread> process() throws Exception {
        Thread.sleep(10000);
        return null;
//     logMonitor("name la"+name+":"+System.currentTimeMillis());
    }

    @Override
    public void fillParameter(VObject vObject) throws Exception {
        name = vObject.getString("name");
        name.length();

    }
}
