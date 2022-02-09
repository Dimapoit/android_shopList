package com.dm_blinov.udemyshoplist.presentation.shopitem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dm_blinov.udemyshoplist.R

class ShopItemActivity : AppCompatActivity() {

    lateinit var shopItemViewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        val mode = intent.getStringExtra(EXTRA_MODE)

        val id = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, 0)
        Log.d("ShopItemAct", mode.toString())
        Log.d("ShopItemAct", id.toString())
    }

    companion object {
        private const val EXTRA_MODE = "EXTRA_MODE"
        private const val EXTRA_SHOP_ITEM_ID = "EXTRA_SHOP_ITEM_ID"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val MODE_ADD = "MODE_ADD"

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }

    }

}