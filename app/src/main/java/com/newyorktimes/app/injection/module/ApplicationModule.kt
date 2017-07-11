package com.newyorktimes.app.injection.module

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import com.newyorktimes.app.data.remote.MvpStarterService
import com.newyorktimes.app.data.remote.MvpStarterServiceFactory
import com.newyorktimes.app.injection.ApplicationContext

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideMvpStarterService(): MvpStarterService {
        return MvpStarterServiceFactory.makeStarterService()
    }
}
