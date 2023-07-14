package com.meihao.kotlin.cashier.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meiling.account.bean.UserBean
import com.meiling.account.jpush.AppConfig

@Database(entities = [UserBean::class],version = 0,exportSchema = false)
abstract class UserLoginDataBase:RoomDatabase() {
    abstract fun getUserLoginDao():UserLoginDao
    private object Single{
        val sin:UserLoginDataBase= Room.databaseBuilder(
            AppConfig.getApplication(),
            UserLoginDataBase::class.java,
            "UserLoginData.db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
    companion object{
        val instance=Single.sin
    }
}