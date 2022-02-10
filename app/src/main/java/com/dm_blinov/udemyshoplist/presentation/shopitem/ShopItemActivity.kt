package com.dm_blinov.udemyshoplist.presentation.shopitem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dm_blinov.udemyshoplist.databinding.ActivityShopItemBinding
import com.dm_blinov.udemyshoplist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var shopItemViewModel: ShopItemViewModel
    private lateinit var _binding: ActivityShopItemBinding

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        parseIntent()
        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)

        when (screenMode) {
            MODE_ADD -> launchAddScreenMode()
            MODE_EDIT -> launchEditScreenMode()
        }
        addTextChangedListeners()
        addObserve()
    }

    private fun addObserve() {
        shopItemViewModel.errorInputName.observe(this) {
            val message = if (it) "Invalid name" else null
            _binding.etName.error = message
        }
        shopItemViewModel.errorInputCount.observe(this) {
            val message = if (it) "Invalid count" else null
            _binding.etCount.error = message
        }
        shopItemViewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun addTextChangedListeners() {
        _binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputName()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        _binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputCount()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun launchAddScreenMode() {
        _binding.saveButton.setOnClickListener {

            val name = _binding.etName.text.toString()
            val count = _binding.etCount.text.toString()
            shopItemViewModel.addShopItem(name, count)

        }
    }

    private fun launchEditScreenMode() {
        shopItemViewModel.getShopItem(shopItemId)
        shopItemViewModel.shopItem.observe(this) {
            _binding.etName.setText(it.name)
            _binding.etCount.setText(it.count.toString())
        }
        _binding.saveButton.setOnClickListener {
            val name = _binding.etName.text?.toString()
            val count = _binding.etCount.text?.toString()
            shopItemViewModel.editShopItem(name, count)
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