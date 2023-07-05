package com.meiling.account.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
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
import com.meiling.account.data.AndIn
import com.meiling.account.data.AppUpdate
import com.meiling.account.databinding.FragmentRecordsCenterBinding
import com.meiling.account.dialog.OptionDatePopWindow
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.*
import com.meiling.common.fragment.BaseFragment
import com.wayne.constraintradiogroup.ConstraintRadioGroup
import org.greenrobot.eventbus.EventBus

//数据中心
class RecordsCenterFragment : BaseFragment<MainViewModel, FragmentRecordsCenterBinding>(),
    OnPageChangeListener,
    RadioGroup.OnCheckedChangeListener,
    OnItemClickListener {
    private val mFragments: ArrayList<Fragment> = ArrayList()
    private val mTitles: ArrayList<String> = ArrayList()
    var shortTimeAdapter: ShortTimeAdapter? = null
    var voucherType=0


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
        shortTimeAdapter?.setList(InputUtil.getShortTime())
        shortTimeAdapter?.setOnItemClickListener(this)
        mDatabind.vpHomePager.addOnPageChangeListener(this)
        mDatabind.startEndTimeRdg.setOnCheckedChangeListener(this)
        mDatabind.startEndTime1.isChecked = true
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
                    var outAndIn = AndIn(
                        startTimen,
                        endTime,
                        voucherType

                    )
                    EventBus.getDefault().post(outAndIn)
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
                        mDatabind.imgClear.visibility = View.VISIBLE
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
                        mDatabind.imgClear1.visibility = View.VISIBLE
                    }
                }
            }
        })

        mDatabind.tvDetail.setSingleClickListener {
            mDatabind.vpHomePager.setCurrentItem(1,false)
        }

    }

    override fun getBind(inflater: LayoutInflater): FragmentRecordsCenterBinding {
        return FragmentRecordsCenterBinding.inflate(inflater)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        voucherType=position
        if (position == 2) {
            mDatabind.rvShorTime.visibility = View.GONE
            mDatabind.ll3.visibility = View.VISIBLE
        } else {
            mDatabind.ll3.visibility = View.GONE
            mDatabind.rvShorTime.visibility = View.VISIBLE
        }

    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        shortTimeAdapter?.data?.forEach {
            it.boolean = false
        }
        shortTimeAdapter?.data?.get(position)?.boolean = true
        shortTimeAdapter?.notifyDataSetChanged()

    }


    var startTimen = formatCurrentDate()
    var endTime = formatCurrentDate()
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.startEndTime1 -> {
                startTimen = formatCurrentDate()
                endTime = formatCurrentDate()
                settime()
                var outAndIn = AndIn(
                    startTimen,
                    endTime,
                    voucherType

                )
                EventBus.getDefault().post(outAndIn)


            }
            R.id.startEndTime2 -> {
                startTimen = formatCurrentDateBeforeDay()
                endTime = formatCurrentDateBeforeDay()
                settime()
                var outAndIn = AndIn(
                    startTimen,
                    endTime,
                    voucherType

                )
                EventBus.getDefault().post(outAndIn)


            }
            R.id.startEndTime3 -> {
                startTimen = formatCurrentDateBeforeWeek()
                endTime = formatCurrentDateBeforeWeek()
                settime()
                var outAndIn = AndIn(
                    startTimen,
                    endTime,
                    voucherType

                )
                EventBus.getDefault().post(outAndIn)


            }
            R.id.startEndTime4 -> {
                startTimen = formatCurrentDateBeforeMouth()
                endTime = formatCurrentDateBeforeMouth()
                settime()
                var outAndIn = AndIn(
                    startTimen,
                    endTime,
                    voucherType

                )
                EventBus.getDefault().post(outAndIn)

            }

        }
    }

    fun settime() {
        mDatabind.tvStartTime.text = startTimen
        mDatabind.tvEndTime.text = endTime
    }





}