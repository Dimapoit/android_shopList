package com.dm_blinov.udemyshoplist.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>
    fun getShopItem(id: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
}