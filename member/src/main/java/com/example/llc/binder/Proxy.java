package com.example.llc.binder;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class Proxy implements IPersonInterface {

    private IBinder mRemote;

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

            mRemote.transact(Stub.TRANSACTION_addPerson, _data, _reply, 0);
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
        List<Person> _result = new ArrayList<>();
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
            _result = null;
        }
        return _result;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
