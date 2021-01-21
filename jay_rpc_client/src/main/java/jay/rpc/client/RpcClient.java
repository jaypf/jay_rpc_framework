package jay.rpc.client;

import jay.rpc.client.proxy.RpcClientFrame;
import jay.rpc.service.SendService;

public class RpcClient {

    public static void main(String[] args) {
        SendService sendSms = RpcClientFrame.getRemoteProxyObj(SendService.class,"127.0.0.1",9189);
        System.out.println("Send msg: "+ sendSms.sendMsg("I am Client"));
    }
}
