package io.srpc.client;

import java.lang.reflect.Proxy;

/**
 * Created by michaelkraz on 15/7/24.
 */
public final class RpcClient {

    public static <T> T createProxyService( Class<T> iface, String address, int port ) {

        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iface}, new ClientInvokeHandler( iface, address, port ));

    }


}
