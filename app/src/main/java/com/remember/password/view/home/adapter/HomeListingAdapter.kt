package com.remember.password.view.home.adapter

import com.remember.password.R
import com.remember.password.base.BaseDiAdapter
import com.remember.password.data.UiRecord

class HomeListingAdapter(private val listItem: List<UiRecord>) : BaseDiAdapter<UiRecord>() {

    override fun getLayoutIdAtPosition(position: Int): Int {
        return R.layout.item_listing_details
    }

    override fun getValueAtIndex(position: Int): UiRecord {
        return listItem[position]
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}