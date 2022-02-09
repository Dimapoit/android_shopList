package com.dm_blinov.udemyshoplist.presentation.shopitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dm_blinov.udemyshoplist.R
import com.dm_blinov.udemyshoplist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    lateinit var shopItemViewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        //val shopItem: ShopItem = shopItemViewModel.getShopItem(2)
    }

}