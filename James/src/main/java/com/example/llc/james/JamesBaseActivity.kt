package com.example.llc.james;

import android.app.Activity
import android.content.res.Resources

open abstract class JamesBaseActivity: Activity() {

    override fun getResources(): Resources {
        if (application != null && application.resources != null) {
            return application.resources
        }
        return super.getResources()
    }

}
