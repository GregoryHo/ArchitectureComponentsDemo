package com.ns.greg.architecturecomponentsdemo.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.util.Log
import com.ns.greg.architecturecomponentsdemo.AppExecutors
import com.ns.greg.architecturecomponentsdemo.db.converter.DateConverter
import com.ns.greg.architecturecomponentsdemo.db.dao.CommentDao
import com.ns.greg.architecturecomponentsdemo.db.dao.ProductDao
import com.ns.greg.architecturecomponentsdemo.db.entity.CommentEntity
import com.ns.greg.architecturecomponentsdemo.db.entity.ProductEntity
import com.ns.greg.architecturecomponentsdemo.pattern.SingletonHolder2
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author gregho
 * @since 2018/5/18
 */

@Database(entities = [ProductEntity::class, CommentEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun productDao(): ProductDao

  abstract fun commentDao(): CommentDao

  private val databaseCreated = AtomicBoolean(false)

  companion object : SingletonHolder2<AppDatabase, Context, AppExecutors>(
      { context: Context, appExecutors: AppExecutors ->
        Room.databaseBuilder(context, AppDatabase::class.java,
            AppDatabase.DATABASE_NAME).addCallback(object : Callback() {
          override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.e("DB", "create database.")
            appExecutors.commonIo().execute {
              // Add a delay to simulate a long-running operation
              try {
                Thread.sleep(400)
              } catch (e: InterruptedException) {
              }

              val database = AppDatabase.getInstance()
              val products = DataGenerator.generateProducts()
              val comments = DataGenerator.generateCommentsForProducts(products)
              database.insertData(products, comments)
              database.setDatabaseCreated()
            }
          }
        }).build()
      }) {

    private const val DATABASE_NAME = "SAMPLE_DB"
  }

  fun updateDatabaseCreated(context: Context) {
    if (context.getDatabasePath(DATABASE_NAME).exists()) {
      AppDatabase.getInstance().setDatabaseCreated()
    }
  }

  private fun insertData(products: List<ProductEntity>, comments: List<CommentEntity>) {
    runInTransaction {
      productDao().insertAll(products)
      commentDao().insertAll(comments)
    }
  }

  private fun setDatabaseCreated() {
    databaseCreated.set(true)
  }

  fun getDatabaseCreated(): Boolean {
    return databaseCreated.get()
  }
}
