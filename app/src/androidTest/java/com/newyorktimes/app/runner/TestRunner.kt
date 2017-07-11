package com.newyorktimes.app.runner

import android.app.Application
import android.content.Context
import com.newyorktimes.app.MvpStarterApplication
import io.appflate.restmock.android.RESTMockTestRunner

class TestRunner : RESTMockTestRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, MvpStarterApplication::class.java.name, context)
    }

}
