package com.cgl.thrift.server;

import com.cgl.thrift.impl.HelloImpl;
import com.cgl.thrift.generated.Hello;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by guangliang.chen on 2015/12/4.
 */
public class HelloServer {
    public static void main(String[] args) {
        HelloServer srv = new HelloServer();
        srv.start();
    }
    private void start() {
        try {
            //transport layer
            TServerSocket serverTransport = new TServerSocket(7911);
            //protocol layer
            TBinaryProtocol.Factory portFactory = new TBinaryProtocol.Factory(true, true);
            //processor layer
            Hello.Processor processor = new Hello.Processor(new HelloImpl());

            //server layer
//            TServer server = new TThreadPoolServer(processor, serverTransport, portFactory);//not supported by thrift0.9.3
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor).protocolFactory(portFactory));
            System.out.println("Starting server on port 7911 ...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
