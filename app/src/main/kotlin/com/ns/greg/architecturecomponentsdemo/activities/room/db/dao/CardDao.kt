package com.ns.greg.architecturecomponentsdemo.activities.room.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ns.greg.architecturecomponentsdemo.activities.room.db.entity.CardEntity

/**
 * @author gregho
 * @since 2018/11/12
 */
@Dao
interface CardDao {

  @Query("SELECT * FROM cards WHERE id = :id")
  fun getCard(id: Int): CardEntity

  @Query("SELECT * FROM cards")
  fun getCards(): List<CardEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCard(card: CardEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCards(cards: List<CardEntity>)

  @Update(onConflict = OnConflictStrategy.REPLACE)
  fun updateCard(card: CardEntity)

  @Delete
  fun deleteCard(card: CardEntity)

  @Query("DELETE FROM cards")
  fun deleteAll()
}