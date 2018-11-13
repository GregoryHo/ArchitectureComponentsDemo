package com.ns.greg.architecturecomponentsdemo.activities.room.db

import com.ns.greg.architecturecomponentsdemo.activities.room.db.entity.CardEntity
import java.util.Date

/**
 * @author gregho
 * @since 2018/6/4
 */
class DataGenerator {

  companion object Functions {

    fun generateCard(id: Int): CardEntity {
      return CardEntity(
          id, "Card_$id", "This is description of Card_$id.", Date(System.currentTimeMillis())
      )
    }
  }
}