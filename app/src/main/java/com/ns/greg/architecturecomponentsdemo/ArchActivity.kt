package com.ns.greg.architecturecomponentsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ns.greg.architecturecomponentsdemo.db.AppDatabase

/**
 * @author gregho
 * @since 2018/6/4
 */
class ArchActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (application as BaseApplication).getExecutors().commonIo().execute {
      Log.e("DB", "Created: " + AppDatabase.getInstance().getDatabaseCreated())
      var products = AppDatabase.getInstance().productDao().loadAllProducts()
      Log.e("DB", "Created: " + AppDatabase.getInstance().getDatabaseCreated())
      if (products.isEmpty()) {
        try {
          Thread.sleep(500L)
        } catch (e: InterruptedException) {
        }

        // Try again later
        products = AppDatabase.getInstance().productDao().loadAllProducts()
      }

      for (product in products) {
        println("""id: ${product.getId()}
              |name: ${product.getName()}
              |description: ${product.getDescription()}
              |price: ${product.getPrice()}""".trimMargin())
        val comments = AppDatabase.getInstance().commentDao().loadComments(product.getId()!!)
        for (comment in comments) {
          println("""comment: ${comment.getText()}
            |posted at: ${comment.getPostedAt()}
          """.trimMargin())
        }
      }
    }
  }
}