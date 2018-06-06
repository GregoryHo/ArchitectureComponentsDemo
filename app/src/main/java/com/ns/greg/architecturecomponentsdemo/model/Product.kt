package com.ns.greg.architecturecomponentsdemo.model

/**
 * @author gregho
 * @since 2018/6/4
 */
interface Product {

  fun getId(): Int?

  fun getName(): String?

  fun getDescription(): String?

  fun getPrice(): Int?
}