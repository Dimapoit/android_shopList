package com.dm_blinov.udemyshoplist.presentation.shoplist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dm_blinov.udemyshoplist.data.ShopListRepositoryImpl
import com.dm_blinov.udemyshoplist.domain.DeleteShopItemUseCase
import com.dm_blinov.udemyshoplist.domain.EditShopItemUseCase
import com.dm_blinov.udemyshoplist.domain.GetShopListUseCase
import com.dm_blinov.udemyshoplist.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase =  GetShopListUseCase(repository)
    private val deleteShopItemUseCase =  DeleteShopItemUseCase(repository)
    private val editShopItemUseCase =  EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun editShopItem(shopItem: ShopItem){

        val item = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(item)
    }

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

}