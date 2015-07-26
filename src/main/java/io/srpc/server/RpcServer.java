package io.srpc.server;

import io.srpc.util.AssertUtil;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerBossPool;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioWorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by michaelkraz on 15/7/24.
 */
public class RpcServer {


    private static Logger LOG = LoggerFactory.getLogger( RpcServer.class );


    private String host;
    private int portStart;
    private int portEnd;
    private int numOfWorkers;
    private int boundPort;
    private ChannelFactory channelFactory;
    private Channel serverChannel;
    private RpcServiceRegistry rpcServiceRegistry;

    public RpcServer(String host) {
        this(host, 0, 0);
    }


    public RpcServer(String host, int port) {
        this(host, port, port);
    }

    public RpcServer(String host, int portStart, int portEnd, int numOfWorkers) {

        AssertUtil.assertNotNull(host, "Host is null");
        AssertUtil.assertTrue(portEnd >= portStart && portStart >= 0, "Port range not valid: [ %d, %d ]", portStart, portEnd);
        AssertUtil.assertTrue( numOfWorkers > 0, "Number of workers %d <=0 ", numOfWorkers );

        this.host = host;
        this.portStart = portStart;
        this.portEnd = portEnd;
        this.numOfWorkers = numOfWorkers;
        this.rpcServiceRegistry = RpcServiceRegistry.newRegistry();
    }

    public RpcServer(String host, int portStart, int portEnd) {
        this(host,portStart,portEnd, Runtime.getRuntime().availableProcessors());
    }

    public void registerService(Class<?> iface, Object instance, String alias) {
        rpcServiceRegistry.register(iface, instance, alias);
    }

    public void registerService(Class<?> iface, Object instance ){
        registerService(iface, instance, iface.getName());
    }


    public void start(){

        LOG.info("Starting rpc server:");

        channelFactory = new NioServerSocketChannelFactory(
                new NioServerBossPool(Executors.newCachedThreadPool(), 1),
                new NioWorkerPool(Executors.newCachedThreadPool(),numOfWorkers));

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.setFactory(channelFactory);
        bootstrap.setPipelineFactory(createPipelineFactory());

        bindPortRange(bootstrap, host, portStart, portEnd);

    }

    public void shutdown(){

        LOG.info("Shutting down rpc server: ");

        closeChannel();

        channelFactory.shutdown();

    }

    public int getBoundPort() {
        return boundPort;
    }

    private void closeChannel() {

        if (serverChannel == null || !serverChannel.isOpen()) {
            return;
        }

        LOG.info("Close server socket channel asynchronous.");
        ChannelFuture close = serverChannel.close();
        close.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                LOG.info("Server socket channel closed.");
            }
        });
    }

    private void bindPortRange(ServerBootstrap bootstrap, String host, int portStart, int portEnd) {

        for(int port = portStart; port <= portEnd; port ++){

            boolean success = tryBind(bootstrap, host, port);

            if(success){
                LOG.info("Bind success to {}:{}", host, port);
                this.boundPort = port;
                break;
            }

        }

    }

    private boolean tryBind(ServerBootstrap bootstrap, String host, int port) {

        try {
            serverChannel = bootstrap.bind(new InetSocketAddress(host, port));
            return true;
        } catch (Exception e) {
            LOG.warn("Try bind {}:{} failed, try another.", host, port);
        }

        return false;
    }

    private ChannelPipelineFactory createPipelineFactory() {

        return new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {

                ChannelPipeline pipeline = Channels.pipeline();

                pipeline.addLast("byteAssembler", new ByteAssembleHandler());
                pipeline.addLast("byteLengthAppender", new ByteLengthAppender());
                pipeline.addLast("objectDeserializer", new ObjectDeserializer());
                pipeline.addLast("objectSerializer", new ObjectSerializer());
                pipeline.addLast("serviceInvoker", new ServiceInvoker());

                return pipeline;
            }
        };
    }




}
