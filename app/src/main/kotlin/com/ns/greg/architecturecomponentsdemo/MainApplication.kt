package com.ns.greg.architecturecomponentsdemo

import android.app.Application
import com.ns.greg.library.fancy_logger.FancyLogger
import com.ns.greg.library.fancy_logger.Printer

/**
 * @author gregho
 * @since 2018/5/17
 */
class MainApplication : Application() {

  private val appExecutors: AppExecutors by lazy {
    AppExecutors()
  }

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      FancyLogger.add(FancyLogger.LOW_PRIORITY, Printer.Builder().build())
    }
  }

  fun getExecutors(): AppExecutors {
    return appExecutors
  }
}