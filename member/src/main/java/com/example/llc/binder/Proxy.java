package com.example.llc.binder;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/**
 *  com.example.llc.binder.Proxy
 *
 * @author liulongchao
 * @since 2020-07-09
 *
 * 客户端代理对象，封装了 BinderProxy (Stub就是 BinderProxy 的具体实现)
 *
 * */
public class Proxy implements IPersonInterface {

    private IBinder mRemote; // 具体实例是 Stub

    Proxy(IBinder remote) {
        this.mRemote = remote;
    }

    public String getInterfaceDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    public void addPerson(Person person) throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            if (person != null) {
                _data.writeInt(1);
                person.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            // 数据传输 分为同步和异步
            // 0 表示同步
            // IBinder.FLAG_ONEWAY 表示异步
            mRemote.transact(Stub.TRANSACTION_addPerson, _data, _reply, IBinder.FLAG_ONEWAY);
            _reply.readException();
        } catch (Exception e) {
            //
        } finally {
            _data.recycle();
            _reply.recycle();
        }

    }

    @Override
    public List<Person> getPersons() throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        List<Person> _result;
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            mRemote.transact(Stub.TRANSACTION_getPersons, _data, _reply, 0);
            _reply.readException();
            _result = _reply.createTypedArrayList(com.example.llc.binder.Person.CREATOR);
            return _result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            _data.recycle();
            _reply.recycle();
        }
        return null;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
