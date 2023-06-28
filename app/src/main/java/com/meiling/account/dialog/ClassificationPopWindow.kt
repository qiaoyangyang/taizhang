package com.meiling.account.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hjq.base.BasePopupWindow
import com.hjq.base.action.AnimAction
import com.meiling.account.R
import com.meiling.account.adapter.ClassificationAdapter
import com.meiling.account.bean.GoosClassify
import com.meiling.account.widget.setSingleClickListener

class ClassificationPopWindow {
    class Builder(context: Context) :
        BasePopupWindow.Builder<Builder>(context) {
       var classificationAdapter: ClassificationAdapter?=null
        private var listener: OnListener? = null
        lateinit var data:ArrayList<GoosClassify>
        init{
            var inflate =
                LayoutInflater.from(getContext()).inflate(R.layout.pop_classification, null)
            inflate.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setContentView(inflate)
            setOutsideTouchable(true)
            setYOffset(10)
            setXOffset(10)
            setWidth(300)
            setHeight(400)

            var tv_SynchronizingSort = inflate.findViewById<TextView>(R.id.tv_SynchronizingSort)

            var recycler = inflate.findViewById<RecyclerView>(R.id.recycler)
            recycler.layoutManager= LinearLayoutManager(getContext())
            classificationAdapter=ClassificationAdapter()
            recycler.adapter=classificationAdapter

            tv_SynchronizingSort.setSingleClickListener{
                if (listener!=null){
                    listener?.onSelected(getPopupWindow(),0,true)
                    dismiss()
                }

            }
            classificationAdapter?.setOnItemClickListener(object : OnItemClickListener{
                override fun onItemClick(
                    adapter: BaseQuickAdapter<*, *>,
                    view: View,
                    position: Int
                ) {
                    if (listener!=null){
                        listener?.onSelected(getPopupWindow(),0,false)
                        dismiss()
                    }
                }

            })



        }
        fun setList(data: MutableList<GoosClassify>): Builder = apply {
            classificationAdapter?.setList(data)
        }
        fun setListener(listener:OnListener?): Builder = apply {
            this.listener = listener
        }



    }
    interface OnListener {

        /**
         * 选择条目时回调
         */
        fun onSelected(popupWindow: BasePopupWindow?, id: Int,boolean: Boolean)
    }

}