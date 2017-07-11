package com.newyorktimes.app.injection.component

import com.newyorktimes.app.features.base.BaseActivity
import com.newyorktimes.app.features.main.MainActivity
import com.newyorktimes.app.injection.PerActivity
import com.newyorktimes.app.injection.module.ActivityModule
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

}
