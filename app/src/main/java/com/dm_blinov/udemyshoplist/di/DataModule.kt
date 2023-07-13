package com.dm_blinov.udemyshoplist.di

import android.app.Application
import com.dm_blinov.udemyshoplist.data.AppDatabase
import com.dm_blinov.udemyshoplist.data.ShopListDao
import com.dm_blinov.udemyshoplist.data.ShopListRoomRepositoryImpl
import com.dm_blinov.udemyshoplist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRoomRepositoryImpl): ShopListRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}