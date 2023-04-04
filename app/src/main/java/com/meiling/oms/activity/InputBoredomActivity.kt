package com.meiling.oms.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityInputBoredomBinding
import com.meiling.oms.dialog.CheckCouponInformationDidalog
import com.meiling.oms.viewmodel.InputBoredomViewModel

// 手动输入
class InputBoredomActivity : BaseActivity<InputBoredomViewModel, ActivityInputBoredomBinding>() {


    var StockCode = ""
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tv0.setOnClickListener {
            setStockCode(mDatabind.tv0)
        }
        mDatabind.tv1.setOnClickListener {
            setStockCode(mDatabind.tv1)
        }
        mDatabind.tv2.setOnClickListener {
            setStockCode(mDatabind.tv2)
        }
        mDatabind.tv3.setOnClickListener {
            setStockCode(mDatabind.tv3)
        }
        mDatabind.tv4.setOnClickListener {
            setStockCode(mDatabind.tv4)
        }
        mDatabind.tv5.setOnClickListener {
            setStockCode(mDatabind.tv5)
        }
        mDatabind.tv6.setOnClickListener {
            setStockCode(mDatabind.tv6)
        }

        mDatabind.tv7.setOnClickListener {
            setStockCode(mDatabind.tv6)
        }
        mDatabind.tv8.setOnClickListener {
            setStockCode(mDatabind.tv6)
        }
        mDatabind.tv9.setOnClickListener {
            setStockCode(mDatabind.tv6)
        }
        mDatabind.ivSearchCloseEdt.setOnClickListener {
            StockCode = ""
            mDatabind.tvStockCode.text = StockCode
        }
        mDatabind.tvClear.setOnClickListener {
            if (mDatabind.tvStockCode.text.isNotEmpty()) {
                StockCode = StockCode.substring(0, StockCode.length - 1)
                mDatabind.tvStockCode.text = StockCode
            }
        }
        mDatabind.tvOk.setOnClickListener {
            var checkCouponInformationDidalog = CheckCouponInformationDidalog()
            checkCouponInformationDidalog.show(supportFragmentManager)
        }

    }

    fun setStockCode(TextView: TextView) {
        StockCode += TextView.text.toString()
        mDatabind.tvStockCode.text = StockCode
        mDatabind.tvStockCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())) {
                    mDatabind.ivSearchCloseEdt.visibility = View.GONE
                } else {
                    mDatabind.ivSearchCloseEdt.visibility = View.VISIBLE
                }
            }

        })
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityInputBoredomBinding {
        return ActivityInputBoredomBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()
        mViewModel.cityshop("1")
    }


}