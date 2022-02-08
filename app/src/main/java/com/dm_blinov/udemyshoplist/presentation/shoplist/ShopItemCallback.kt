package com.dm_blinov.udemyshoplist.presentation.shoplist

import androidx.recyclerview.widget.DiffUtil
import com.dm_blinov.udemyshoplist.domain.ShopItem

class ShopItemCallback : DiffUtil.ItemCallback<ShopItem>() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}