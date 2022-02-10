package com.dm_blinov.udemyshoplist.presentation.shoplist

import androidx.recyclerview.widget.DiffUtil
import com.dm_blinov.udemyshoplist.domain.ShopItem

class ShopListCallback(
    private val oldList: List<ShopItem>,
    private val newList: List<ShopItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    //Возвращает true если id старого и нового объекта равны
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    //Возвращает true если все поля старого и нового объекта равны
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        //return (old.name == new.name && old.count == new.count && old.enabled == new.enabled)
        //Так как у data классов метод equals по умолчанию переопределен, то:
        return old == new

    }

}