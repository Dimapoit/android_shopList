package com.dm_blinov.udemyshoplist.di

import android.app.Application
import com.dm_blinov.udemyshoplist.presentation.shopitem.ShopItemFragment
import com.dm_blinov.udemyshoplist.presentation.shoplist.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ):AppComponent
    }
}