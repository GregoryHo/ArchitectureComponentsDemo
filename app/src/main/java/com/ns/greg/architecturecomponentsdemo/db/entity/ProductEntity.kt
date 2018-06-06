package com.ns.greg.architecturecomponentsdemo.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.ns.greg.architecturecomponentsdemo.model.Product

/**
 * @author gregho
 * @since 2018/6/4
 */

@Entity(tableName = "products")
class ProductEntity : Product {

  @PrimaryKey
  private var id: Int? = null
  private var name: String? = null
  private var description: String? = null
  private var price: Int? = null

  constructor()

  constructor(id: Int, name: String, description: String, price: Int) {
    this.id = id
    this.name = name
    this.description = description
    this.price = price
  }

  constructor(product: Product) {
    this.id = product.getId()
    this.name = product.getName()
    this.description = product.getDescription()
    this.price = product.getPrice()
  }

  override fun getId(): Int? {
    return id
  }

  override fun getName(): String? {
    return name
  }

  override fun getDescription(): String? {
    return description
  }

  override fun getPrice(): Int? {
    return price
  }

  fun setId(id: Int) {
    this.id = id
  }

  fun setName(name: String) {
    this.name = name
  }

  fun setDescription(description: String) {
    this.description = description
  }

  fun setPrice(price: Int) {
    this.price = price
  }
}