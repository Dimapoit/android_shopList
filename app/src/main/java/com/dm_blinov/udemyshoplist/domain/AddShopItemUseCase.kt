package com.dm_blinov.udemyshoplist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor (
    private val shopListRepository: ShopListRepository
    ) {

    fun addShopItem(shopItem: ShopItem){
        shopListRepository.addShopItem(shopItem)
    }
}