package com.cgl.thrift.client;

import com.cgl.thrift.generated.*;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by guangliang.chen on 2015/12/4.
 */
public class CalculatorClient {
    public static void main(String[] args) {
        try {
            TTransport transport = null;
            if(args == null || args.length == 0 || args[0].contains("simple")) {
                transport = new TSocket("localhost", 9090);
                transport.open();
            }else {
                //todo
            }

            TProtocol protocol = new TBinaryProtocol(transport);
            Calculator.Client client = new Calculator.Client(protocol);

            perform(client);
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void perform(Calculator.Client client) throws TException {
        client.ping();
        System.out.println("ping()");

        int sum = client.add(1,1);
        System.out.println("1+1=" + sum);

        Work work = new Work();
        work.op = Operation.DIVIDE;
        work.num1 = 1;
        work.num2 = 0;
        try {
            int quotient = client.calculate(1, work);
            System.out.println("Wokao we can devide by 0");
        } catch (InvalidOperation io) {
            System.out.println("Invalid operation: " + io.whatOp + ",reason is:" + io.why);
        }

        work.op = Operation.SUBTRACT;
        work.num1 = 15;
        work.num2 = 10;
        try {
            int diff = client.calculate(1, work);
            System.out.println("15-10=" + diff);
        } catch (InvalidOperation io) {
            System.out.println("Invalid operation: " + io.whatOp + ",reason is:" + io.why);
        }

        //todo
//        SharedStruct  log = client.getS
        System.out.println("Check log: is empty{TODO...}");
    }
}
