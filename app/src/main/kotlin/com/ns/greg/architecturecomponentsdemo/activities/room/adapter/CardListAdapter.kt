package com.ns.greg.architecturecomponentsdemo.activities.room.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ns.greg.architecturecomponentsdemo.R
import com.ns.greg.architecturecomponentsdemo.activities.room.db.entity.CardEntity
import com.ns.greg.library.fastlightrecyclerview.base.BaseRecyclerViewHolder
import com.ns.greg.library.fastlightrecyclerview.base.SingleVHAdapter
import com.ns.greg.library.fastlightrecyclerview.listener.OnItemClickListener

/**
 * @author gregho
 * @since 2018/11/13
 */
class CardListAdapter(
  context: Context?,
  list: List<CardEntity>,
  private val listener: OnItemClickListener
) : SingleVHAdapter<BaseRecyclerViewHolder, CardEntity>(context, list) {

  override fun onCreateViewHolderImp(parent: ViewGroup?): BaseRecyclerViewHolder {
    return BaseRecyclerViewHolder(
        LayoutInflater.from(context).inflate(R.layout.layout_card_item, parent, false)
    ).apply {
      onItemClickListener = listener
    }
  }

  override fun getInitItemCount(): Int {
    return 0
  }

  override fun onBindViewHolderImp(
    holder: BaseRecyclerViewHolder?,
    position: Int,
    listItem: CardEntity?
  ) {
    holder?.also { root ->
      listItem?.also { item ->
        /* title */
        holder.getTextView(R.id.card_title_tv)
            .text = item.name
        /* description */
        holder.getTextView(R.id.card_description_tv)
            .text = item.description
        /* updated time */
        holder.getTextView(R.id.card_updated_at_tv)
            .text = item.updatedAt.toString()
        /* update */
        holder.getView(R.id.card_refresh_iv).setOnClickListener(holder)
        /* delete */
        holder.getView(R.id.card_delete_iv)
            .setOnClickListener(holder)
      }
    }
  }
}