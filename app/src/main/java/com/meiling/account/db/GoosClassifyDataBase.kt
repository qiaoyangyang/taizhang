package com.meihao.kotlin.cashier.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meiling.account.bean.GoosClassify
import com.meiling.account.jpush.AppConfig

@Database(entities = [GoosClassify::class],version = 1,exportSchema = false)
abstract class GoosClassifyDataBase : RoomDatabase() {
    abstract fun getGoodsCategoryDao():GoosClassifyDaoDao
    private object Single{
        val sin:GoosClassifyDataBase= Room.databaseBuilder(
            AppConfig.getApplication(),
            GoosClassifyDataBase::class.java,
            "GoosClassify.db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
    companion object{
        val instance=Single.sin
    }
}