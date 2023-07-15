package com.dm_blinov.udemyshoplist.domain

import javax.inject.Inject

class GetShopItemUseCase  @Inject constructor (
    private val shopListRepository: ShopListRepository
    ) {

    suspend fun getShopItem(id: Int): ShopItem {
        return shopListRepository.getShopItem(id)
    }
}