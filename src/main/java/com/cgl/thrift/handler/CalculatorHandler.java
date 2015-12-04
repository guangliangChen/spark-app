package com.cgl.thrift.handler;

import com.cgl.thrift.generated.Calculator;
import com.cgl.thrift.generated.Calculator.Iface;
import com.cgl.thrift.generated.InvalidOperation;
import com.cgl.thrift.generated.SharedStruct;
import com.cgl.thrift.generated.Work;
import org.apache.thrift.TException;
import scala.tools.cmd.gen.AnyVals;

import java.util.HashMap;

/**
 * Created by guangliang.chen on 2015/12/4.
 */
public class CalculatorHandler implements Iface {
    private HashMap<Integer, SharedStruct> log;

    public CalculatorHandler() {
        this.log = new HashMap<Integer, SharedStruct>();
    }

    @Override
    public void ping() throws TException {
        System.out.println("ping()");
    }

    @Override
    public int add(int num1, int num2) throws TException {
        System.out.println("add(" + num1 + "," + num2 + ")");
        return num1 + num2;
    }

    @Override
    public int calculate(int logid, Work work) throws InvalidOperation, TException {
        System.out.println("calculate(" + logid + ",{" + work.num1 + "," + work.op + "," + work.num2 + "})");
        int val = 0;
        switch (work.op) {
            case ADD:
                val = work.num1 + work.num2;
                break;
            case SUBTRACT:
                val = work.num1 - work.num2;
                break;
            case MULTIPLY:
                val = work.num1 * work.num2;
                break;
            case DIVIDE:
                if (work.num2 == 0) {
                    InvalidOperation io = new InvalidOperation();
                    io.whatOp = work.op.getValue();
                    io.why = "Cannot divide by 0";
                    throw io;
                }
                val = work.num1 / work.num2;
                break;
            default:
                InvalidOperation io = new InvalidOperation();
                io.whatOp = work.op.getValue();
                io.why = "Unknow operation";
                throw io;
        }
        SharedStruct sharedStruct = new SharedStruct();
        sharedStruct.key = logid;
        sharedStruct.value = Integer.toString(val);
        log.put(logid, sharedStruct);
        return val;
    }

    public SharedStruct getStruct(Integer key) {
        System.out.println("getStruct(" + key + ")");
        return log.get(key);
    }

    @Override
    public void zip() throws TException {
        System.out.println("zip()");
    }
}
