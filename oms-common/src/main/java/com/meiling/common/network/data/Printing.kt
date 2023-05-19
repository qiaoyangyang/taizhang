package com.meiling.common.network.data

import com.google.gson.annotations.SerializedName


data class Printing(

    @SerializedName("autoPrint")
    var autoPrint: Int? = 0,
    @SerializedName("brandCode")
    var brandCode: String? = "",
    @SerializedName("brandCodeName")
    var brandCodeName: String? = "",
    @SerializedName("poiList")
    var poiList: List<String> = arrayListOf(),
    @SerializedName("createTime")
    var createTime: Long? = 0,
    @SerializedName("deviceid")
    var deviceid: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("printNum")
    var printNum: Int? = 0,
    @SerializedName("printStatus")
    var printStatus: Int? = 0,
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("tenantId")
    var tenantId: Int? = 0,
    @SerializedName("tkAutoPrint")
    var tkAutoPrint: Int? = 0,
    @SerializedName("type")
    var type: Int? = 0,
    @SerializedName("updateAutoPrint")
    var updateAutoPrint: Int? = 0,
    @SerializedName("updateTime")
    var updateTime: Long? = 0   ,
    @SerializedName("flag")
    var flag: Boolean? = false,
)

data class CityShop(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("poiList")
    var poiList: List<Poi?>? = listOf()
)

data class Poi(
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("status")
    var status: Int? = 0
)
//  @ApiModelProperty("租户id")
//    private Long tenantId;
//    @ApiModelProperty("设备名称")
//    private String name;
//    @ApiModelProperty("设备id")
//    private String deviceid;
//    @ApiModelProperty("打印机品牌")
//    private String brandCode;
//    @ApiModelProperty("-1删除，1正常")
//    private Integer status;
//    @ApiModelProperty("创建时间")
//    private Date createTime;
//    @ApiModelProperty("修改时间")
//    private Date updateTime;
//    @ApiModelProperty("1 58 2 80")
//    private Integer type;
//    @ApiModelProperty("打印数量")
//    private Integer printNum;
//    @ApiModelProperty("接单是否自动打印1 自动打印 0不自动打印")
//    private Integer autoPrint;
//    @ApiModelProperty("退款自动打印 1 自动打印 0 不自动打印")
//    private Integer tkAutoPrint;
//    @ApiModelProperty("修改订单自动打印 1自动打印 0 不自动打印")
//    private Integer updateAutoPrint;
//    @ApiModelProperty("打印机状态  0 离线 1 在线 2 缺纸")
//    private Integer printStatus;
//    @ApiModelProperty("城市门店集合")
//    private List<Map<String, Object>> cityList;
data class PrinterConfigDto(
    var autoPrint: Int? = 0,//接单是否自动打印1 自动打印 0不自动打印
    var brandCode: String? = "",//打印机品牌
    var deviceid: String? = "",//设备id
    var name: String? = "",//设备名称
    var printNum: Int? = 1,//打印数量
    var tenantId: String? = "",//租户id
    var tkAutoPrint: Int? = 0,//退款自动打印 1 自动打印 0 不自动打印
    var type: Int? = 0,//1 58 2 80
    var updateAutoPrint: Int? = 0,//修改订单自动打印 1自动打印 0 不自动打印
    var poiIds:ArrayList<String> ? = ArrayList(),
)