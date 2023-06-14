package com.meiling.account.widget

import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R

class SS : BaseLoadMoreView() {

    override fun getRootView(parent: ViewGroup): View =
        parent.getItemView(R.layout.ss)

    override fun getLoadingView(holder: BaseViewHolder): View =
        holder.getView(R.id.view_ss)

    override fun getLoadComplete(holder: BaseViewHolder): View =
        holder.getView(R.id.view_ss)

    override fun getLoadEndView(holder: BaseViewHolder): View =
        holder.getView(R.id.view_ss)

    override fun getLoadFailView(holder: BaseViewHolder): View =
        holder.getView(R.id.view_ss)
}