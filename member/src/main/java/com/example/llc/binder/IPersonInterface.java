package com.example.llc.binder;

import android.os.IInterface;

import java.util.List;

public interface IPersonInterface extends IInterface {

    String DESCRIPTOR = "com.example.llc.binder.IPersonInterface";

    void addPerson(Person person) throws android.os.RemoteException;

    List<Person> getPersons() throws android.os.RemoteException;
}
