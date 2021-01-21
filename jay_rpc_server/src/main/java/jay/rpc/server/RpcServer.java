package jay.rpc.server;

import jay.rpc.server.proxy.RpcServerFrame;
import jay.rpc.service.SendService;
import jay.rpc.service.impl.SendServiceImpl;

/**
 * 服务端
 * @author Jay.Jia
 */
public class RpcServer {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                try{
                    RpcServerFrame serviceServer = new RpcServerFrame(9189);
                    serviceServer.registerSerive(SendService.class.getName(), SendServiceImpl.class);
                    serviceServer.startService();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
