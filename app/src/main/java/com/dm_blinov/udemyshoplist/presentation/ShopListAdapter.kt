package com.dm_blinov.udemyshoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dm_blinov.udemyshoplist.R
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

    var count = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when(viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder((view))
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("onCreateViewHolder", "onBindViewHolder: ${++count}")
        val shopItem = getItem(position)

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            //onShopItemLongClickListener?.onShopITemLongClick(item)
            onShopItemLongClick?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
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