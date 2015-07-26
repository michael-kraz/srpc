package io.srpc.client;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by michaelkraz on 15/7/24.
 */
public class RpcClientTest {

    @Test
    public void test() {

        Iface1 service = RpcClient.createProxyService(Iface1.class, "localhost", 14325);
        assertNotNull(service);

    }

}


interface Iface1{

}
