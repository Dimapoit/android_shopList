package com.dm_blinov.udemyshoplist.domain

import javax.inject.Inject

class EditShopItemUseCase  @Inject constructor (
    private val shopListRepository: ShopListRepository
    ) {

    fun editShopItem(shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}