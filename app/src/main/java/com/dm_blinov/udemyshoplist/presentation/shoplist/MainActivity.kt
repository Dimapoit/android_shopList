package com.dm_blinov.udemyshoplist.presentation.shoplist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dm_blinov.udemyshoplist.R

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var shopListAdapter: ShopListAdapter

    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            //shopListAdapter.shopList = it
            shopListAdapter.submitList(it)
        }
    }

    fun initRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()

        rvShopList.adapter = shopListAdapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        initLongClickListener()
        initClickListener()
        InitSwipeListener(rvShopList)
    }

    private fun InitSwipeListener(rvShopList: RecyclerView) {
        //Создаем simpleCallBack для удаления с помощью свайпа
        val simpleCallBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        //Создаем объект класса ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        //Прикрепляем его к RecyclerView
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun initClickListener() {
        shopListAdapter.onShopItemClick = {
            Log.d("onShopItemClick", it.toString())
        }
    }

    private fun initLongClickListener() {
        shopListAdapter.onShopItemLongClick = {
            viewModel.editShopItem(it)
        }
    }
}