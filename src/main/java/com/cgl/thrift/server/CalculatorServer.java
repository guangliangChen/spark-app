package com.cgl.thrift.server;

import com.cgl.thrift.generated.Calculator;
import com.cgl.thrift.handler.CalculatorHandler;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.*;

import java.net.InetAddress;

import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
/**
 * Created by guangliang.chen on 2015/12/4.
 */
public class CalculatorServer {
    public static CalculatorHandler handler;
    public static Calculator.Processor processor;

    public static void main(String[] args) {
        handler = new CalculatorHandler();
        processor = new Calculator.Processor(handler);

        Runnable simple = new Runnable() {
            @Override
            public void run() {
                simple(processor);
            }
        };
//        Runnable secure = new Runnable() {
//            @Override
//            public void run() {
//                secure(processor);
//            }
//        };
        new Thread(simple).start();
//        new Thread(secure).start();
    }

    private static void simple(Calculator.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server ...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    private static void secure(Calculator.Processor processor) {
        try {
            org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters parameters = new org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters();
            // The Keystore contains the private key
//            params.setKeyStore("../../lib/java/test/.keystore", "thrift", null, null);
            parameters.setKeyStore("", "thrift", null, null);

            TServerTransport serverTransport = TSSLTransportFactory.getServerSocket(9091, 0, null, parameters);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the secure server ...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
