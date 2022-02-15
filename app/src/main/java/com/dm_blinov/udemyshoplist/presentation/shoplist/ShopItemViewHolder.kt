package com.dm_blinov.udemyshoplist.presentation.shoplist

import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dm_blinov.udemyshoplist.R
import com.dm_blinov.udemyshoplist.databinding.ItemShopDisabledBinding

//class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    val tvName = view.findViewById<TextView>(R.id.tv_name)
//    val tvCount = view.findViewById<TextView>(R.id.tv_count)
//}

class ShopItemViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)