package com.remember.password.view.home.adapter

import com.remember.password.R
import com.remember.password.base.BaseDiAdapter
import com.remember.password.data.UiRecord

class HomeListingAdapter(private val listItem: List<UiRecord>) : BaseDiAdapter<UiRecord>() {

    override fun getLayoutIdAtPosition(position: Int): Int {
        return if (listItem[position].isHeader) R.layout.item_pwd_listing_header else R.layout.item_pwd_listing
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.isHeader = listItem[position].isHeader
        super.onBindViewHolder(holder, position)
    }

    override fun getValueAtIndex(position: Int): UiRecord {
        return listItem[position]
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}