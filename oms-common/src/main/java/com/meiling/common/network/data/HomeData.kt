package com.meiling.common.network.data

import com.stx.xhb.androidx.entity.BaseBannerInfo
import java.math.BigDecimal


class HomeRecentTabVO(
    val banners: MutableList<BannerDTO>,
    val navigations: MutableList<NavigationDTO>
)

data class BannerDTO(
    val title: String,
    val url: String,
    val img: String,
    val type: String,
    val data: String,
) : BaseBannerInfo {
    override fun getXBannerUrl(): Any {
        return img
    }

    override fun getXBannerTitle(): String {
        return title
    }
}


data class NavigationDTO(
    val name: String,
    val path: String,
    val icon: String,
    val img: String,
)


data class ExploreGoodsVO(
    val nickName: String,
    val avatar: String,
    val id: Long,
    val circulation: Int,
    val name: String,
    val cover: String,
    val price: BigDecimal,
)

data class MarketGoodsVO(
    val nickName: String,
    val avatar: String,
    val id: Long,
    val name: String,
    val cover: String,
    val price: BigDecimal,
)

data class UserGoodsVO(
    val id: Long,
    val name: String,
    val cover: String,
    val price: BigDecimal,
    val stock: Int,
    val lickGoodsCount: Long
)


data class FollowUserVO(
    val userId: Long,
    val goodsId: Long,
    val nickName: String,
    val avatar: String,
    val brief: String,
    val name: String,
    val cover: String,
)


data class CollectionGoodsVO(
    val id: Long,
    val avatar: String,
    val price: BigDecimal,
    val number: Int,
    val name: String,
    val type: Int,
    val circulation: Int,
    val cover: String
)


data class FollowVO(
    val userId: Long,
    val nickName: String,
    val avatar: String,
    val interrelation: Int,
)

data class FansVO(
    val userId: Long,
    val nickName: String,
    val avatar: String,
    val interrelation: Int,
)


data class ProfitVO(
    val name: String,
    val cover: String,
    val money: BigDecimal,
    val createTime: String
)

data class ProfitInfoVO(
    val sumProfit: BigDecimal,
    val sumDayProfit: BigDecimal,
    val sumMonthProfit: BigDecimal,
    val sumLastMonthProfit: BigDecimal,
)


data class ReleaseRecordVO(
    val id: Long,
    val name: String,
    val conver: String,
    val state: Int,
    val createTime: String,
    val remarks: String,
)


data class MarketRecordVO(
    val id: Long,
    val name: String,
    val price: String,
    var type: Int,
    val number: Int,
    val createTime: String,
    val circulation: Int,
    val cover: String,
)


data class TransactionRecordVO(
    val name: String,
    val price: BigDecimal,
    val number: Int,
    val createTime: String,
    val circulation: Int,
    val cover: String,
    val type: Int,
)

data class AssetsRecordVO(
    val amount: BigDecimal,
    val type: Int,
    val remarks: String,
    val createTime: String,
)

data class SearchVO(
    val searchUserList: MutableList<SearchUserVO>,
    val searchGoodsList: MutableList<SearchGoodsVO>,
)


data class SearchUserVO(
    val id: Long,
    val nickName: String,
    val avatar: String,
    val followCount: Long,
    val fansCount: Long,
    val goodsCount: Long,
)


data class SearchGoodsVO(
    val id: Long,
    val name: String,
    val cover: String,
    val consignmentCount: Long,
    val brief: String,
    val circulation: Int,
    val price: BigDecimal
)

data class GoodsLikeVO(
    val id: Long,
    val name: String,
    val cover: String,
    val goodsLikeCount: Long,
    val circulation: Int,
    val nickName: String,
    val avatar: String,
)


data class FollowCollectionVO(
    val userId: Long,
    val goodsId: Long,
    val nickName: String,
    val avatar: String,
    val brief: String,
    val name: String,
    val cover: String,
)


data class QRCodeInfo(
    val issue:String,
    val type:String,
    val data:String,
    val title:String,
)