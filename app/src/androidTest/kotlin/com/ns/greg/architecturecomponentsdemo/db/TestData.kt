package com.ns.greg.architecturecomponentsdemo.db

import com.ns.greg.architecturecomponentsdemo.activities.room.db.entity.CardEntity
import java.util.Date

/**
 * @author gregho
 * @since 2018/11/14
 */
object TestData {

  @JvmField
  val CARD_ENTITY =
    CardEntity(1, "Card1", "This is description of card 1", Date(System.currentTimeMillis()))
  @JvmField
  val CARD_ENTITY_2 =
    CardEntity(2, "Card2", "This is description of card 2", Date(System.currentTimeMillis()))
  @JvmField
  val CARD_ENTITY_3 =
    CardEntity(3, "Card3", "This is description of card 3", Date(System.currentTimeMillis()))
  @JvmField
  val CARDS = arrayListOf(CARD_ENTITY_2, CARD_ENTITY_3)
}