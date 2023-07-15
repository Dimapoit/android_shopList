package com.dm_blinov.udemyshoplist.domain

import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor (
    private val shopListRepository: ShopListRepository
    ) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}