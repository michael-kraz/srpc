package io.srpc.client;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by michaelkraz on 15/7/24.
 */
public class RpcClientTest {

    @Test
    public void test() {

        Object service = RpcClient.createProxyService(Object.class, "localhost", 14325);
        assertNotNull(service);

    }

}
