package io.srpc.server;

/**
 * Created by michaelkraz on 15/7/26.
 */
public class RpcServiceRegistry {

    public static RpcServiceRegistry newRegistry() {
        return new RpcServiceRegistry();
    }

    public void register(Class<?> iface, Object instance, String alias) {

    }
}
