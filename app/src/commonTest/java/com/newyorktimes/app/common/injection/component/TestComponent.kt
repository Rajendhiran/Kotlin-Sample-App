package com.newyorktimes.app.common.injection.component

import com.newyorktimes.app.common.injection.module.ApplicationTestModule
import com.newyorktimes.app.injection.component.ApplicationComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface  TestComponent : ApplicationComponent