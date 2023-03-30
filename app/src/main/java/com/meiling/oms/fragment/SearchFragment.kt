package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.meiling.common.BaseViewModel
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<BaseViewModel, FragmentSearchBinding>() {


    companion object {
        fun newInstance(id: Int): Fragment {
            val searchFragment = SearchFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            searchFragment.arguments = bundle
            return searchFragment
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
//        mDatabind.aivImg.setImageResource(requireArguments().getInt("id"))
//        mDatabind.aivImg.scaleType = ImageView.ScaleType.FIT_CENTER
    }

    override fun getBind(inflater: LayoutInflater): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater)
    }


}