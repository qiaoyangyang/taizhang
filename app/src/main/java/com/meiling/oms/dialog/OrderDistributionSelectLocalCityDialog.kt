package com.meiling.oms.dialog

import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.oms.R
import com.meiling.oms.widget.KeyBoardUtil
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * 订单配送定位地址搜索
 */
class OrderDistributionSelectLocalCityDialog() : BaseNiceDialog() {
    open var isshow = false

    init {
        setGravity(Gravity.CENTER)
        setOutCancel(false)
    }

    var txtLocal: TextView? = null

    private fun fullScreenImmersive(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            view.systemUiVisibility = uiOptions
        }
    }

    var mLocationOption: AMapLocationClientOption? = null
    var mLocationClient: AMapLocationClient? = null

    fun local() {
        var mLocationListener: AMapLocationListener = AMapLocationListener { amapLocation ->
            if (amapLocation != null) {
                if (amapLocation.errorCode == 0) {
                    amapLocation.locationType;//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.latitude;//获取纬度
                    amapLocation.longitude;//获取经度
                    amapLocation.accuracy;//获取精度信息
                    txtLocal?.text = amapLocation.city;
                    amapLocation.address;//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.country;//国家信息
                    amapLocation.province;//省信息
                    amapLocation.city;//城市信息
                    amapLocation.district;//城区信息
                    amapLocation.street;//街道信息
                    amapLocation.streetNum;//街道门牌号信息
                    amapLocation.cityCode;//城市编码
                    amapLocation.adCode;//地区编码
                    mLocationClient?.stopLocation()
                } else {
                    txtLocal?.text = "定位失败"
                    showToast("定位失败,请检查权限")
                }
            }
        }
        mLocationClient = AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient?.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
        mLocationOption?.isOnceLocation = true
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption?.interval = 100000
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption?.isNeedAddress = true;
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption?.httpTimeOut = 2000000;
        //给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient?.startLocation();


    }

    private var onSelectAddressClickLister: ((value: String) -> Unit)? = null
    fun setOnSelectAddressClickLister(onSelectAddressClickLister: (value: String) -> Unit) {
        this.onSelectAddressClickLister = onSelectAddressClickLister
    }

    fun newInstance(): OrderDistributionSelectLocalCityDialog {
        var bundle = Bundle()
        val fragment = OrderDistributionSelectLocalCityDialog()
        return fragment
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_order_distribution_local_city
    }

    override fun show(manager: FragmentManager?): BaseNiceDialog {
        return super.show(manager)
    }

    override fun dismiss() {
        super.dismiss()
    }

    lateinit var recyAdapter: BaseQuickAdapter<JSONObject, BaseViewHolder>
    lateinit var recyCityNameAdapter: BaseQuickAdapter<String, BaseViewHolder>



    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var recyCity = holder?.getView<RecyclerView>(R.id.recyCityAddress)
        var txtLocalSearch = holder?.getView<EditText>(R.id.txtLocalSearch)
        var imgOrderDisClose = holder?.getView<ImageView>(R.id.imgOrderDisClose)
        var imgClearInput = holder?.getView<ImageView>(R.id.imgClearInput)
        txtLocal = holder?.getView<TextView>(R.id.txtLocal)
        var txtLocalReact = holder?.getView<TextView>(R.id.txtLocalReact)
        local()
        txtLocalReact?.setOnClickListener {
            txtLocal?.text = "正在定位"
            local()
        }
        var json:String =  getJson("cityMap.json", requireContext()).toString()
        val jsonObjects: List<JSONObject> = JSON.parseArray(
            json,
            JSONObject::class.java
        )
        imgOrderDisClose?.setOnClickListener { dismiss() }
        recyAdapter =
            object : BaseQuickAdapter<JSONObject, BaseViewHolder>(R.layout.item_city_select_name) {
                override fun convert(holder: BaseViewHolder, item: JSONObject) {
                    val itemPosition = getItemPosition(item)
                    //第一个字母显示
                    if (itemPosition == 0) {
                        holder.getView<View>(R.id.txtKey).setVisibility(View.VISIBLE)
                    } else {
                        //然后判断当前姓名的首字母和上一个首字母是否相同,如果相同字母导航条就影藏,否则就显示
                        if (data[itemPosition].getString("letter")
                                .equals(data[itemPosition - 1].getString("letter"))
                        ) {
                            holder.getView<View>(R.id.txtKey).visibility = View.GONE
                        } else {
                            holder.getView<View>(R.id.txtKey).visibility = View.VISIBLE
                        }
                    }
                    holder.setText(R.id.txtKey, item.getString("letter"))
                    holder.setText(R.id.txtCityName, item.getString("name"))
                }
            }
        recyCity?.adapter = recyAdapter

        txtLocalSearch?.setOnEditorActionListener { v, actionId, event ->
            KeyBoardUtil.closeKeyBord(txtLocalSearch, requireContext())
            recyAdapter.setList(arrayListOf())
            recyAdapter.setEmptyView(R.layout.empty_local_recycler3)
            recyAdapter.notifyDataSetChanged()
            search(txtLocalSearch.text.toString(),jsonObjects )
            return@setOnEditorActionListener false
        }
        recyAdapter.setOnItemClickListener { adapter, view, position ->
            onSelectAddressClickLister?.invoke((adapter.data[position] as JSONObject).getString("name"))
            dismiss()
        }

        txtLocalSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()){
                    imgClearInput?.visibility = View.VISIBLE
                    recyAdapter.setList(arrayListOf())
                    recyAdapter.setEmptyView(R.layout.empty_local_recycler3)
                    recyAdapter.notifyDataSetChanged()
                    search(txtLocalSearch.text.toString(),jsonObjects )
                }else{
                    imgClearInput?.visibility = View.GONE
                    recyAdapter.setList(jsonObjects)
                    recyAdapter.notifyDataSetChanged()
                    return
                }

            }
        })
        recyAdapter.setList(jsonObjects)

        imgClearInput?.setOnClickListener { txtLocalSearch?.setText("")
            recyAdapter.setList(jsonObjects)
            recyAdapter.notifyDataSetChanged()

        }
    }

    fun search(selectKey:String,jsonObjects: List<JSONObject>){
        var searchList = arrayListOf<JSONObject>()
        val patten: String = Pattern.quote(selectKey)
        val pattern: Pattern = Pattern.compile(patten, Pattern.CASE_INSENSITIVE)
        for (i in 0 until jsonObjects.size) {
            //根据首字母
            val matcherWord: Matcher =
                pattern.matcher(jsonObjects.get(i).getString("initials"))
            //根据拼音
            val matcherPin: Matcher =
                pattern.matcher(jsonObjects.get(i).getString("pinyin"))
            //根据简拼
            val matcherJianPin: Matcher =
                pattern.matcher(jsonObjects.get(i).getString("lower"))
            //根据名字
            val matcherName: Matcher = pattern.matcher(jsonObjects.get(i).getString("name"))
            if (matcherWord.find() || matcherPin.find() || matcherName.find() || matcherJianPin.find()) {
                searchList.add(jsonObjects[i])
                recyAdapter.setList(searchList)
                recyAdapter.notifyDataSetChanged()
            }
//                    else{
//                        recyAdapter.setList(arrayListOf())
//                        recyAdapter.setEmptyView(R.layout.empty_local_recycler3)
//                        recyAdapter.notifyDataSetChanged()
//                    }
        }
    }

    fun getJson(fileName: String?, context: Context): String? {
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
            val assetManager: AssetManager = context.assets
            //通过管理器打开文件并读取
            val bf = BufferedReader(
                InputStreamReader(
                    fileName?.let { assetManager.open(it) }
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}


