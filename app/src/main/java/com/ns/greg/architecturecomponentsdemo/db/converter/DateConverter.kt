package com.ns.greg.architecturecomponentsdemo.db.converter

import android.arch.persistence.room.TypeConverter
import java.util.Date

/**
 * @author gregho
 * @since 2018/6/4
 */
class DateConverter {

  companion object {

    @TypeConverter
    @JvmStatic
    fun toDate(timestamp: Long?): Date? {
      return timestamp?.let {
        Date(it)
      } ?: run {
        null
      }
    }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(date: Date?): Long? {
      return date?.time ?: run {
        null
      }
    }
  }
}