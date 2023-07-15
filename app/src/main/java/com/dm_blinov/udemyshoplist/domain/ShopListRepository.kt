package com.dm_blinov.udemyshoplist.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun getShopItem(id: Int): ShopItem
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
}