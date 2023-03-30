package com.meiling.common.network.data

import java.math.BigDecimal

data class UserDataVO(
    val name: String,
    val id: String,
    val portraitUri: String
)


/**
 *     private Long id;
private String nickName;
private String avatar;
private Long faceCount;
private Long followCount;
private Long goodsCount;
private BigDecimal balance;
private String mobile;
private String name;
private String idCard;
private Long collectionCount;
 */
data class UserInfoVO(
    val id: Long,
    val nickName: String,
    val avatar: String,
    val faceCount: Long,
    val followCount: Long,
    val goodsCount: Long,
    val balance: BigDecimal,
    val mobile: String,
    val name: String,
    val idCard: String,
    val likeGoodsCount: Long,
    val collectionCount: String,
    val sumAmount: String,
    val sumDayAccount: String,
    val sumMonthAccount: String,
    val sumLastMonthAccount: String,
)