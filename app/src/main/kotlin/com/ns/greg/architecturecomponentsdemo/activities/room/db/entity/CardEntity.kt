package com.ns.greg.architecturecomponentsdemo.activities.room.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * @author gregho
 * @since 2018/11/12
 */
@Entity(tableName = "cards")
data class CardEntity constructor(
  @PrimaryKey var id: Int,
  var name: String?,
  var description: String?,
  var updatedAt: Date?
) {

  override fun toString(): String {
    return "{\"card\":{\"name\":$name, \"description\":$description, \"updatedAt\":$updatedAt}}"
  }
}