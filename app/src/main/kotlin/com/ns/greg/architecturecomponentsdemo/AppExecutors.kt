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

  private val diskIo: Executor by lazy {
    Executors.newSingleThreadExecutor()
  }
  private val networkIo: Executor by lazy {
    Executors.newCachedThreadPool()
  }
  private val mainThread: Executor by lazy {
    MainThreadExecutor()
  }

  fun diskIo(): Executor {
    return diskIo
  }

  fun networkIo(): Executor {
    return networkIo
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