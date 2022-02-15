package com.dm_blinov.udemyshoplist.presentation.shoplist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.dm_blinov.udemyshoplist.R
import com.dm_blinov.udemyshoplist.databinding.ItemShopDisabledBinding
import com.dm_blinov.udemyshoplist.databinding.ItemShopEnabledBinding
import com.dm_blinov.udemyshoplist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemCallback()) {

//    var shopList = emptyList<ShopItem>()
//    set(value) {
//        val callback = ShopListCallback(shopList, value)
//        val diffRes = DiffUtil.calculateDiff(callback)
//        diffRes.dispatchUpdatesTo(this)
//        field = value
//    }

    //var onShopItemLongClickListener: OnShopItemLongClickListener? = null
    var onShopItemLongClick: ((ShopItem) -> Unit?)? = null
    var onShopItemClick: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when(viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }

//        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
//        return ShopItemViewHolder((view))
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding

        when(binding){
            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }
            is ItemShopEnabledBinding -> {
                binding.shopItem = shopItem
            }
        }
        binding.root.setOnLongClickListener {
            //onShopItemLongClickListener?.onShopITemLongClick(item)
            onShopItemLongClick?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClick?.invoke(shopItem)
        }
    }

//    override fun getItemCount(): Int {
//        return shopList.size
//    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(item.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 200
        const val MAX_POOL_SIZE = 10
    }
}