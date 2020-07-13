package com.example.llc.login;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import com.example.llc.annotation.BindPath;
import com.example.llc.binder.IPersonInterface;
import com.example.llc.binder.Person;
import com.llc.kotlin.example.basic.BaseModuleActivity;

import org.jetbrains.annotations.Nullable;

@BindPath("Login/login")
public class ModuleLoginActivity extends BaseModuleActivity {

    private IPersonInterface iPersonInterface = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService();
        TextView loginModule = findViewById(R.id.loginModule);
        loginModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iPersonInterface.addPerson(new Person("1", "Kobe"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_module_login;
    }

    public void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.llc.member", "com.example.llc.member.MemberService"));
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iPersonInterface = IPersonInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iPersonInterface = null;
        }
    };
}
