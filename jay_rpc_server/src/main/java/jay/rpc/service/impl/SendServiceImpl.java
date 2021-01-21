package jay.rpc.service.impl;

import jay.rpc.service.SendService;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger
public class SendServiceImpl implements SendService {


    @Override
    public boolean sendMsg(String msg) {
        System.out.printf("server 收到消息:"+msg);
        return true;
    }


}
