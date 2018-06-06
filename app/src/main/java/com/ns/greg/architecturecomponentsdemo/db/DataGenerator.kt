package com.ns.greg.architecturecomponentsdemo.db

import com.ns.greg.architecturecomponentsdemo.db.entity.CommentEntity
import com.ns.greg.architecturecomponentsdemo.db.entity.ProductEntity
import java.util.Date
import java.util.Random
import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.HOURS

/**
 * @author gregho
 * @since 2018/6/4
 */
class DataGenerator {

  companion object {

    @JvmStatic
    private val FIRST = arrayOf("Special edition", "New", "Cheap", "Quality", "Used")
    @JvmStatic
    private val SECOND = arrayOf("Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle")
    @JvmStatic
    private val DESCRIPTION = arrayOf("is finally here", "is recommended by Stan S. Stanman",
        "is the best sold product on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine")
    @JvmStatic
    private val COMMENTS = arrayOf("Comment 1", "Comment 2", "Comment 3", "Comment 4", "Comment 5",
        "Comment 6")

    fun generateProducts(): List<ProductEntity> {
      val products = ArrayList<ProductEntity>(FIRST.size * SECOND.size)
      val random = Random()
      for (i in FIRST.indices) {
        for (j in SECOND.indices) {
          val product = ProductEntity()
          product.setName(FIRST[i] + " " + SECOND[j])
          product.setDescription(product.getName() + " " + DESCRIPTION[j])
          product.setPrice(random.nextInt(240))
          product.setId(FIRST.size * i + j + 1)
          products.add(product)
        }
      }

      return products
    }

    fun generateCommentsForProducts(products: List<ProductEntity>): List<CommentEntity> {
      val comments = ArrayList<CommentEntity>()
      val random = Random()
      for (product in products) {
        val commentsNumber = random.nextInt(5) + 1
        for (i in 0 until commentsNumber) {
          val comment = CommentEntity()
          comment.setProductId(product.getId()!!)
          comment.setText(COMMENTS[i] + " for " + product.getName())
          comment.setPostedAt(Date(System.currentTimeMillis() - DAYS.toMillis(
              (commentsNumber - i).toLong()) + HOURS.toMillis(i.toLong())))
          comments.add(comment)
        }
      }

      return comments
    }
  }
}