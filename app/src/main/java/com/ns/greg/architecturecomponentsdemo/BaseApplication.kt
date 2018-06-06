package com.ns.greg.architecturecomponentsdemo

import android.app.Application
import com.ns.greg.architecturecomponentsdemo.db.AppDatabase

/**
 * @author gregho
 * @since 2018/5/17
 */
class BaseApplication : Application() {

  private val appExecutors: AppExecutors by lazy {
    AppExecutors()
  }

  override fun onCreate() {
    super.onCreate()
    AppDatabase.init(applicationContext, appExecutors)
    AppDatabase.getInstance().updateDatabaseCreated(applicationContext)
  }

  fun getExecutors(): AppExecutors {
    return appExecutors
  }
}