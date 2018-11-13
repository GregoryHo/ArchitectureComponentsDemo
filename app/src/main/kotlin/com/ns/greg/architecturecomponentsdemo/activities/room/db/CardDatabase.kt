package com.ns.greg.architecturecomponentsdemo.activities.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ns.greg.architecturecomponentsdemo.AppExecutors
import com.ns.greg.architecturecomponentsdemo.activities.room.RoomActivity.Companion.TAG
import com.ns.greg.architecturecomponentsdemo.activities.room.db.CardDatabase.Plugin.DATABASE_NAME
import com.ns.greg.architecturecomponentsdemo.activities.room.db.CardDatabase.Plugin.getInstance
import com.ns.greg.architecturecomponentsdemo.activities.room.db.converter.DateConverter
import com.ns.greg.architecturecomponentsdemo.activities.room.db.dao.CardDao
import com.ns.greg.architecturecomponentsdemo.activities.room.db.entity.CardEntity
import com.ns.greg.architecturecomponentsdemo.pattern.SingletonHolder2
import com.ns.greg.library.fancy_logger.FancyLogger
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author gregho
 * @since 2018/11/12
 */
@Database(entities = [CardEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class CardDatabase : RoomDatabase() {

  companion object Plugin : SingletonHolder2<CardDatabase, Context, AppExecutors>(
      { context, appExecutors ->
        Room.databaseBuilder(context.applicationContext, CardDatabase::class.java, DATABASE_NAME)
            .addCallback(object : Callback() {
              override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                FancyLogger.i(TAG, "card database created")
                getInstance().setDatabaseCreated()
              }

              override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                FancyLogger.i(TAG, "card database opened")
              }
            })
            .build()
            .apply {
              updateDatabaseCreated(context)
            }
      }) {

    private const val DATABASE_NAME = "demo-card-db"
  }

  private val atomicDatabaseCreated = AtomicBoolean(false)

  /*--------------------------------
   * Public functions
   *-------------------------------*/

  fun isDatabaseCreated(): Boolean {
    return atomicDatabaseCreated.get()
  }

  /*--------------------------------
   * DAO functions
   *-------------------------------*/

  abstract fun cardDao(): CardDao

  /*--------------------------------
   * Private function
   *-------------------------------*/

  private fun updateDatabaseCreated(context: Context) {
    context.getDatabasePath(DATABASE_NAME)
        .exists()
        .also { exists ->
          FancyLogger.i(TAG, "card database exists: $exists")
          if (exists) {
            setDatabaseCreated()
          }
        }
  }

  private fun setDatabaseCreated() {
    atomicDatabaseCreated.set(true)
  }
}