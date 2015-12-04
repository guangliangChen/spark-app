package com.cgl.thrift.client;

import com.cgl.thrift.generated.Hello;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by guangliang.chen on 2015/12/4.
 */
public class HelloClient {
    public static void main(String[] args) {
        //transport layer
        TTransport transport = new TSocket("localhost", 7911);
        //protocol layer
        TProtocol protocol = new TBinaryProtocol(transport);
        //client layer(iniliazteby protocol)
        Hello.Client client = new Hello.Client(protocol);
        try {
            transport.open();
            System.out.println("HelloClient call method helloString");
            //service request
            client.helloString("Read the fucking source!!");
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
