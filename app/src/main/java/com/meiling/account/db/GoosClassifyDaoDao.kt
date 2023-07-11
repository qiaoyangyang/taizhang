package com.meihao.kotlin.cashier.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meiling.account.bean.GoosClassify
import com.meiling.account.db.BaseDao

@Dao
interface GoosClassifyDaoDao: BaseDao<GoosClassify> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goods:GoosClassify)

    @Query("select * from GoosClassify")
    fun getCategoryname():MutableList<GoosClassify>

    @Query("select * from GoosClassify where id= :id")
    fun getSelectGoodsCategory(id:String):GoosClassify

    @Query("select * from GoosClassify where id= :id")
    fun getSelectGoodsCategoryActivityId(id:String):GoosClassify

    @Query("delete from GoosClassify")
    fun deleteAll()

    @Query("select count(*) from GoosClassify" )
    fun selectGoodsCount(): Int
}