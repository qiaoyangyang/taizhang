package com.meihao.kotlin.cashier.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meiling.account.bean.Goods
import com.meiling.account.db.BaseDao

@Dao
interface ArticleDao: BaseDao<Goods> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goods:Goods)

    @Query("select * from Goods")
    fun getCategoryname():MutableList<Goods>

    //分类查询
    @Query("select * from Goods where sortCode = :sortCode")
    fun getGoodsByCategoryId(sortCode: String): MutableList<Goods>

    //商品名 拼音首字母 全拼 搜索
    @Query("select * from Goods where goodsName  like '%' ||:keyWord || '%'  or  chineseFirstPinYin like '%' ||:keyWord || '%'or  chineseAllPinYin like '%' ||:keyWord || '%'or  skuCode like '%' ||:keyWord || '%''%'or  sortCode like '%' ||:keyWord || '%'")
    fun getGoodsByKeyWord(keyWord: String): MutableList<Goods>


    @Query("delete from Goods")
    fun deleteAll()

    @Query("select count(*) from Goods" )
    fun selectGoodsCount(): Int
}