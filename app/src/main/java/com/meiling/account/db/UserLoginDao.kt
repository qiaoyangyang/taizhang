package com.meihao.kotlin.cashier.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meiling.account.bean.UserBean
import com.meiling.account.db.BaseDao

@Dao
interface UserLoginDao : BaseDao<UserBean> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userBean: UserBean)

    @Query("select * from UserBean")
    fun getAllUsers(): MutableList<UserBean>

    @Query("delete from UserBean")
    fun deleteAll()

    @Query("select * from UserBean where adminUserId =:id")
    fun selectUserByUserId(id: String): UserBean

    @Query("select * from UserBean where username =:username")
    fun acquirename(username: String): UserBean



    //有条件查询没有成功
    @Query("select * from UserBean where issave = :issave ")
    fun getStudentById(issave: Int): MutableList<UserBean>


}