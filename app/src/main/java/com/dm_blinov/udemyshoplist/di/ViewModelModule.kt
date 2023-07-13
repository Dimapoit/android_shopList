package com.dm_blinov.udemyshoplist.di

import androidx.lifecycle.ViewModel
import com.dm_blinov.udemyshoplist.presentation.shopitem.ShopItemViewModel
import com.dm_blinov.udemyshoplist.presentation.shoplist.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindsShopItemViewModel(viewModel: ShopItemViewModel): ViewModel
}