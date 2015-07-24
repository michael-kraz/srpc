package io.srpc.server;

/**
 * Created by michaelkraz on 15/7/24.
 */
public class RpcServer {


    private String ipAddress;
    private int portStart;
    private int portEnd;

    public RpcServer(String ipAddress) {
        this(ipAddress, 0, 0);
    }


    public RpcServer(String ipAddress, int port) {
        this(ipAddress, port, port);
    }

    public RpcServer(String ipAddress, int portStart, int portEnd) {
        this.ipAddress = ipAddress;
        this.portStart = portStart;
        this.portEnd = portEnd;
    }

    public void registerService(Class<?> iface, Object instance, String alias) {

    }

    public void registerService(Class<?> iface, Object instance ){
        registerService( iface, instance, iface.getName() );
    }


    public void start(){

    }

    public void shutdown(){

    }



}
