package com.ns.greg.architecturecomponentsdemo

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author gregho
 * @since 2018/5/17
 */
class AppExecutors {

  private val commonIo: Executor by lazy {
    Executors.newCachedThreadPool()
  }
  private val mainThread: Executor by lazy {
    MainThreadExecutor()
  }

  fun commonIo(): Executor {
    return commonIo
  }

  fun mainThread(): Executor {
    return mainThread
  }

  inner class MainThreadExecutor : Executor {

    private val mainThreadHandler: Handler by lazy {
      Handler(Looper.getMainLooper())
    }

    override fun execute(command: Runnable?) {
      mainThreadHandler.post(command)
    }
  }
}