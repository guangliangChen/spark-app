package com.cgl.thrift.impl;

import com.cgl.thrift.generated.Hello;
import org.apache.thrift.TException;

/**
 * Created by guangliang.chen on 2015/12/4.
 */
public class HelloImpl implements Hello.Iface {

    @Override
    public String helloString(String para) throws TException {
        System.out.println("HelloImpl.helloString is called, and the para is :" + para);
        return para;
    }

    @Override
    public int helloInt(int para) throws TException {
        return para;
    }

    @Override
    public boolean helloBoolean(boolean para) throws TException {
        return para;
    }

    @Override
    public void helloVoid() throws TException {
        System.out.println("helloVoid is executed,return void.");
    }

    @Override
    public String helloNull() throws TException {
        return "helloNull";
    }
}
