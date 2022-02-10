package com.dm_blinov.udemyshoplist.presentation.shopitem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dm_blinov.udemyshoplist.R
import com.dm_blinov.udemyshoplist.databinding.ActivityShopItemBinding
import com.dm_blinov.udemyshoplist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    //    private lateinit var shopItemViewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()

        if(savedInstanceState == null) {
            val fragment = when (screenMode) {
                MODE_ADD -> ShopItemFragment.newInstanceAddFragment()
                MODE_EDIT -> ShopItemFragment.newInstanceEditFragment(shopItemId)
                else -> throw RuntimeException("Unknown screen mode $screenMode")
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.shop_item_container, fragment)
                .commit()
        }

    }

    //Проверка наличия и корректности параметров intent
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Param is not absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        if (mode == MODE_EDIT && !intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
            throw RuntimeException("Param shop item id is absent")
        }
        screenMode = mode
        shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
    }

    //NewIntent
    companion object {
        private const val EXTRA_MODE = "EXTRA_MODE"
        private const val EXTRA_SHOP_ITEM_ID = "EXTRA_SHOP_ITEM_ID"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val MODE_ADD = "MODE_ADD"
        private const val MODE_UNKNOWN = ""

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