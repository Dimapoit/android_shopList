package com.dm_blinov.udemyshoplist.presentation

import android.app.Application
import com.dm_blinov.udemyshoplist.di.DaggerAppComponent

class ShopListApplication: Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}