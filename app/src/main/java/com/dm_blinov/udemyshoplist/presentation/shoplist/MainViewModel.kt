package com.dm_blinov.udemyshoplist.presentation.shoplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm_blinov.udemyshoplist.domain.DeleteShopItemUseCase
import com.dm_blinov.udemyshoplist.domain.EditShopItemUseCase
import com.dm_blinov.udemyshoplist.domain.GetShopListUseCase
import com.dm_blinov.udemyshoplist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

    val shopList = getShopListUseCase.getShopList()

    fun editShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            val item = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(item)
        }

    }

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }

    }

}