package com.example.llc.member;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.llc.binder.Person;
import com.example.llc.binder.Stub;

import java.util.ArrayList;
import java.util.List;

public class MemberService extends Service {

    private ArrayList<Person> personArrayList;

    public MemberService() {
        personArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Stub() {
            @Override
            public void addPerson(Person person) throws RemoteException {
                Log.d("Android_Kotlin_MVP", "id: " + person.getId() + ", name: " + person.getName());
                personArrayList.add(person);
            }

            @Override
            public List<Person> getPersons() throws RemoteException {
                return personArrayList;
            }
        };
    }
}
