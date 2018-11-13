package com.ns.greg.architecturecomponentsdemo.pattern

/**
 * @author gregho
 * @since 2018/5/31
 */
open class SingletonHolder2<out T, in A1, in A2>(initializer: (A1, A2) -> T) {

  private var initializer: ((A1, A2) -> T)? = initializer
  @Volatile
  private var instance: T? = null

  fun init(argument1: A1, argument2: A2) {
    if (instance == null) {
      synchronized(this) {
        if (instance == null) {
          instance = initializer!!(argument1, argument2)
        }
      }
    }
  }

  @Throws(NullPointerException::class)
  fun getInstance(): T {
    instance?.let {
      return it
    } ?: run {
      throw NullPointerException("Please call init() before getInstance().")
    }
  }
}