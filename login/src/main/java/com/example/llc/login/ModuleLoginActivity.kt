package com.example.llc.login

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.llc.annotation.BindPath
import com.example.llc.binder.IPersonInterface
import com.example.llc.binder.Person
import com.llc.kotlin.example.basic.BaseModuleActivity
import kotlinx.android.synthetic.main.activity_module_login.*

@BindPath("Login/login")
class ModuleLoginActivity : BaseModuleActivity() {

    private var iPersonInterface: IPersonInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindService()

        loginModule.setOnClickListener {
            val result = iPersonInterface?.addPerson(Person("1", "kobe"))
            Log.d("Android_Kotlin_MVP", "result: $result")
            Toast.makeText(this, "result: $result", Toast.LENGTH_LONG).show()
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_module_login


    private fun bindService() {
        val intent = Intent()
        intent.setComponent(ComponentName("com.example.llc.member", "com.example.llc.member.MemberService"))
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
    }

    private val serviceConnection = object : ServiceConnection{

        override fun onServiceDisconnected(name: ComponentName?) {
            iPersonInterface = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iPersonInterface = IPersonInterface.Stub.asInterface(service)
        }
    }
}
