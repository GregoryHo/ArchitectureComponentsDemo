package com.ns.greg.architecturecomponentsdemo.activities.room

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView.BufferType.EDITABLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.ns.greg.architecturecomponentsdemo.AppExecutors
import com.ns.greg.architecturecomponentsdemo.MainApplication
import com.ns.greg.architecturecomponentsdemo.R
import com.ns.greg.architecturecomponentsdemo.activities.room.adapter.CardListAdapter
import com.ns.greg.architecturecomponentsdemo.activities.room.db.CardDatabase
import com.ns.greg.architecturecomponentsdemo.activities.room.db.DataGenerator
import com.ns.greg.architecturecomponentsdemo.activities.room.db.entity.CardEntity
import com.ns.greg.library.fancy_logger.FancyLogger
import com.ns.greg.library.fastlightrecyclerview.decoration.EasyDecoration
import com.ns.greg.library.fastlightrecyclerview.listener.OnItemClickListener
import kotlinx.android.synthetic.main.layout_add_card.view.add_btn
import java.util.Date

/**
 * @author gregho
 * @since 2018/11/12
 */
class RoomActivity : AppCompatActivity() {

  companion object {

    const val TAG = "RoomDemo"
  }

  private lateinit var newCardTitleEt: TextInputEditText
  private lateinit var newCardDescriptionEt: TextInputEditText
  private lateinit var cardListRv: RecyclerView
  private lateinit var queryingPb: ProgressBar

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_room)
    queryingPb = findViewById(R.id.loading_pb)
    /* init database */
    initDatabase()
    /* init views */
    initAddNewCardView()
    initCardListView()
  }

  private fun initDatabase() {
    CardDatabase.init(applicationContext, getAppExecutors())
    if (CardDatabase.getInstance().isDatabaseCreated()) {
      loadCardsFromDatabase()
    } else {
      queryData()
    }
  }

  private fun queryData() {
    queryingPb.visibility = View.VISIBLE
    getAppExecutors().diskIo()
        .execute {
          FancyLogger.i(TAG, "simulate a long running operation during retrieve data from cloud")
          /* add a delay to simulate a long running operation */
          try {
            Thread.sleep(2000L)
          } catch (e: InterruptedException) {
          }

          val default = ArrayList<CardEntity>(2)
          for (i in 0 until 2 /* create 2 default cards */) {
            default.add(DataGenerator.generateCard(i))
            /* delay for different created time */
            try {
              Thread.sleep(500L)
            } catch (e: InterruptedException) {
            }
          }

          CardDatabase.getInstance()
              .cardDao()
              .insertCards(default)
          loadCardsFromDatabase()
        }
  }

  private fun initAddNewCardView() {
    newCardTitleEt = findViewById(R.id.card_title_et)
    newCardDescriptionEt = findViewById(R.id.card_description_et)
    findViewById<View>(R.id.cancel_btn).setOnClickListener {
      newCardTitleEt.setText("", EDITABLE)
      newCardDescriptionEt.setText("", EDITABLE)
    }
    findViewById<View>(R.id.add_btn).setOnClickListener {
      addNewCard(newCardTitleEt.text.toString(), newCardDescriptionEt.text.toString())
    }
  }

  private fun addNewCard(
    title: String,
    description: String
  ) {
    /* create new card */
    CardEntity(
        title.hashCode(), title, description, Date(System.currentTimeMillis())
    ).also { added ->
      /* add into database */
      getAppExecutors().diskIo()
          .execute {
            CardDatabase.getInstance()
                .cardDao()
                .insertCard(added)
          }
      /* add into adapter and notify inserted */
      with(getCardAdapter()) {
        addItem(added)
        notifyItemInserted(collectionSize - 1)
      }
    }
  }

  private fun initCardListView() {
    cardListRv = findViewById(R.id.card_list_rv)
    cardListRv.layoutManager = LinearLayoutManager(applicationContext)
    cardListRv.addItemDecoration(EasyDecoration.Builder().setMargin(Rect(20, 20, 20, 20)).build())
    cardListRv.adapter =
        CardListAdapter(applicationContext, ArrayList(), object : OnItemClickListener {
          override fun onRootViewClick(position: Int) {
          }

          override fun onSpecificViewClick(
            id: Int,
            position: Int
          ) {
            when (id) {
              R.id.card_refresh_iv -> with(getCardAdapter()) {
                getItem(position).also { updated ->
                  /* refresh updated at time */
                  updated.updatedAt = Date(System.currentTimeMillis())
                  /* update card */
                  getAppExecutors().diskIo()
                      .execute {
                        CardDatabase.getInstance()
                            .cardDao()
                            .updateCard(updated)
                      }
                }
                /* notify changed */
                notifyItemChanged(position)
              }
              R.id.card_delete_iv -> with(getCardAdapter()) {
                /* remove card in database */
                getItem(position).also { remvoed ->
                  getAppExecutors().diskIo()
                      .execute {
                        CardDatabase.getInstance()
                            .cardDao()
                            .deleteCard(remvoed)
                      }
                }
                /* remove card in adapter */
                removeItem(position)
                /* notify removed */
                notifyItemRemoved(position)
              }
            }
          }
        })
  }

  private fun loadCardsFromDatabase() {
    with(getAppExecutors()) {
      diskIo()
          .execute {
            CardDatabase.getInstance()
                .cardDao()
                .getCards()
                .also { cards ->
                  mainThread().execute {
                    queryingPb.visibility = View.INVISIBLE
                    getCardAdapter().addItems(cards)
                  }
                }
          }
    }
  }

  private fun getAppExecutors(): AppExecutors {
    return (application as MainApplication).getExecutors()
  }

  private fun getCardAdapter(): CardListAdapter {
    return cardListRv.adapter as CardListAdapter
  }
}