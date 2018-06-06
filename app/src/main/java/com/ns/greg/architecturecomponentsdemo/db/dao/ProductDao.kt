package com.ns.greg.architecturecomponentsdemo.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ns.greg.architecturecomponentsdemo.db.entity.ProductEntity

/**
 * @author gregho
 * @since 2018/6/4
 */

@Dao
interface ProductDao {

  @Query("SELECT * FROM products")
  fun loadAllProducts(): List<ProductEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(products: List<ProductEntity>)

  @Query("SELECT * FROM products WHERE id = :productId")
  fun loadProduct(productId: Int): ProductEntity
}