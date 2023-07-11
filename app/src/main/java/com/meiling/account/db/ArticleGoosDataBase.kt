package com.meihao.kotlin.cashier.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meiling.account.bean.Goods
import com.meiling.account.jpush.AppConfig

@Database(entities = [Goods::class],version = 1,exportSchema = false)
abstract class ArticleGoosDataBase : RoomDatabase() {
    abstract fun getGoodsToOrderContentDao():ArticleDao
    private object Single{
        val sin:ArticleGoosDataBase= Room.databaseBuilder(
            AppConfig.getApplication(),
            ArticleGoosDataBase::class.java,
            "Goods.db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
    companion object{
        val instance=Single.sin
    }
}