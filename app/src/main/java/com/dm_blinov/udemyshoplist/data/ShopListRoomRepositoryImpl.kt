package com.dm_blinov.udemyshoplist.data

import androidx.lifecycle.*
import com.dm_blinov.udemyshoplist.domain.ShopItem
import com.dm_blinov.udemyshoplist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRoomRepositoryImpl @Inject constructor(

    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {

//    override fun getShopList(): LiveData<List<ShopItem>> =
//        MediatorLiveData<List<ShopItem>>().apply {
//            addSource(shopListDao.getShopList()) {
//                value = mapper.mapListDbModelToListEntity(it)
//            }
//        }

    override fun getShopList(): LiveData<List<ShopItem>> =
//        Transformations.map(shopListDao.getShopList()) {
//            mapper.mapListDbModelToListEntity(it)
        shopListDao.getShopList().map {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }


    override suspend fun getShopItem(id: Int): ShopItem {
        val shopDbModel = shopListDao.getShopItem(id)
        return mapper.mapDbModelToEntity(shopDbModel)
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }
}