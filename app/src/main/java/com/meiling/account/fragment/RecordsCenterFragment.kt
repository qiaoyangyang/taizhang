package com.meiling.account.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hjq.base.BasePopupWindow
import com.meiling.account.R
import com.meiling.account.adapter.MyPagerAdapter
import com.meiling.account.adapter.ShortTimeAdapter
import com.meiling.account.bean.DateSplit
import com.meiling.account.bean.DateSplitList
import com.meiling.account.bean.Statistics
import com.meiling.account.data.AndIn
import com.meiling.account.databinding.FragmentRecordsCenterBinding
import com.meiling.account.dialog.OptionDatePopWindow
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.*
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.utils.TextDrawableUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//数据中心
class RecordsCenterFragment : BaseFragment<MainViewModel, FragmentRecordsCenterBinding>(),
    OnPageChangeListener,
    RadioGroup.OnCheckedChangeListener,
    OnItemClickListener {
    private val mFragments: ArrayList<Fragment> = ArrayList()
    private val mTitles: ArrayList<String> = ArrayList()
    var shortTimeAdapter: ShortTimeAdapter? = null
    var voucherType = 0


    override fun initView(savedInstanceState: Bundle?) {
        mTitles.add("良品明细")
        mTitles.add("不良明细")
        mTitles.add("入库报表")
        mFragments.add(GoodProductDetailFragment())
        mFragments.add(DefectiveDetailFragment())
        mFragments.add(WarehousingReportFragment())

        mDatabind.vpHomePager.adapter = MyPagerAdapter(childFragmentManager, mFragments, mTitles)
        mDatabind.slidingTabLayout.setViewPager(mDatabind.vpHomePager)
        mDatabind.vpHomePager.setNoScroll(true)

        mDatabind.rvShorTime?.layoutManager = GridLayoutManager(context, 2)
        shortTimeAdapter = ShortTimeAdapter()
        mDatabind.rvShorTime?.adapter = shortTimeAdapter

        shortTimeAdapter?.setOnItemClickListener(this)
        mDatabind.vpHomePager.addOnPageChangeListener(this)
        mDatabind.startEndTimeRdg.setOnCheckedChangeListener(this)
        mDatabind.startEndTime1.isChecked = true

        shortTimeAdapter?.setEmptyView(R.layout.no_time_data)
        settime()
        mDatabind.startEndTime5.setSingleClickListener {
            mDatabind.startEndTime5.isChecked = true
            OptionDatePopWindow.Builder(mActivity).setListener(object :
                OptionDatePopWindow.OnListener {
                override fun onSelected(
                    popupWindow: BasePopupWindow?,
                    startTime: String,
                    endTim: String
                ) {
                    startTimen = startTime
                    endTime = endTim
                    settime()
                    if (voucherType != 2) {
                        initData()
                    } else {
                        statement()
                    }

                }

            }).showAsDropDown(mDatabind.tvStartTime)
        }
        mDatabind.imgClear.setSingleClickListener {
            startTimen = ""
            settime()
        }
        mDatabind.imgClear1.setSingleClickListener {
            endTime = ""
            settime()
        }
        mDatabind.tvStartTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (mDatabind.startEndTime5.isChecked) {
                    if (TextUtils.isEmpty(mDatabind.tvStartTime.text.toString())) {
                        mDatabind.imgClear.visibility = View.INVISIBLE
                    } else {
                        mDatabind.imgClear.visibility = View.INVISIBLE
                    }
                }
            }
        })
        mDatabind.tvEndTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (mDatabind.startEndTime5.isChecked) {
                    if (TextUtils.isEmpty(mDatabind.tvEndTime.text.toString())) {
                        mDatabind.imgClear1.visibility = View.INVISIBLE
                    } else {
                        mDatabind.imgClear1.visibility = View.INVISIBLE
                    }
                }
            }
        })

        mDatabind.tvDetail.setSingleClickListener {
            mDatabind.vpHomePager.setCurrentItem(1, false)
        }

    }

    override fun getBind(inflater: LayoutInflater): FragmentRecordsCenterBinding {
        return FragmentRecordsCenterBinding.inflate(inflater)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        voucherType = position
        if (position == 2) {
            mDatabind.rvShorTime.visibility = View.GONE
            mDatabind.ll3.visibility = View.VISIBLE



            statement()

        } else {
            initData()
            mDatabind.ll3.visibility = View.GONE
            mDatabind.rvShorTime.visibility = View.VISIBLE
        }

        //  EventBus.getDefault().post(dateSplitList)


    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        shortTimeAdapter?.data?.forEach {
            it.boolean = false
        }
        shortTimeAdapter?.data?.get(position)?.boolean = true
        shortTimeAdapter?.notifyDataSetChanged()
        dateSplitList = shortTimeAdapter?.getItem(position)
        EventBus.getDefault().post(dateSplitList)

    }


    var startTimen = formatCurrentDate()
    var endTime = formatCurrentDate()
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.startEndTime1 -> {
                startTimen = formatCurrentDate()
                endTime = formatCurrentDate()
                settime()
                if (voucherType != 2) {
                    initData()
                } else {
                    statement()
                }

//


            }
            R.id.startEndTime2 -> {
                startTimen = formatCurrentDateBeforeDay()
                endTime = formatCurrentDateBeforeDay()
                settime()
                if (voucherType != 2) {
                    initData()
                } else {
                    statement()
                }
//


            }
            R.id.startEndTime3 -> {
                startTimen = formatCurrentDateBeforeWeek()
                endTime = formatCurrentDate()
                settime()
                if (voucherType != 2) {
                    initData()
                } else {
                    statement()
                }

            }
            R.id.startEndTime4 -> {
                startTimen = formatCurrentDateBeforeMouth()
                endTime = formatCurrentDate()
                initData()
                settime()
                if (voucherType != 2) {
                    initData()
                } else {
                    statement()
                }


            }


        }
    }

    fun settime() {
        mDatabind.tvStartTime.text = startTimen
        mDatabind.tvEndTime.text = endTime
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (voucherType == 2) {
                statement()
            } else {
                initData()
            }
        }
    }

    override fun initData() {
        super.initData()

        mViewModel.dateSplit(DateSplit(startTimen, endTime, voucherType + 1))
    }

    var dateSplitList: DateSplitList? = null
    override fun createObserver() {
        super.createObserver()
        mViewModel.dateSplitlist.onStart.observe(this) {
        }
        mViewModel.dateSplitlist.onSuccess.observe(this) {
            if (it.size != 0) {
                it[0].boolean = true
                dateSplitList = it[0]
                shortTimeAdapter?.setList(it)
                EventBus.getDefault().post(dateSplitList)
            } else {
                shortTimeAdapter?.setList(it)

                EventBus.getDefault().post(DateSplitList())
            }
        }
        mViewModel.dateSplitlist.onError.observe(this) {
            showToast(it.msg)
        }
        mViewModel.statisticsdata.onStart.observe(this) {

        }
        mViewModel.statisticsdata.onSuccess.observe(this) {

            mDatabind.tvAggregate.text = it.storageGoodsTotalNumber
            mDatabind.tvGoodsTotalNumber.text = "共计${it.goodsTotalNumber}种商品"

            mDatabind.tvAcceptedGoods.text = it.goodProductTotalNumber
            mDatabind.tvGoodProductGoodsTotalNumber.text = "共计${it.goodProductGoodsTotalNumber}种商品"

            mDatabind.tvGoodProductRatio.text = it.goodProductRatio
            mDatabind.tvGoodProductRate.text = it.goodProductRate
            if (it.ratioType == 1) {
                mDatabind.tvGoodProductRatio.setTextColor(Color.parseColor("#52C41A"))
                TextDrawableUtils.setLeftDrawable(mDatabind.tvGoodProductRatio, R.drawable.zhangsan)

            } else if (it.ratioType==2){
                mDatabind.tvGoodProductRatio.setTextColor(Color.parseColor("#FF472A"))
                TextDrawableUtils.setLeftDrawable(mDatabind.tvGoodProductRatio, R.drawable.dao_san)
            }


        }
        mViewModel.statisticsdata.onError.observe(this) {

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun data(refundType: AndIn) {
        //Log.d("yjk", "data:  1 " +refundType.voucherType)
        initData()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    //请求报表数据
    fun statement() {
        mViewModel.statistics(
            Statistics(
                startTimen, endTime,
                userStoreList()!!.viewId!!
            )
        )
        var outAndIn = AndIn(
            startTimen,
            endTime,
            voucherType

        )
        EventBus.getDefault().post(outAndIn)

    }

}