package com.dm_blinov.udemyshoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dm_blinov.udemyshoplist.R
import com.dm_blinov.udemyshoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = emptyList<ShopItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    //var onShopItemLongClickListener: OnShopItemLongClickListener? = null
    var onShopItemLongClick: ((ShopItem) -> Unit?)? = null
    var onShopItemClick: ((ShopItem) -> Unit)? = null

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

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
        val shopItem = shopList[position]

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

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if(item.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    interface OnShopItemLongClickListener {
        fun onShopITemLongClick(shopItem: ShopItem)
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 200
        const val MAX_POOL_SIZE = 10
    }
}