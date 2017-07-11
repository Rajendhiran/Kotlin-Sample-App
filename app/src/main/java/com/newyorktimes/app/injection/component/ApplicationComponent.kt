package com.newyorktimes.app.injection.component

import com.newyorktimes.app.data.DataManager
import com.newyorktimes.app.data.remote.MvpStarterService
import com.newyorktimes.app.injection.ApplicationContext
import com.newyorktimes.app.injection.module.ApplicationModule
import android.app.Application
import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun mvpBoilerplateService(): MvpStarterService
}
