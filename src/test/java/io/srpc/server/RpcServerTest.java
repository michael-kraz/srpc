package io.srpc.server;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by michaelkraz on 15/7/24.
 */
public class RpcServerTest {

    @Test
    public void test(){

        RpcServer rpcServer = new RpcServer( "localhost" );
        rpcServer.registerService( Object.class, new Object() );
        rpcServer.start();
        rpcServer.shutdown();

    }

}
