package com.dm_blinov.udemyshoplist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dm_blinov.udemyshoplist.domain.ShopItem
import com.dm_blinov.udemyshoplist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private val shopList = sortedSetOf<ShopItem>({o1 , o2 -> o1.id.compareTo(o2.id)})

   private var autoincrement = 0

//    init {
//        for(i in 1 until 10) {
//            val item = ShopItem("Name $i", i, Random.nextBoolean())
//            addShopItem(item)
//        }
//    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override suspend fun getShopItem(id: Int): ShopItem {

        return shopList.find { id == it.id } ?: throw RuntimeException("Element with id $id not found" )
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoincrement++
        }
        shopList.add(shopItem)
        setShopListLD()
    }

    override suspend fun editShopItem(shopItem: ShopItem) {

        val oldShopItem = getShopItem(shopItem.id)
        print(oldShopItem.toString())
        shopList.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        setShopListLD()
    }
    fun setShopListLD(){
        shopListLD.value = shopList.toList()
    }
}