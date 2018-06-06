package com.ns.greg.architecturecomponentsdemo.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ns.greg.architecturecomponentsdemo.db.entity.CommentEntity

/**
 * @author gregho
 * @since 2018/6/4
 */

@Dao
interface CommentDao {

  @Query("SELECT * FROM comments WHERE productId = :productId")
  fun loadComments(productId: Int): List<CommentEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(comments: List<CommentEntity>)
}