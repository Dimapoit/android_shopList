package com.dm_blinov.udemyshoplist.domain

import androidx.lifecycle.MutableLiveData

interface ShopListRepository {

    fun getShopList(): MutableLiveData<List<ShopItem>>
    fun getShopItem(id: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
}