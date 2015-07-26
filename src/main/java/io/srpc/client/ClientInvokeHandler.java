package io.srpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by michaelkraz on 15/7/26.
 */
public class ClientInvokeHandler<T> implements InvocationHandler {

    public ClientInvokeHandler(Class<T> iface, String address, int port) {

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }

}
