package com.example.llc.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;

/**
 * com.example.llc.binder.Stub
 *
 * @author liulongchao
 * @since 2020-07-09
 *
 * */
public abstract class Stub extends Binder implements IPersonInterface {

    static final int TRANSACTION_addPerson = IBinder.FIRST_CALL_TRANSACTION;
    static final int TRANSACTION_getPersons = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    protected Stub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static IPersonInterface asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (iin instanceof IPersonInterface) {
            return ((IPersonInterface) iin);
        }
        return new Proxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int code, @NonNull Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_addPerson: {
                data.enforceInterface(DESCRIPTOR);
                Person _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Person.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addPerson(_arg0);
                reply.writeNoException();
                return true;
            }
            case TRANSACTION_getPersons: {
                data.enforceInterface(DESCRIPTOR);
                java.util.List<Person> _result = this.getPersons();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }
}
