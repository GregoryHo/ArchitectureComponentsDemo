package com.ns.greg.architecturecomponentsdemo.db

import android.util.Log
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.ns.greg.architecturecomponentsdemo.activities.room.db.CardDatabase
import com.ns.greg.architecturecomponentsdemo.activities.room.db.dao.CardDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author gregho
 * @since 2018/11/14
 */
@RunWith(AndroidJUnit4::class)
class CardDaoTest {

  companion object Constant {

    const val TAG = "DaoTest"
  }

  private lateinit var database: CardDatabase
  private lateinit var cardDao: CardDao

  @Throws(Exception::class)
  @Before
  fun initDatabase() {
    /* using an in-memory database because the information stored here disappears when the
     * process is killed */
    database =
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), CardDatabase::class.java)
            /* allowing main thread queries for testing */
            .allowMainThreadQueries()
            .build()
    cardDao = database.cardDao()
  }

  @Throws(Exception::class)
  @After
  fun closeDatabase() {
    database.close()
  }

  @Test
  fun testDao() {
    Log.d(TAG, "--> START TEST")
    cardDao.getCards()
        .size.run {
      assertEquals(0, this)
      Log.d(TAG, "database size: $this")
    }

    Log.d(TAG, "insert card")
    cardDao.insertCard(TestData.CARD_ENTITY)
    cardDao.getCards()
        .size.run {
      assertEquals(1, this)
      Log.d(TAG, "database size: $this")
    }

    Log.d(TAG, "insert two cards")
    cardDao.insertCards(TestData.CARDS)
    cardDao.getCards()
        .size.run {
      assertEquals(3, this)
      Log.d(TAG, "database size: $this")
    }

    Log.d(TAG, "remove card_3")
    cardDao.deleteCard(TestData.CARD_ENTITY_3)
    cardDao.getCard(3)
        .run {
          assertNull(this)
          Log.d(TAG, "card_3: $this, database size: ${cardDao.getCards().size}")
        }

    Log.d(TAG, "update card_1")
    Log.d(TAG, "current description = ${cardDao.getCard(1).description}")
    cardDao.updateCard(TestData.CARD_ENTITY.copy(description = "test"))
    cardDao.getCard(1)
        .description.run {
      assertEquals(this, "test")
      Log.d(TAG, "update description = $this")
    }

    Log.d(TAG, "delete all")
    cardDao.deleteAll()
    cardDao.getCards()
        .size.run {
      assertEquals(0, this)
      Log.d(TAG, "database size: $this")
    }

    Log.d(TAG, "<-- END TEST")
  }
}