package jay.rpc.client.proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 客户端代理生成
 * @author Jay.Jia
 */
public class RpcClientFrame {

    /**
     * 远程代理对象
     * @param serviceInterface
     * @param hostname
     * @param port
     * @param <T>
     * @return
     */
    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface,
                                          String hostname,int port){
        final InetSocketAddress addr = new InetSocketAddress(hostname,port);
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),//类加载器
                new Class<?>[]{serviceInterface}//代理接口
                ,new DynProxy(serviceInterface,addr));//进入invoke
    }


    /**
     * 动态代理类
     */
    private static class DynProxy implements InvocationHandler {

        private final Class<?> serviceInterface;
        private final InetSocketAddress addr;

        public DynProxy(Class<?> serviceInterface, InetSocketAddress addr) {
            this.serviceInterface = serviceInterface;
            this.addr = addr;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            Socket socket = null;
            ObjectOutputStream output = null;
            ObjectInputStream input = null;

            try{
                socket = new Socket();
                socket.connect(addr);

                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeUTF(serviceInterface.getName());//方法所在的类
                output.writeUTF(method.getName());//方法的名
                output.writeObject(method.getParameterTypes());//方法的入参类型
                output.writeObject(args);
                output.flush();

                input = new ObjectInputStream(socket.getInputStream());
                return input.readObject();

            }finally{
                if (socket!=null) socket.close();
                if (output!=null) output.close();
                if (input!=null) input.close();
            }

        }
    }

}
