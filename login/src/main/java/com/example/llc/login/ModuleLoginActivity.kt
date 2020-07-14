package com.example.llc.login

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.widget.TextView

import com.example.llc.annotation.BindPath
import com.example.llc.binder.IPersonInterface
import com.example.llc.binder.Person
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.anno.RouterRule

@BindPath("Login/login")
@RouterRule("Login/login")
class ModuleLoginActivity : BaseModuleLoginActivity() {

    private var iPersonInterface: IPersonInterface? = null

    internal var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            iPersonInterface = IPersonInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            iPersonInterface = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindService()
        val loginModule = findViewById<TextView>(R.id.loginModule)
        loginModule.setOnClickListener {
            try {
                iPersonInterface!!.addPerson(Person("1", "Kobe"))
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            //
            Router.create("module://Member/member").activityRoute.open(this)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_module_login
    }

    fun bindService() {
        val intent = Intent()
        intent.component = ComponentName("com.example.llc.member", "com.example.llc.member.MemberService")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }
}
