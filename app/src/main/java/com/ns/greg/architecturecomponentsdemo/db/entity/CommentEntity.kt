package com.ns.greg.architecturecomponentsdemo.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.ns.greg.architecturecomponentsdemo.model.Comment
import java.util.Date

/**
 * @author gregho
 * @since 2018/6/4
 */

@Entity(tableName = "comments",
    foreignKeys = [ForeignKey(entity = ProductEntity::class, parentColumns = ["id"],
        childColumns = ["productId"], onDelete = ForeignKey.CASCADE)],
    indices = [(Index(value = ["productId"]))])
class CommentEntity : Comment {

  @PrimaryKey(autoGenerate = true)
  private var id: Int? = null
  private var productId: Int? = null
  private var text: String? = null
  private var postedAt: Date? = null

  constructor()

  constructor(id: Int, productId: Int, text: String, postedAt: Date) {
    this.id = id
    this.productId = productId
    this.text = text
    this.postedAt = postedAt
  }

  override fun getId(): Int? {
    return id
  }

  override fun getProductId(): Int? {
    return productId
  }

  override fun getText(): String? {
    return text
  }

  override fun getPostedAt(): Date? {
    return postedAt
  }

  fun setId(id: Int) {
    this.id = id
  }

  fun setProductId(productId: Int) {
    this.productId = productId
  }

  fun setText(text: String) {
    this.text = text
  }

  fun setPostedAt(postedAt: Date) {
    this.postedAt = postedAt
  }
}